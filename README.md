# Drug Tracker 

[![License](https://img.shields.io/badge/License-Apache%202.0-blue.svg)](https://opensource.org/licenses/Apache-2.0)
[![Android Studio](https://img.shields.io/badge/IDE-Android%20Studio-brightgreen)](https://developer.android.com/studio)
[![Kotlin](https://img.shields.io/badge/Kotlin-2.0.0-blue)](https://kotlinlang.org/)  

An application provides drug-related info.

---

App Download link: https://drive.google.com/file/d/1Yp-UPCwc9x-pHCVzlDfIIh2vcLzoHdZQ/view?usp=sharing

App Demo link: 

[![Watch the video](https://img.youtube.com/vi/paBPXmHGfKo/1.jpg)](www.youtube.com/watch?v=paBPXmHGfKo)

## Table of Contents

- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Project Structure](#project-structure)  
- [Installation](#installation)  
- [Testing](#testing)   
- [License](#license)
- [Contact](#contact)

---


## Tech Stack

- **Programming Language:** Kotlin
- **UI Framework:** Material UI Library, Jetpack Compose
- **Architecture:** MVVM
- **Dependency Injection:** Hilt
- **Database:** Room
- **Asynchronous Tasks:** Kotlin Coroutines
- **Network Library:** Retrofit
- **Authentication Service:** Firebase

## Architecture

This project follows **MVVM (Model-View-ViewModel)** architecture to ensure a clean separation of concerns:

1. **Model**:  
   - Responsible for handling data operations like fetching Medicine related data.  
   - Example: `Repository` classes.  

2. **ViewModel**:  
   - Acts as a bridge between the `Model` and `View`.  
   - Manages UI-related data and state.  

3. **View**:  
   - Implements the user interface via **Material UI Library**.  
   - Observes data changes from the `ViewModel` and renders the UI.

## Project Structure

```plaintext
├── data/                           # Data layer: API, database, repositories
    ├── api/                        # Contains remote api service related classes  
│   ├── database/                   # Contains Room database-related classes    
│   ├── model/                      # Contains Models for Room   
│   └── repository/                 # Repository implementations 
├── di/                             # Dependency injection modules  
├── ui/                             # Presentation layer: UI and ViewModel  
├── build.gradle                    # Gradle configuration  
└── AndroidManifest.xml             # App configuration  
```

- **data:** Manages Remote Api calls, Database calls, data caching, and data mapping.
- **presentation:** Handles UI and interaction logic.
- **di:** Dependency injection setup using Hilt.

## Covered Requirents

- Signup/SignIn Using Firebase
- Searching Medicine List and showing top 10
- Showing Details of Medicine in Details Page
- Adding Medicine to User's medication list
- Showing User's medication list
- Deleting User's medication item

## Project Config
- Java Version: Java 21
- Target Android Sdk: Android 10 (Api Level: 29)

## Improvement Scope If given more time

- Introducing Unit tests
- Adding Calendar Event
- UI improvements (Adding nice dialog, fonts etc)
  
## Installation

Install the apk from here: https://drive.google.com/file/d/1Yp-UPCwc9x-pHCVzlDfIIh2vcLzoHdZQ/view?usp=sharing

Or

1. Clone the repository:
   ```bash
   git clone git@github.com:HasibPrince/StartupCompose.git
   ```

2. Open the project in Android Studio.

3. Sync the project with Gradle files.

4. Build and run the app on an emulator or physical device.

## Testing

## Contributions

Contributions are welcome! Please follow these steps:

1. Fork the repository.
2. Create a new branch (`feature/your-feature`).
3. Commit your changes.
4. Push to your branch.
5. Open a pull request.

## License

Copyright 2024 HasibPrince (Md. Hasibun Nayem)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

## Contact

For questions or feedback, feel free to reach out:

- **Author:** Hasib Prince
- **GitHub:** [HasibPrince](https://github.com/HasibPrince)

---

Thank you for checking out the App Startup project! ✨

