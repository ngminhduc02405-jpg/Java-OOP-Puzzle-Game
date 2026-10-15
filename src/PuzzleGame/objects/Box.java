package PuzzleGame.objects;
import PuzzleGame.map.Map;
import PuzzleGame.player.Direction;
import PuzzleGame.player.Player;

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
        // TODO: Người phụ trách logic sẽ triển khai
        return false;
    }

    public void move(int dx, int dy) {
        setPosition(getX() + dx, getY() + dy);
    }
}
