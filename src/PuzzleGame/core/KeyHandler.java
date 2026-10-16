package PuzzleGame.core;

import PuzzleGame.ui.GamePanel;
import PuzzleGame.player.Direction;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class KeyHandler implements KeyListener {
    private GamePanel gp;

    public KeyHandler(GamePanel gp) {
        this.gp = gp;
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();

        if (gp.gameState == gp.titleState) {
            if (code == KeyEvent.VK_ENTER) {
                gp.resetGame();
            }
        } else if (gp.gameState == gp.playState) {
            if (code == KeyEvent.VK_P) {
                GameManager.getInstance().pauseGame();
                return;
            }

            // Bấm R để Restart màn chơi ngay lập tức (không cần vào pause menu)
            if (code == KeyEvent.VK_R) {
                gp.resetGame();
                return;
            }

            // Phím F1 hỗ trợ kiểm tra nhanh màn hình chiến thắng (Win screen của UI)
            if (code == KeyEvent.VK_F1) {
                GameManager.getInstance().winGame();
                return;
            }

            if (gp.testPlayer == null || gp.map == null) {
                return;
            }

            // Điều khiển di chuyển nhân vật bằng phím mũi tên hoặc WASD
            switch (code) {
                case KeyEvent.VK_W:
                case KeyEvent.VK_UP:
                    gp.testPlayer.move(Direction.UP, gp.map);
                    break;

                case KeyEvent.VK_S:
                case KeyEvent.VK_DOWN:
                    gp.testPlayer.move(Direction.DOWN, gp.map);
                    break;

                case KeyEvent.VK_A:
                case KeyEvent.VK_LEFT:
                    gp.testPlayer.move(Direction.LEFT, gp.map);
                    break;

                case KeyEvent.VK_D:
                case KeyEvent.VK_RIGHT:
                    gp.testPlayer.move(Direction.RIGHT, gp.map);
                    break;
            }

            // Kiểm tra dịch chuyển nếu người chơi bước vào Teleporter
            if (gp.teleManager != null) {
                gp.teleManager.checkTeleport(gp.testPlayer);
            }

            // Kiểm tra nhặt chìa khóa
            if (gp.testKey != null && !gp.testKey.isCollected()) {
                if (gp.testPlayer.getX() == gp.testKey.getX() && gp.testPlayer.getY() == gp.testKey.getY()) {
                    gp.testKey.collect();
                }
            }

            // Kiểm tra đẩy hộp Box
            if (gp.testBox != null && gp.testPlayer.getX() == gp.testBox.getX() && gp.testPlayer.getY() == gp.testBox.getY()) {
                Direction dir = gp.testPlayer.getDirection();
                boolean pushed = gp.testBox.push(gp.testPlayer, dir, gp.map);
                if (!pushed) {
                    // Không đẩy được (kẹt tường) -> hoàn tác vị trí người chơi
                    switch (dir) {
                        case UP:    gp.testPlayer.setY(gp.testPlayer.getY() + 1); break;
                        case DOWN:  gp.testPlayer.setY(gp.testPlayer.getY() - 1); break;
                        case LEFT:  gp.testPlayer.setX(gp.testPlayer.getX() + 1); break;
                        case RIGHT: gp.testPlayer.setX(gp.testPlayer.getX() - 1); break;
                    }
                }
            }

            // Kiểm tra công tắc Switch kích hoạt khi Box đè lên -> Mở cửa Door
            if (gp.testSwitch != null && gp.testBox != null) {
                gp.testSwitch.check(gp.testBox);
                if (gp.testDoor != null) {
                    if (gp.testSwitch.isActivated()) {
                        gp.testDoor.open();
                    } else {
                        gp.testDoor.close();
                    }
                }
            }

            // Khi bước vào cửa đã mở -> Chiến thắng (Win screen)
            if (gp.testDoor != null && gp.testDoor.isOpened()) {
                if (gp.testPlayer.getX() == gp.testDoor.getX() && gp.testPlayer.getY() == gp.testDoor.getY()) {
                    GameManager.getInstance().completeLevel();
                }
            }
        } else if (gp.gameState == gp.pauseState) {
            if (code == KeyEvent.VK_P) {
                GameManager.getInstance().resumeGame();
            }
            if (code == KeyEvent.VK_R) {
                gp.resetGame();
            }
        } else if (gp.gameState == gp.winState) {
            if (code == KeyEvent.VK_R) {
                gp.resetGame();
            }
            if (code == KeyEvent.VK_ENTER) {
                GameManager.getInstance().backToMenu();
            }
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {}
}