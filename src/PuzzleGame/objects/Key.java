
package PuzzleGame.objects;

public class Key extends GameObject {
    private boolean collected;

    public Key(int x, int y) {
        super(x, y);
        collected = false;
        loadImage("assets/images/puzzle objects/key.png");
    }
    public boolean isCollected() {
        return collected;
    }
    public void collect() {
        collected = true;
    }
}
