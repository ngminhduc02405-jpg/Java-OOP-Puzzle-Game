package PuzzleGame.teleport;

/**
 * Lớp đại diện cho một cổng dịch chuyển (Teleporter) trên bản đồ.
 * Mỗi teleporter có tọa độ (x, y) và một điểm đến (destination).
 */
public class Teleporter {
    private int x;
    private int y;
    private Teleporter destination;

    public Teleporter(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void setDestination(Teleporter destination) {
        this.destination = destination;
    }

    public Teleporter getDestination() {
        return destination;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
