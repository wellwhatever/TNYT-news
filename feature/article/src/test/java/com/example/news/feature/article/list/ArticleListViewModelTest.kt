package com.example.news.feature.article.list

import app.cash.turbine.test
import com.example.news.shared.domain.articles.GetArticlesUseCase
import com.example.shared.core.testing.data.FakeArticleDataProvider
import com.example.shared.core.testing.data.FakeArticleRepository
import com.example.shared.core.testing.data.FakeErrorArticleRepository
import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@ExperimentalStdlibApi
class ArticleListViewModelTest : FunSpec({
    lateinit var viewModel: ArticleListViewModel
    lateinit var fakeRepository: FakeArticleRepository
    beforeEach {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }
    afterEach {
        Dispatchers.resetMain()
    }
    beforeEach {
        fakeRepository = FakeArticleRepository()
        viewModel = ArticleListViewModel(
            GetArticlesUseCase(fakeRepository),
            ArticleListErrorMapper(),
        )
    }

    test("articleListScreenState should emit loading as initial value") {
        val result = viewModel.articleListScreenState.value
        val expected = ArticleListScreenState.Loading
        result shouldBe expected
    }
    test("articleListScreenState should emit content state after init") {
        viewModel.articleListScreenState.test {
            val result = awaitItem()
            val expectedArticleListState =
                ArticleListState.Content(FakeArticleDataProvider.fakeArticles.toImmutableList())
            val expectedSearchBarState = ArticleSearchBarState("")
            val expected =
                ArticleListScreenState.Content(expectedArticleListState, expectedSearchBarState)

            result shouldBe expected
        }
    }
    test("onQueryChange should update search bar state") {
        viewModel.articleListScreenState.test {
            val newQuery = "expected"
            viewModel.onQueryChange(newQuery)
            val result = expectMostRecentItem()

            val expectedArticleListState =
                ArticleListState.Content(FakeArticleDataProvider.fakeArticles.toImmutableList())
            val expectedSearchBarState = ArticleSearchBarState(newQuery)
            val expected =
                ArticleListScreenState.Content(expectedArticleListState, expectedSearchBarState)

            result shouldBe expected
        }
    }

    test("onQueryChange should propagate changes in article list values") {
        val newQuery = "expected"
        val articleListContentState =
            ArticleListState.Content(FakeArticleDataProvider.fakeArticles.toImmutableList())
        val articleListLoadingState = ArticleListState.Loading
        val articleListUpdatedState = ArticleSearchBarState(newQuery)
        viewModel.articleListScreenState.test {
            awaitItem() // Ignore initial state
            viewModel.onQueryChange(newQuery)

            // Only app bar state should be updated
            val result1 = awaitItem()
            val expected1 =
                ArticleListScreenState.Content(articleListContentState, articleListUpdatedState)

            result1 shouldBe expected1

            // Content state is set to loading while updating data
            val result2 = awaitItem()
            val expected2 =
                ArticleListScreenState.Content(articleListLoadingState, articleListUpdatedState)

            result2 shouldBe expected2

            // Both states should be set to content
            val result3 = awaitItem()
            val expected3 =
                ArticleListScreenState.Content(articleListContentState, articleListUpdatedState)

            result3 shouldBe expected3
        }
    }

    test("onClearQuery should reset query flow to empty string") {
        viewModel.articleListScreenState.test {
            viewModel.onQueryChange("expected")
            viewModel.onClearQueryClick()
            val result = expectMostRecentItem()

            val expectedArticleListState =
                ArticleListState.Content(FakeArticleDataProvider.fakeArticles.toImmutableList())
            val expectedSearchBarState = ArticleSearchBarState("")
            val expected =
                ArticleListScreenState.Content(expectedArticleListState, expectedSearchBarState)

            result shouldBe expected
        }
    }
})

class ArticleListViewModelErrorTest : FunSpec({
    lateinit var viewModel: ArticleListViewModel
    lateinit var fakeRepository: FakeErrorArticleRepository
    beforeEach {
        Dispatchers.setMain(UnconfinedTestDispatcher())
    }
    afterEach {
        Dispatchers.resetMain()
    }
    beforeEach {
        fakeRepository = FakeErrorArticleRepository()
        viewModel = ArticleListViewModel(
            GetArticlesUseCase(fakeRepository),
            ArticleListErrorMapper(),
        )
    }
    test("articleListScreenState should emmit error state") {
        viewModel.articleListScreenState.test {
            val result = expectMostRecentItem()
            val expectedArticleListState =
                ArticleListState.Error.Unexpected
            val expectedSearchBarState = ArticleSearchBarState("")
            val expected =
                ArticleListScreenState.Content(expectedArticleListState, expectedSearchBarState)

            result shouldBe expected
        }
    }

    test("onReloadClick clear error state and refresh state") {
        viewModel.articleListScreenState.test {
            viewModel.onReloadClick()
            val result = expectMostRecentItem()
            val expectedListState = ArticleListState.Loading
            val expectedSearchBarState = ArticleSearchBarState("")
            val expected =
                ArticleListScreenState.Content(expectedListState, expectedSearchBarState)

            result shouldBe expected
        }
    }
})
