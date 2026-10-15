package PuzzleGame.level;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LevelManager {

    private final List<Level> levels = new ArrayList<>();
    private int currentIndex = 0;

    public LevelManager() {
        loadDefaultLevels();
    }

    public void addLevel(Level level) { levels.add(level); }

    public List<Level> getLevels() { return Collections.unmodifiableList(levels); }

    public int getLevelCount() { return levels.size(); }

    public Level getCurrentLevel() {
        if (levels.isEmpty()) return null;
        return levels.get(currentIndex);
    }

    public int getCurrentIndex() { return currentIndex; }

    public boolean isLastLevel() { return currentIndex >= levels.size() - 1; }

    public Level loadFirstLevel() {
        currentIndex = 0;
        return getCurrentLevel();
    }

    public boolean loadLevel(int index) {
        if (index < 0 || index >= levels.size()) return false;
        currentIndex = index;
        return true;
    }

    // Trả về null nếu đã là level cuối
    public Level nextLevel() {
        if (isLastLevel()) return null;
        currentIndex++;
