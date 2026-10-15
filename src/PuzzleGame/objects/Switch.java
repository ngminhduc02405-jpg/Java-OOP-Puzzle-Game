package PuzzleGame.objects;

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
    public void activate() {
        activated = true;
    }
    public void deactivate() {
        activated = false;
    }
}
