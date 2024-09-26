# StoryAppLite-Dicoding-BPAAI 📖
This project is part of the **Dicoding "Belajar Pengembangan Aplikasi Android Intermediate (BPAAI)"** course. StoryAppLite is a feature-rich Android application that allows users to register, log in, upload stories, and view stories on a map. The app utilizes modern Android development practices such as custom views, paging, remote mediation, and more.

<br>

> **⚠️ Warning:**  
> If you are working on the same submission, please **do not copy-paste**. Use this repository as a **reference material only**.

<br>

## Features 📱
- **User Registration & Login:** Securely register and log in users.
- **Story Upload:** Capture images using **CameraX** or upload from the gallery to share stories.
- **Paging3 Integration:** Efficiently load stories in pages with **Remote Mediator** for handling local and remote data sources.
- **Story Map:** Visualize user stories on a map using **Google Maps SDK**.
- **Local Storage:** Store user preferences using **DataStore** and stories with **Room** database.
- **Offline Access:** Seamless integration of offline support with cached data using Room and remote mediator.
- **Dark Mode:** Support for light and dark themes.
- **Unit Testing:** Comprehensive unit tests to ensure app stability.

## Tech Stack 🛠️
- **Kotlin** - Primary language for Android development.
- **Android View** - Standard UI components for user interaction.
- **Custom View** - Creating reusable and customizable views.
- **Retrofit** - For HTTP networking and API calls.
- **DataStore** - To handle user preferences.
- **Paging3** - For efficient pagination of story data.
- **Remote Mediator** - For seamless data synchronization between remote and local sources.
- **Room** - Local database for caching data.
- **Maps SDK** - For integrating Google Maps and showing story locations.
- **LiveData** - Observing and reacting to data changes.
- **MVVM** - A clean and maintainable architecture pattern.
- **Coroutines** - Asynchronous programming to handle background tasks.
- **Glide** - Efficient image loading and caching.
- **CameraX** - Camera API for capturing images.
- **Unit Testing** - Ensuring robust code with tests.

## Screenshots 📸

| Welcome Screen | Register Screen | Login Screen |
|----------------|-----------------|--------------|
| ![Screenshot_2023-05-17-00-56-27-03_d6717f7918fda3bc8353b8836fbc02fc](https://github.com/KristianEka/StoryAppLite/assets/69257405/d5ec5f8b-c7ae-4a40-b2b1-73ac9b2edea9) | ![Screenshot_2023-05-17-00-56-32-91_d6717f7918fda3bc8353b8836fbc02fc](https://github.com/KristianEka/StoryAppLite/assets/69257405/35d3776b-9fe8-46cf-aaff-39da9bd7ff2b) | ![Screenshot_2023-05-17-00-56-38-85_d6717f7918fda3bc8353b8836fbc02fc](https://github.com/KristianEka/StoryAppLite/assets/69257405/c2f59ed7-a4b3-4ef4-90d0-14e2b7230eec) |

| Home Screen | Story Details |
|-------------|---------------|
| ![Screenshot_2023-05-17-00-56-57-25_d6717f7918fda3bc8353b8836fbc02fc](https://github.com/KristianEka/StoryAppLite/assets/69257405/b369ecae-b5b9-4c16-8c60-8551945db712) | ![Screenshot_2023-05-17-00-57-03-32_d6717f7918fda3bc8353b8836fbc02fc](https://github.com/KristianEka/StoryAppLite/assets/69257405/1bbcb32f-6bfb-4b83-bcd3-c6848c46b75d) |

| Camera/Gallery | Upload Story | Story Map |
|----------------|--------------|-----------|
| ![Screenshot_2023-05-17-00-57-16-24_d6717f7918fda3bc8353b8836fbc02fc](https://github.com/KristianEka/StoryAppLite/assets/69257405/3d765692-54a5-4a07-8303-7502d78b5e69) | ![Screenshot_2023-05-17-00-57-41-12_d6717f7918fda3bc8353b8836fbc02fc](https://github.com/KristianEka/StoryAppLite/assets/69257405/f632477a-5e16-43d4-a74c-b3f346fc5c51) | ![Screenshot_2023-05-17-00-58-03-98_d6717f7918fda3bc8353b8836fbc02fc](https://github.com/KristianEka/StoryAppLite/assets/69257405/1a141a7a-3991-4c7c-b91e-fdb5d30615eb) |

## Setup & Installation ⚙️
1. Clone the repository:
   ```bash
   git clone https://github.com/KristianEka/StoryAppLite.git
   ```
2. Install dependencies and build the project in **Android Studio**.
3. Run the app on an emulator or device.
