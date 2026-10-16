package PuzzleGame.objects;

/**
 * Lớp đại diện cho công tắc sàn (Switch).
 * Kích hoạt khi có Box đè lên.
 */
public class Switch extends GameObject {
    private boolean activated;

    public Switch(int x, int y) {
        super(x, y);
        activated = false;
        loadImage("assets/images/puzzle objects/switch.png");
    }

    public boolean isActivated() {
        return activated;
    }

    // Giữ thêm alias isActive() để tương thích ngược với code cũ của nhóm
    public boolean isActive() {
        return activated;
    }

    public void activate() {
        activated = true;
    }

    public void deactivate() {
        activated = false;
    }

    /**
     * Kiểm tra xem box có nằm đè lên switch không
     */
    public void check(Box box) {
        if (box != null && box.getX() == x && box.getY() == y) {
            activate();
        } else {
            deactivate();
        }
    }
}