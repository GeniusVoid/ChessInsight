# ChessInsight - Research Report

## Agent 1: Chess.com PubAPI
- **Validation**: Username is validated via `https://api.chess.com/pub/player/{username}`. Returns 404 if invalid.
- **Profile**: `https://api.chess.com/pub/player/{username}`
- **Stats**: `https://api.chess.com/pub/player/{username}/stats`
- **Archives**: `https://api.chess.com/pub/player/{username}/games/archives` (returns list of URLs for monthly archives).
- **Monthly Games**: `https://api.chess.com/pub/player/{username}/games/{YYYY}/{MM}`
- **Game Data**: Contains PGN, time class/control, rated status, rules, ECO, end_time, white/black players (with ratings and results), game UUID, and URL.
- **Rate Limits**: Chess.com strongly enforces rate limits. It is recommended to use ETags or `Last-Modified` headers. Sequential requests with a delay (e.g., 1-2 seconds) are preferred over massive parallel requests.
- **User-Agent**: Must include a meaningful `User-Agent` (e.g., `ChessInsight Android App / contact: developer@example.com`).

## Agent 2: Chess.com Terms/IP/Fair Play
- **Compliance**: The app must use original branding, names, and assets. "Game Review", the Chess.com logo, and their specific piece designs are proprietary.
- **Fair Play**: Engine analysis is strictly post-game. The app does not interact with live games or active Chess.com tabs.
- **API Rules**: Public data only. No scraping HTML.

## Agent 3: Stockfish
- **Version**: Stockfish 16.1 or later is standard. Requires NNUE for optimal 3000+ Elo play. 
- **Integration**: A native Android build (ARM64) compiled via NDK, communicating with the JVM layer via standard input/output streams (process execution) or JNI.
- **UCI Protocol**: Standard commands: `uci`, `isready`, `position fen ...`, `go depth 20`, `stop`, `quit`.
- **License**: GPLv3. The app's integration must comply (if distributing Stockfish binaries).

## Agent 4: Chess Rules
- **Library**: `chezz` or a Kotlin/Java port of `chess.js` (e.g., `chess-kt` or `KChess`). For a production app, using a robust, open-source JVM chess library is safer than writing from scratch. We will use a reliable pure-Kotlin engine for move validation and rule enforcement.

## Agent 5: PGN
- **Parsing**: Standard PGN parsing is required to break down the move text returned by Chess.com's API into individual moves and positions. We must handle edge cases like comments, variations, and clock times.

## Agent 6: Game Synchronization
- **Strategy**: 
  1. Fetch archives list.
  2. Filter out already fully downloaded months.
  3. Fetch the latest month and insert new games into the local DB.
  4. Use Room Database for offline storage.

## Agent 7: Data Model
- **Entities**: 
  - `User` (username, avatar_url, sync_state)
  - `Game` (uuid, pgn, white_player, black_player, result, end_time, analyzed)
  - `MoveAnalysis` (game_uuid, ply, evaluation, best_move, classification)

## Agent 8: Analysis Algorithms
- **Classification**:
  - Blunder: Significant drop in winning probability (e.g., eval drops by > 200 centipawns or mate is lost).
  - Mistake: Drop of 100-200 cp.
  - Inaccuracy: Drop of 50-100 cp.
  - Best: Matches Stockfish's top choice.
  - Excellent/Good: Top 2-3 choices with minimal cp loss.

## Agent 9: Accuracy Calculation
- **Formula**: (Based on win percentage models). Accuracy = `100 * (1 - (average win probability loss per move / some constant))`. The formula will be normalized to produce a 0-100 score that feels intuitive but remains mathematically distinct from Chess.com's proprietary algorithm.

## Agents 10-19 Synthesis
- **Tactical Motifs**: Detected by analyzing eval spikes after specific piece interactions.
- **Android Architecture**: Jetpack Compose (UI), Room (DB), Coroutines/Flow (Async), Ktor (Networking), ViewModel (MVI/MVVM).
- **UI/UX**: Dark-themed, sleek "Lab" aesthetic.
- **Security**: No passwords required. Only public data.
