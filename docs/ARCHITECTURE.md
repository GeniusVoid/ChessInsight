# ChessInsight - Architecture & Implementation Plan

## 1. Tech Stack
- **Language**: Kotlin
- **UI**: Jetpack Compose
- **Database**: Room (SQLite)
- **Networking**: Ktor Client
- **Chess Engine**: Stockfish (compiled for ARM64) bundled as an executable inside the APK, run via `java.lang.ProcessBuilder`.
- **Chess Rules/PGN**: A robust Kotlin chess library (e.g., a custom port or existing lightweight library).

## 2. Core Modules
- **`app`**: Main UI and DI setup.
- **`core-network`**: Ktor client for Chess.com PubAPI.
- **`core-database`**: Room DAOs for Games, Users, and Analysis.
- **`core-chess`**: Move generation, board representation, PGN parsing.
- **`core-engine`**: Stockfish process management, UCI protocol communication.
- **`feature-sync`**: WorkManager or Coroutine flows for syncing archives.
- **`feature-analysis`**: The engine that drives game review, evaluates cp loss, and assigns classifications.
- **`feature-ui`**: Compose screens for Dashboard, Game Library, and Analysis Board.

## 3. Implementation Slices
Following the prompt's directive:
1. **Slice 1**: Project Setup + UI Shell
2. **Slice 2**: Chess Board Component
3. **Slice 3**: Stockfish Integration
4. **Slice 4**: Chess.com Profile API Lookup
5. **Slice 5**: Game Archive Sync
6. **Slice 6**: Local Database integration
7. **Slice 7**: Game Library Screen
8. **Slice 8**: Game Analysis Logic
9. **Slice 9-14**: Polish, Graphs, Insights, Settings.

## 4. Build Environment Note
To actually compile the Android app in this environment (Termux), we would need the Android SDK. Given the size of the SDK and Gradle dependencies (~1.5GB+), we will focus on generating the complete source code, project files (build.gradle.kts), and a comprehensive `README.md` with build instructions. If the user explicitly authorizes the bandwidth and storage usage, we can attempt a local build.
