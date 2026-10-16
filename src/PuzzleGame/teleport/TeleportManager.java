package PuzzleGame.teleport;

import PuzzleGame.player.Player;
import java.util.ArrayList;
import java.util.List;

/**
 * Lớp quản lý các cổng dịch chuyển (TeleportManager).
 * Cho phép tạo cặp teleporter và kiểm tra dịch chuyển cho Player.
 */
public class TeleportManager {
    private List<Teleporter> teleporters = new ArrayList<>();

    public void addTeleporterPair(int x1, int y1, int x2, int y2) {
        Teleporter t1 = new Teleporter(x1, y1);
        Teleporter t2 = new Teleporter(x2, y2);

        t1.setDestination(t2);
        t2.setDestination(t1);

        teleporters.add(t1);
        teleporters.add(t2);
    }

    public void addTeleporter(Teleporter teleporter) {
        teleporters.add(teleporter);
    }

    public List<Teleporter> getTeleporters() {
        return teleporters;
    }

    public boolean checkTeleport(Player player) {
        if (player == null) return false;
        for (Teleporter t : teleporters) {
            if (player.getX() == t.getX() && player.getY() == t.getY()) {
                Teleporter dest = t.getDestination();
                if (dest != null) {
                    player.setPosition(dest.getX(), dest.getY());
                    return true;
                }
            }
        }
        return false;
    }

    public void clear() {
        teleporters.clear();
    }
}
