import { useState } from 'react';
import { Search, Loader2 } from 'lucide-react';
import { Chessboard } from 'react-chessboard';
import { Chess } from 'chess.js';

function App() {
  const [username, setUsername] = useState('');
  const [games, setGames] = useState<any[]>([]);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState('');
  
  const [game, setGame] = useState(new Chess());
  const [viewingGame, setViewingGame] = useState<any>(null);

  const fetchGames = async () => {
    if (!username.trim()) return;
    setLoading(true);
    setError('');
    
    try {
      // Must use User-Agent if calling directly, but fetch in browser can't easily override User-Agent due to CORS.
      // Chess.com PubAPI allows standard browser requests without custom UA mostly, but let's see.
      const archivesRes = await fetch(`https://api.chess.com/pub/player/${username.trim().toLowerCase()}/games/archives`);
      if (!archivesRes.ok) throw new Error('Profile not found or API Error');
      const archivesData = await archivesRes.json();
      
      if (!archivesData.archives || archivesData.archives.length === 0) {
         throw new Error('No games found for this user.');
      }
      
      const lastMonthUrl = archivesData.archives[archivesData.archives.length - 1];
      const gamesRes = await fetch(lastMonthUrl);
      if (!gamesRes.ok) throw new Error('Failed to fetch games.');
      const gamesData = await gamesRes.json();
      
      setGames(gamesData.games.reverse().slice(0, 50));
    } catch (err: any) {
      setError(err.message || 'An error occurred.');
    } finally {
      setLoading(false);
    }
  };

  const loadGame = (pgn: string, gameObj: any) => {
    const newGame = new Chess();
    newGame.loadPgn(pgn);
    setGame(newGame);
    setViewingGame(gameObj);
  };

  return (
    <div className="min-h-screen p-8 flex flex-col md:flex-row gap-8">
      <div className="w-full md:w-1/3 flex flex-col gap-4">
        <h1 className="text-3xl font-bold text-blue-400">ChessInsight Web</h1>
        
        <div className="flex gap-2">
          <input 
            type="text" 
            placeholder="Chess.com Username" 
            className="flex-1 bg-zinc-900 border border-zinc-800 rounded px-4 py-2"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
            onKeyDown={(e) => e.key === 'Enter' && fetchGames()}
          />
          <button 
            className="bg-blue-600 hover:bg-blue-500 px-4 rounded text-white font-bold flex items-center gap-2"
            onClick={fetchGames}
          >
            {loading ? <Loader2 className="animate-spin" /> : <Search size={20}/>}
          </button>
        </div>

        {error && <div className="text-red-400 bg-red-950/30 p-4 rounded">{error}</div>}

        <div className="flex-1 overflow-y-auto max-h-[70vh] flex flex-col gap-2">
          {games.map((g, i) => (
            <div 
              key={i} 
              onClick={() => loadGame(g.pgn, g)}
              className="bg-zinc-900 border border-zinc-800 p-3 rounded cursor-pointer hover:border-blue-500 transition-colors"
            >
              <div className="font-bold flex justify-between">
                <span>{g.white.username} <span className="text-zinc-500 font-normal">vs</span> {g.black.username}</span>
              </div>
              <div className="text-sm text-zinc-400 mt-1">
                {new Date(g.end_time * 1000).toLocaleDateString()} • {g.time_class}
              </div>
            </div>
          ))}
        </div>
      </div>
      
      <div className="flex-1 bg-zinc-900 rounded border border-zinc-800 flex items-center justify-center p-8">
        {viewingGame ? (
            <div className="w-full max-w-[600px] flex flex-col gap-4">
               <div className="flex justify-between font-bold text-lg">
                  <span>{viewingGame.black.username} ({viewingGame.black.rating})</span>
               </div>
               <div className="w-full shadow-2xl">
                 <Chessboard position={game.fen()} boardWidth={600} />
               </div>
               <div className="flex justify-between font-bold text-lg">
                  <span>{viewingGame.white.username} ({viewingGame.white.rating})</span>
               </div>
            </div>
        ) : (
            <div className="text-zinc-500">Select a game to view the board</div>
        )}
      </div>
    </div>
  );
}

export default App;
// Trigger deployment
