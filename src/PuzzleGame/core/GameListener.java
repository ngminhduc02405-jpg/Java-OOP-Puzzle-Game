package PuzzleGame.core;

import PuzzleGame.level.Level;

public interface GameListener {
    void onStateChanged(GameState newState);

    void onLevelLoaded(Level level);

    void onGameWon();
}
