## MVI Architecture in Jetpack Compose
A sample application created to use MVI architecture with Jetpack Compose where you can search dog breed to see the details which includes lifespan, temperament, height & weight along with high quality image.

The app follows MVI architecture with repository pattern, Hilt for dependency injection, Jetpack Compose for UI and other jetpack components.

## Design and Libraries
- MVI architecture + Repository pattern
- Jetpack Compose
- ViewModel, Flow & Channel
- Coroutines
- Hilt
- Retrofit
- Navigation Component
- Coil for image loading

## Modules
There are 2 main modules: app and core-mvi
- sample: Contains sample application for dog breeds
- core-mvi: Contains core classes for MVI architecture

## Folder structure of app module
There are 4 main folders: app, data, di and ui.
- app: Contains Application class.
- data: Contains subfolders for api client, extension functions, model classes and repositories.
- di: Contains module classes for dependencies.
- ui: Contains activity, navgraph, subfolders for individual screen subfolders for Compose screen, viewmodel, domain for use case and other composables.

<br/>

***Note: Kindly contribute or raise issues for any improvment/bugs***