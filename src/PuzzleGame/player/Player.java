package PuzzleGame.player;

import PuzzleGame.map.Map;

public class Player {
    private int x;
    private int y;
    private Direction direction = Direction.DOWN;

    // Constructor bắt buộc phải là public để GamePanel gọi được
    public Player(int x, int y) {
        this.x = x;
        this.y = y;
    }

    // Các hàm Get/Set tọa độ (Dùng cho GamePanel vẽ hình)
    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    // --- CÁC HÀM PLACEHOLDER CHO NGƯỜI 2 LÀM LOGIC ---

    // Hàm xử lý di chuyển của nhân vật
    public void move(Direction direction, Map map) {
        this.direction = direction;
        int newX = x;
        int newY = y;

        switch (direction) {
            case UP:
                newY--;
                break;
            case DOWN:
                newY++;
                break;
            case LEFT:
                newX--;
                break;
            case RIGHT:
                newX++;
                break;
            default:
                return;
        }

        // Kiểm tra ô đích có đi được không (hàng = newY, cột = newX)
        if (map != null && map.isWalkable(newY, newX)) {
            x = newX;
            y = newY;
        }
    }
}