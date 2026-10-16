package PuzzleGame.objects;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Lớp trừu tượng cơ sở cho tất cả các đối tượng tương tác trong game
 * (Box, Key, Door, Switch...)
 * Đảm nhiệm quản lý tọa độ (x, y) và hình ảnh (image).
 */
public abstract class GameObject {
    protected int x;
    protected int y;
    protected BufferedImage image;

    public GameObject(int x, int y) {
        this.x = x;
        this.y = y;
    }

    protected void loadImage(String path) {
        try {
            image = ImageIO.read(new File(path));
            if (image == null) {
                System.out.println("File khong phai anh hop le: " + path);
            }
        } catch (IOException e) {
            System.out.println("Khong the tai anh: " + path);
            image = null;
        }
    }

    public BufferedImage getImage() {
        return image;
    }

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
}