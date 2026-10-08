# ChessInsight

ChessInsight is a professional chess analysis Android application that allows you to import your public games from Chess.com and analyze them locally using Stockfish.

## Features
- **Local Synchronization**: Fetches public games via the Chess.com PubAPI.
- **Local Engine**: Uses a bundled Stockfish binary to evaluate positions without relying on external servers.
- **Game Library**: View your imported games offline.
- **Privacy First**: Does not ask for your Chess.com password. Only public API data is used.

## Architecture
- **Language**: Kotlin
- **UI**: Jetpack Compose
- **Database**: Room (SQLite)
- **Networking**: Ktor
- **Engine**: Stockfish executable executed via `ProcessBuilder` (requires cross-compiled ARM64 Stockfish binary).

## Build Instructions (APK/AAB)

### Prerequisites
- Java 17+
- Android SDK (API 34)
- Gradle 8.2+

### Steps
1. Clone the repository.
2. In the root directory, run:
   ```bash
   ./gradlew assembleDebug
   ```
   (Or use Android Studio to open the project).
3. The APK will be available in `app/build/outputs/apk/debug/app-debug.apk`.
4. **Important**: Before releasing, you must download the Stockfish 16.1 Android ARM64 binary and place it in the `assets/` or `jniLibs/` folder, adjusting the `StockfishEngine.kt` path accordingly.

## Known Limitations
- The current build scaffolding does not include the 80MB Stockfish NNUE binary or executable due to size constraints. It must be manually added.
- The UI is currently limited to the Dashboard and Sync functionality. The Analysis Board UI (Slice 7+) needs further Compose implementation.
- API requests do not currently implement sophisticated 429 backoff strategies (relies on sequential requests).

## Future Roadmap
- Complete the Compose Analysis Board with interactive pieces.
- Integrate the evaluation graph.
- Implement move classifications (Blunder, Mistake, Inaccuracy) based on engine centipawn loss.
