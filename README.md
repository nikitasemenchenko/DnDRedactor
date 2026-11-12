# DnD Redactor

Android application for creating and managing Dungeons & Dragons characters.

The app allows you to build a character step by step: choose race, class, archetype, background and ability scores. Reference data is loaded from a remote D&D API, while created characters are stored locally on the device.

## Features

- Character creation wizard (race, class, archetype, background, ability scores)
- Reference data from remote D&D API
- Local storage of characters
- Modern UI built with Jetpack Compose

## Tech Stack

- Language: Kotlin
- UI: Jetpack Compose
- Local database: Room
- Networking: Retrofit
- Dependency injection: Hilt
- Image loading: Coil

## Screenshots

<p align="center">
  <img src="screenshots/screenshot1.jpg" width="200" alt="Screenshot 1"/>
  <img src="screenshots/screenshot2.jpg" width="200" alt="Screenshot 2"/>
  <img src="screenshots/screenshot3.jpg" width="200" alt="Screenshot 3"/>
  <img src="screenshots/screenshot4.jpg" width="200" alt="Screenshot 4"/>
</p>

<p align="center">
  <img src="screenshots/screenshot5.jpg" width="200" alt="Screenshot 5"/>
  <img src="screenshots/screenshot6.jpg" width="200" alt="Screenshot 6"/>
  <img src="screenshots/screenshot7.jpg" width="200" alt="Screenshot 7"/>
  <img src="screenshots/screenshot8.jpg" width="200" alt="Screenshot 8"/>
</p>
## Installation

1. Clone the repository:
```bash
git clone https://github.com/nikitasemenchenko/DnDRedactor.git
```

2. Open the project in Android Studio.

3. Sync Gradle and run the application on an emulator or a physical device.

## Requirements

- Android 8.0 (API level 26) or higher
- Android Studio Hedgehog or newer

## Project Structure

```
DnDRedactor/
├── app/
│   ├── src/main/java/     # Application source code
│   └── src/main/res/      # Resources
├── build.gradle.kts
└── ...
```
