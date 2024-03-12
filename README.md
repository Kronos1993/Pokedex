<img alt="Pokedex Logo" height="96" src="pokeball_icon.png" width="96"/>

# Pokedex
#### Copyright &copy; 2023 by Kronos

Demo App using Kotlin,View Binding, Retrofit, Dependency Injection and Pokemon Api

## Getting Started

This is a demo app.

The "Pokedex" app is an Android application that allows users to browse information about different Pokemon species.
The app retrieves its data from the official Pokemon API(https://pokeapi.com/), which provides a comprehensive catalog of Pokemon with detailed information such as stats, abilities, and evolutions.

To interact with the Pokemon API, the app uses Retrofit, a widely-used library for consuming RESTfull web services in Android applications.
Retrofit provides a convenient and efficient way to handle network requests and parse JSON responses into Kotlin objects.

For the presentation layer, the app follows the Model-View-ViewModel (MVVM) pattern, which provides a clean separation between the user interface and the business logic.
This pattern allows for easier testing, maintainability, and scalability of the app.

The user interface of the app is designed following the Material Design guidelines, which provide a modern and intuitive visual language for Android apps.
The app uses different UI components such as RecyclerView, CardView, and Toolbar to display the Pokemon information in an attractive and user-friendly way.

Overall, the "Pokedex" app provides a reliable and enjoyable experience for Pokemon fans who want to learn more about their favorite creatures in a mobile-friendly way.

## Modules

The app follows the Clean Architecture principles, which promote a clear separation of concerns between the different layers of the application.
The project is divided into several modules that handle specific functionalities, such as:

### Core
This module contains the core functionalities of the project, such as:
- Notification(to handle the notification logic)
- Adapters(to handle general adapters that can be use in several projects)
- Extension Function(with several types utilities)
- IO(to handle in-out option and persistance)
- ViewModel(general view model data)
- Util(general utilities)

### Data
This module contains the data types and dtos of the project, data sources and repository implementations.

### Domain
This module contains the domain types and repository declarations.

### Logger
This module contains the logger logic of the app.

### Resources
This module contains the resources.

### WebClient
This module handle the creation of the retrofit client.