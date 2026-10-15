package PuzzleGame.level;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/*
 * Ký tự map: '#' tường, '.' ô trống, 'P' Player, 'B' Box,
 * 'K' Key, 'D' Door, 'S' Switch, 'T' Teleporter
 */
public class Level {

    public static final char WALL = '#';
    public static final char EMPTY = '.';
    public static final char PLAYER = 'P';
    public static final char BOX = 'B';
    public static final char KEY = 'K';
    public static final char DOOR = 'D';
    public static final char SWITCH = 'S';
    public static final char TELEPORTER = 'T';

    private final int number;
    private final String name;
    private final char[][] data;

    public Level(int number, String name, String[] rows) {
        this.number = number;
        this.name = name;
        this.data = new char[rows.length][];
        for (int r = 0; r < rows.length; r++) {
            this.data[r] = rows[r].toCharArray();
        }
    }

    public int getNumber() { return number; }
    public String getName() { return name; }
    public int getRows() { return data.length; }

    public int getCols() {
        int max = 0;
        for (char[] row : data) max = Math.max(max, row.length);
        return max;
    }

    public char getTile(int row, int col) {
        if (row < 0 || row >= data.length || col < 0 || col >= data[row].length) return WALL;
        return data[row][col];
    }

    public char[][] copyMap() {
        char[][] copy = new char[data.length][];
        for (int r = 0; r < data.length; r++) copy[r] = data[r].clone();
        return copy;
    }

    // Trả về danh sách vị trí (x = cột, y = hàng)
    public List<Point> findAll(char symbol) {
        List<Point> result = new ArrayList<>();
        for (int r = 0; r < data.length; r++) {
            for (int c = 0; c < data[r].length; c++) {
                if (data[r][c] == symbol) result.add(new Point(c, r));
            }
        }
        return result;
    }

    public Point getPlayerStart() {
        List<Point> p = findAll(PLAYER);
        return p.isEmpty() ? null : p.get(0);
    }

    public List<Point> getBoxes()       { return findAll(BOX); }
    public List<Point> getKeys()        { return findAll(KEY); }
    public List<Point> getDoors()       { return findAll(DOOR); }
    public List<Point> getSwitches()    { return findAll(SWITCH); }
    public List<Point> getTeleporters() { return findAll(TELEPORTER); }

    @Override
    public String toString() {
        return "Level " + number + (name == null || name.isEmpty() ? "" : " - " + name);
    }
}
