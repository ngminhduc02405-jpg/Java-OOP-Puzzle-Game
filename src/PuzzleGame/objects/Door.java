
package PuzzleGame.objects;
public class Door extends GameObject {
    private boolean opened;
    public Door(int x, int y) {
        super(x, y);
        opened = false;
        loadImage("assets/images/puzzle objects/door closed.png");
    }
    public boolean isOpened() {
        return opened;
    }
    public void open() {
        opened = true;
        loadImage("assets/images/puzzle objects/door opened.png");
    }
    public void close() {
        opened = false;
        loadImage("assets/images/puzzle objects/door closed.png");
    }
}
