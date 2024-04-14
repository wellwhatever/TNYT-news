package com.example.news.shared.core.network

//
// class BaseNetworkClient(
//    private val baseUrl: String,
//    private val apiKey: String,
//    override val domainExceptionMapper: DomainExceptionMapper,
//    httpClient: HttpClient,
// ) : NetworkClient {
//    override val httpClient = httpClient.config {
//        install(Logging) {
//            logger = Logger.Companion.DEFAULT
//            level = LogLevel.ALL
//            filter { request ->
//                request.url.host.contains("ktor.io")
//            }
//            sanitizeHeader { header -> header == HttpHeaders.Authorization }
//        }
//        install(ContentNegotiation) {
//            json(
//                Json {
//                    ignoreUnknownKeys = true
//                    prettyPrint = true
//                    isLenient = true
//                },
//            )
//        }
//
//        defaultRequest {
//            url(baseUrl)
//        }
//    }.apply {
//        plugin(HttpSend).intercept { request ->
//            request.url {
//                parameters.append("api-key", apiKey)
//            }
//            execute(request)
//        }
//    }
// }
