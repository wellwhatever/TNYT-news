# The New York Times News Multiplatform Mobile Application

The New York Times News is a simple KMM application designed to
demonstrate approach to mobile application development.

## Overview

The primary purpose of this project is to showcase the implementation of a modern mobile
application, leveraging Kotlin Multiplatform Mobile technology to facilitate the sharing of common
code across different platforms. The application emphasizes code reusability and maintainability,
achieved through the application of **recommended _Clean Architecture_ principles**.

## Features

The application utilizes The New York Times API to simply display its articles. It contains two
screens: one for displaying a list of news articles, and another one serving as the detail view for
each article.

## Project Structure

### Architecture

The project follows modern Clean Architecture principles, splitting the architecture into three main
layers: Data, Domain, and UI. Only the Domain layer is exposed to UI layers using UseCase classes,
allowing easy sharing of all business logic and reuse in multiple UI layers. MVVM was selected as
the UI-layer architecture. ViewModel is used as a state hoist for screens, exposing a single screen
state. It also reacts to screen events and combines fresh data from lower layers into a new screen
state in a reactive manner to keep data up to date.

### Modularization

The application is divided into modules structured by both feature and layer, similar to packages
inside each module. This approach keeps module coupling as low as possible while maintaining
flexibility and high cohesion. Feature modules depend only on data/domain modules. Data/domain
modules can be reused as multiple features might require the same data sources.

#### Structure

Description on most important structure components.

- **build-logic:** contains all project's build-related logic and Gradle Convention plugins used
  across project.
- **feature-...:** all project's feature modules, included in higher level application module.
- **shared:** umbrella module for all KMM modules.
- **domain-...:** UseCases classes and business logic per feature.
- **data-...:** concrete Repositories implementation.
- **core-common:** common utils used across different modules in project.
- **core-model:** domain data classes and Repository interfaces.

#### KMM Modularization

Project shares all business logic between different platforms. To support project's architecture,
shared code is also splitted into different modules, following project's architecture. Because KMM
doesn't really support multi-module application, to import **_shared_** module is used as **umbrella
** module.

## Technologies and Libraries

### Dependency Injection

For Dependency Injection purposes, **Koin** was used as a convenient, lightweight, and most
importantly, multiplatform library. To inject UseCases into iOS applications, simple object wrappers
can be used to create and pass dependencies.

### Navigation

**Compose Destinations** library was utilized to avoid boilerplate code around Compose navigation
component.

### Database

**SQLDelight** was employed as a multiplatform database that would work on both Android and iOS
platforms.

### Ktor

**Ktor Client** was used as a multiplatform HTTP Client.

### Build Logic

Multi-module applications often contain various Gradle scripts with similar build logic. Due to this
reason, build logic was implemented using **Gradle Convention Plugins**, allowing for organization
and reuse of build-logic.

## Detekt

Project has workflow to run **Detekt** static analysis in combination with **Review Dog** to analyse Github pull
requests and write review comments in case of any warnings.

## Testing
TODO
Describe testing here!

## App Distribution

Application is distributed using Firebase App Distribution. GitHub Actions is set up to automatically distribute release version of application every time something is pushed with 'v-' tag in 'release/...' branch. 
