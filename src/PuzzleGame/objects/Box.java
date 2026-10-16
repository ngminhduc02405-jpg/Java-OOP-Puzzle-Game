package PuzzleGame.objects;

import PuzzleGame.map.Map;
import PuzzleGame.player.Direction;
import PuzzleGame.player.Player;

/**
 * Lớp đại diện cho khối hộp (Box) có thể đẩy được trong puzzle.
 * Kế thừa từ GameObject.
 */
public class Box extends GameObject {

    public Box(int x, int y) {
        super(x, y);
        loadImage("assets/images/puzzle objects/box.png");
    }

    public void setX(int x) {
        setPosition(x, getY());
    }

    public void setY(int y) {
        setPosition(getX(), y);
    }

    // Giữ phương thức để tích hợp logic của nhóm
    public boolean push(Player player, Direction direction, Map map) {
        int nextX = getX();
        int nextY = getY();

        switch (direction) {
            case UP:    nextY--; break;
            case DOWN:  nextY++; break;
            case LEFT:  nextX--; break;
            case RIGHT: nextX++; break;
        }

        // Kiểm tra xem ô tiếp theo có đi được (không phải tường) không
        if (map != null && map.isWalkable(nextY, nextX)) {
            setPosition(nextX, nextY);
            return true;
        }
        return false;
    }

    public void move(int dx, int dy) {
        setPosition(getX() + dx, getY() + dy);
    }
}