# User Management App

A lightweight mobile application designed to display user information and allow users to like/unlike profiles. Built with modern Android development practices, this project follows **Clean Architecture** and the **MVI** (Model-View-Intent) pattern, ensuring scalability, testability, and maintainability.

## Project Overview

The User Management App provides an intuitive interface to explore a list of users, view their details, and like/unlike profiles with persistent local storage. The app ensures efficient state management, responsive UI, and a seamless user experience.

The architecture incorporates **Jetpack Compose** for UI, **GetX** for state management, and **SharedPreferences** for caching liked users.

## Key Features

- **User Listing**: Browse through a list of users with essential details like name, email, and phone.
- **Like/Unlike Functionality**: Users can like or unlike profiles, with the state being cached locally for persistence.
- **State Management**: Utilizes **MVI architecture**, ensuring a unidirectional data flow.
- **Shimmer Effect**: Provides a visually appealing loading placeholder while data is being fetched.
- **Local Storage**: Caching liked users using **SharedPreferences**, allowing state persistence.
- **Dependency Injection**: Modular and testable code through dependency injection.
- **Unit Testing**: Comprehensive test coverage for business logic and repository layers.
- **Clean Architecture**: Well-structured project separating concerns into layers for better maintainability.

## Technologies Used

- **Jetpack Compose**: For building a declarative and responsive UI.
- **Kotlin**: The primary programming language used for both UI and business logic.
- **SharedPreferences**: Used for storing liked user preferences locally.
- **Shimmer Effect**: Enhances UI experience by displaying placeholder animations during data loading.
- **Coroutines**: For asynchronous operations, ensuring smooth UI performance.
- **Dagger/Hilt**: Dependency injection to provide modularization and scalability.
- **JUnit & MockK**: For unit testing the repository and use cases.

## Architecture Overview

This project follows the **Clean Architecture** approach, dividing responsibilities into distinct layers:

- **Presentation Layer** (Jetpack Compose, ViewModel, Intents, States) – Handles UI and user interactions.
- **Domain Layer** (UseCases, Repository Interfaces, Entities) – Contains business logic and use cases.
- **Data Layer** (Repository Implementation, Mappers, Data Sources) – Handles data operations and mapping.

## Running Tests

To run unit tests for the app, use the following command:
```
./gradlew testDebugUnitTest
```

