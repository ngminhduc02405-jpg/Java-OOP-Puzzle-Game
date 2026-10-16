package PuzzleGame.ui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

/**
 * Lớp quản lý giao diện người dùng (UI) cho game.
 * Bao gồm: Menu chính, HUD trong game, màn hình Pause và Win.
 * Tất cả đều được vẽ bằng Java2D, không cần file ảnh.
 */
public class UI {
    private GamePanel gp;

    // === FONT CHỮ ===
    private Font titleFont;
    private Font subtitleFont;
    private Font normalFont;
    private Font smallFont;
    private Font tinyFont;

    // === MÀU SẮC CHÍNH ===
    private static final Color COLOR_GOLD = new Color(255, 215, 0);
    private static final Color COLOR_GOLD_DARK = new Color(200, 160, 0);
    private static final Color COLOR_ACCENT = new Color(0, 200, 255);
    private static final Color COLOR_BG_DARK_BLUE = new Color(15, 15, 45);
    private static final Color COLOR_BG_DARK_PURPLE = new Color(40, 10, 60);
    private static final Color COLOR_PANEL_BG = new Color(20, 20, 50, 220);
    private static final Color COLOR_PANEL_BORDER = new Color(100, 150, 255, 150);

    // === BIẾN ANIMATION ===
    private int frameCounter = 0; // Bộ đếm frame cho hiệu ứng nhấp nháy

    public UI(GamePanel gp) {
        this.gp = gp;
        titleFont = new Font("SansSerif", Font.BOLD, 56);
        subtitleFont = new Font("SansSerif", Font.ITALIC, 20);
        normalFont = new Font("SansSerif", Font.BOLD, 22);
        smallFont = new Font("SansSerif", Font.PLAIN, 18);
        tinyFont = new Font("SansSerif", Font.PLAIN, 14);
    }

    /**
     * Cập nhật bộ đếm frame (gọi mỗi frame từ GamePanel.update())
     */
    public void update() {
        frameCounter++;
        if (frameCounter > 10000) {
            frameCounter = 0; // Tránh overflow
        }
    }

    /**
     * Vẽ UI tương ứng với trạng thái game hiện tại
     */
    public void draw(Graphics2D g2) {
        // Bật anti-aliasing
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

        if (gp.gameState == gp.titleState) {
            drawMainMenu(g2);
        } else if (gp.gameState == gp.playState) {
            drawHUD(g2);
        } else if (gp.gameState == gp.pauseState) {
            drawHUD(g2);
            drawPauseScreen(g2);
        } else if (gp.gameState == gp.winState) {
            drawWinScreen(g2);
        }
    }

    // =========================================================================
    // MÀN HÌNH MENU CHÍNH
    // =========================================================================
    private void drawMainMenu(Graphics2D g2) {
        int sw = gp.screenWidth;
        int sh = gp.screenHeight;

        // --- Nền gradient (Xanh đậm -> Tím đậm) ---
        GradientPaint bgGradient = new GradientPaint(0, 0, COLOR_BG_DARK_BLUE, 0, sh, COLOR_BG_DARK_PURPLE);
        g2.setPaint(bgGradient);
        g2.fillRect(0, 0, sw, sh);

        // --- Vẽ các hình trang trí nền (puzzle pieces / hình tròn mờ) ---
        drawMenuDecorations(g2, sw, sh);

        // --- Tiêu đề "PUZZLE GAME" ---
        g2.setFont(titleFont);
        String title = "PUZZLE GAME";
        int titleX = getXForCenteredText(title, g2);
        int titleY = sh / 2 - 80;

        // Bóng đổ
        g2.setColor(new Color(0, 0, 0, 120));
        g2.drawString(title, titleX + 3, titleY + 3);

        // Viền vàng (vẽ text offset nhiều hướng)
        g2.setColor(COLOR_GOLD_DARK);
        g2.drawString(title, titleX - 2, titleY);
        g2.drawString(title, titleX + 2, titleY);
        g2.drawString(title, titleX, titleY - 2);
        g2.drawString(title, titleX, titleY + 2);

        // Chữ chính màu trắng
        g2.setColor(Color.WHITE);
        g2.drawString(title, titleX, titleY);

        // --- Phụ đề ---
        g2.setFont(subtitleFont);
        g2.setColor(new Color(180, 180, 220));
        String subtitle = "A Java OOP Project";
        int subX = getXForCenteredText(subtitle, g2);
        g2.drawString(subtitle, subX, titleY + 35);

        // --- Đường kẻ trang trí ---
        int lineY = titleY + 55;
        int lineWidth = 200;
        g2.setColor(COLOR_ACCENT);
        g2.setStroke(new BasicStroke(2));
        g2.drawLine(sw / 2 - lineWidth / 2, lineY, sw / 2 + lineWidth / 2, lineY);

        // --- Nút "Press ENTER to Start" (nhấp nháy) ---
        g2.setFont(normalFont);
        String startText = "\u25B6  Press ENTER to Start";
        int startX = getXForCenteredText(startText, g2);
        int startY = sh / 2 + 40;

        // Hiệu ứng nhấp nháy: hiện 40 frame, ẩn 20 frame
        boolean showStart = (frameCounter % 60) < 40;
        if (showStart) {
            // Glow effect
            g2.setColor(new Color(0, 200, 255, 60));
            g2.drawString(startText, startX, startY + 1);
            g2.setColor(Color.WHITE);
            g2.drawString(startText, startX, startY);
        }

        // --- Hướng dẫn điều khiển ---
        g2.setFont(smallFont);
        g2.setColor(new Color(150, 150, 180));
        String controls = "Controls: Arrow Keys to Move  |  R = Restart  |  P = Pause";
        int ctrlX = getXForCenteredText(controls, g2);
        g2.drawString(controls, ctrlX, sh - 60);

        // --- Phiên bản ---
        g2.setFont(tinyFont);
        g2.setColor(new Color(100, 100, 130));
        String version = "v1.0";
        FontMetrics fm = g2.getFontMetrics();
        int versionX = sw - fm.stringWidth(version) - 15;
        g2.drawString(version, versionX, sh - 15);
    }

    /**
     * Vẽ các hình trang trí nền cho menu (các hình tròn/vuông tròn mờ)
     */
    private void drawMenuDecorations(Graphics2D g2, int sw, int sh) {
        // Vẽ các hình tròn mờ ở nhiều vị trí
        int[][] circles = {
            {80, 100, 60}, {sw - 120, 80, 40}, {150, sh - 130, 50},
            {sw - 80, sh - 100, 35}, {sw / 2 + 150, 150, 45},
            {sw / 2 - 200, sh - 80, 30}, {300, 60, 25}
        };

        for (int[] c : circles) {
            // Mỗi hình tròn có màu hơi khác
            int alpha = 15 + (c[0] % 20);
            g2.setColor(new Color(100, 120, 255, alpha));
            g2.fillOval(c[0] - c[2], c[1] - c[2], c[2] * 2, c[2] * 2);

            // Viền mờ
            g2.setColor(new Color(150, 170, 255, alpha + 10));
            g2.setStroke(new BasicStroke(1.5f));
            g2.drawOval(c[0] - c[2], c[1] - c[2], c[2] * 2, c[2] * 2);
        }

        // Vẽ các hình puzzle piece đơn giản (rounded rect với notch)
        drawPuzzlePiece(g2, 60, sh / 2 + 50, 40, new Color(255, 200, 50, 25));
        drawPuzzlePiece(g2, sw - 100, sh / 2 - 30, 35, new Color(50, 200, 255, 25));
    }

    /**
     * Vẽ một mảnh puzzle trang trí (hình vuông tròn góc)
     */
    private void drawPuzzlePiece(Graphics2D g2, int x, int y, int size, Color color) {
        g2.setColor(color);
        g2.fill(new RoundRectangle2D.Float(x, y, size, size, 10, 10));

        // Thêm "nub" (mấu) bên phải
        g2.fillOval(x + size - 5, y + size / 2 - 8, 16, 16);

        // Viền mảnh puzzle
        g2.setColor(new Color(color.getRed(), color.getGreen(), color.getBlue(), color.getAlpha() + 15));
        g2.setStroke(new BasicStroke(1.5f));
        g2.draw(new RoundRectangle2D.Float(x, y, size, size, 10, 10));
    }

    // =========================================================================
    // HUD (Thanh thông tin trong game)
    // =========================================================================
    private void drawHUD(Graphics2D g2) {
        int sw = gp.screenWidth;
        int barHeight = 40;

        // --- Thanh nền bán trong suốt ---
        g2.setColor(new Color(10, 10, 30, 180));
        g2.fillRect(0, 0, sw, barHeight);

        // --- Đường accent phía dưới thanh HUD ---
        GradientPaint accentGrad = new GradientPaint(0, barHeight - 2, COLOR_ACCENT, sw, barHeight - 2, COLOR_GOLD);
        g2.setPaint(accentGrad);
        g2.fillRect(0, barHeight - 2, sw, 2);

        // --- Bên trái: Level ---
        g2.setFont(smallFont);
        g2.setColor(COLOR_GOLD);

        // Icon level nhỏ (hình vuông)
        g2.fillRoundRect(12, 10, 18, 18, 4, 4);
        g2.setColor(new Color(10, 10, 30));
        g2.setFont(tinyFont);
        int levelNumber = gp.getCurrentLevelNumber();
        g2.drawString(Integer.toString(levelNumber), 17, 24);

        g2.setFont(smallFont);
        g2.setColor(Color.WHITE);
        g2.drawString("Level " + levelNumber, 38, 27);

        // --- Giữa: Tên game ---
        g2.setFont(tinyFont);
        g2.setColor(new Color(150, 150, 180));
        String gameName = "PUZZLE GAME";
        int nameX = getXForCenteredText(gameName, g2);
        g2.drawString(gameName, nameX, 26);

        // --- Bên phải: Hướng dẫn ---
        g2.setFont(tinyFont);
        g2.setColor(new Color(120, 120, 160));
        String hint = "R = Restart  |  P = Pause";
        FontMetrics fm = g2.getFontMetrics();
        int hintX = sw - fm.stringWidth(hint) - 15;
        g2.drawString(hint, hintX, 26);
    }

    // =========================================================================
    // MÀN HÌNH PAUSE
    // =========================================================================
    private void drawPauseScreen(Graphics2D g2) {
        int sw = gp.screenWidth;
        int sh = gp.screenHeight;

        // --- Lớp phủ tối bán trong suốt ---
        g2.setColor(new Color(0, 0, 0, 160));
        g2.fillRect(0, 0, sw, sh);

        // --- Panel chính ở giữa ---
        int panelW = 340;
        int panelH = 200;
        int panelX = (sw - panelW) / 2;
        int panelY = (sh - panelH) / 2;

        // Nền panel
        g2.setColor(COLOR_PANEL_BG);
        g2.fill(new RoundRectangle2D.Float(panelX, panelY, panelW, panelH, 20, 20));

        // Viền panel (2 lớp tạo hiệu ứng viền đôi)
        g2.setStroke(new BasicStroke(2));
        g2.setColor(COLOR_PANEL_BORDER);
        g2.draw(new RoundRectangle2D.Float(panelX, panelY, panelW, panelH, 20, 20));
        g2.setColor(new Color(100, 150, 255, 60));
        g2.draw(new RoundRectangle2D.Float(panelX + 4, panelY + 4, panelW - 8, panelH - 8, 16, 16));

        // --- Tiêu đề "PAUSED" ---
        g2.setFont(titleFont);
        String pauseTitle = "\u23F8  PAUSED";
        int titleX = getXForCenteredText(pauseTitle, g2);
        int titleY = panelY + 70;

        // Bóng đổ
        g2.setColor(new Color(0, 0, 0, 100));
        g2.drawString(pauseTitle, titleX + 2, titleY + 2);

        // Chữ chính
        g2.setColor(Color.WHITE);
        g2.drawString(pauseTitle, titleX, titleY);

        // --- Hướng dẫn ---
        g2.setFont(normalFont);
        g2.setColor(new Color(180, 220, 255));
        String resume = "Press P to Resume";
        int resumeX = getXForCenteredText(resume, g2);
        g2.drawString(resume, resumeX, panelY + 120);

        g2.setFont(smallFont);
        g2.setColor(new Color(150, 150, 180));
        String restart = "Press R to Restart";
        int restartX = getXForCenteredText(restart, g2);
        g2.drawString(restart, restartX, panelY + 160);
    }

    // =========================================================================
    // MÀN HÌNH CHIẾN THẮNG
    // =========================================================================
    private void drawWinScreen(Graphics2D g2) {
        int sw = gp.screenWidth;
        int sh = gp.screenHeight;

        // --- Lớp phủ tối với tông vàng ấm ---
        g2.setColor(new Color(20, 15, 0, 200));
        g2.fillRect(0, 0, sw, sh);

        // --- Vẽ các ngôi sao trang trí ---
        drawStars(g2, sw, sh);

        // --- Panel chính ---
        int panelW = 420;
        int panelH = 260;
        int panelX = (sw - panelW) / 2;
        int panelY = (sh - panelH) / 2;

        // Nền panel gradient vàng
        GradientPaint panelGrad = new GradientPaint(
            panelX, panelY, new Color(50, 40, 10, 220),
            panelX, panelY + panelH, new Color(30, 20, 5, 220)
        );
        g2.setPaint(panelGrad);
        g2.fill(new RoundRectangle2D.Float(panelX, panelY, panelW, panelH, 24, 24));

        // Viền vàng
        g2.setStroke(new BasicStroke(3));
        g2.setColor(COLOR_GOLD);
        g2.draw(new RoundRectangle2D.Float(panelX, panelY, panelW, panelH, 24, 24));
        g2.setColor(new Color(255, 215, 0, 60));
        g2.draw(new RoundRectangle2D.Float(panelX + 5, panelY + 5, panelW - 10, panelH - 10, 20, 20));

        // --- Tiêu đề "YOU WIN!" với hiệu ứng glow ---
        g2.setFont(titleFont);
        String winTitle = "\uD83C\uDF89  YOU WIN!";
        int titleX = getXForCenteredText(winTitle, g2);
        int titleY = panelY + 75;

        // Glow effect (vẽ nhiều lớp offset)
        g2.setColor(new Color(255, 200, 0, 40));
        for (int i = -3; i <= 3; i++) {
            for (int j = -3; j <= 3; j++) {
                g2.drawString(winTitle, titleX + i, titleY + j);
            }
        }

        // Bóng đổ
        g2.setColor(new Color(150, 100, 0));
        g2.drawString(winTitle, titleX + 2, titleY + 2);

        // Chữ chính màu vàng
        g2.setColor(COLOR_GOLD);
        g2.drawString(winTitle, titleX, titleY);

        // --- Phụ đề "Congratulations!" ---
        g2.setFont(subtitleFont);
        g2.setColor(new Color(255, 240, 200));
        String congrats = "Congratulations!";
        int congratsX = getXForCenteredText(congrats, g2);
        g2.drawString(congrats, congratsX, titleY + 40);

        // --- Đường kẻ trang trí ---
        g2.setColor(new Color(255, 215, 0, 100));
        g2.setStroke(new BasicStroke(1.5f));
        int lineY2 = titleY + 55;
        g2.drawLine(panelX + 40, lineY2, panelX + panelW - 40, lineY2);

        // --- Nút tiếp tục (nhấp nháy) ---
        g2.setFont(normalFont);
        String continueText = "Press ENTER to Return to Menu";
        int continueX = getXForCenteredText(continueText, g2);
        int continueY = panelY + panelH - 50;

        boolean showContinue = (frameCounter % 50) < 35;
        if (showContinue) {
            g2.setColor(Color.WHITE);
            g2.drawString(continueText, continueX, continueY);
        }
    }

    /**
     * Vẽ các ngôi sao trang trí cho màn hình Win
     */
    private void drawStars(Graphics2D g2, int sw, int sh) {
        // Các vị trí ngôi sao cố định
        int[][] starPositions = {
            {100, 80}, {sw - 130, 100}, {200, sh - 120},
            {sw - 80, sh - 90}, {sw / 2 - 180, 120},
            {sw / 2 + 160, sh - 100}, {50, sh / 2},
            {sw - 60, sh / 2 - 50}
        };

        for (int i = 0; i < starPositions.length; i++) {
            int x = starPositions[i][0];
            int y = starPositions[i][1];
            int size = 6 + (i % 3) * 3; // Kích thước khác nhau

            // Hiệu ứng nhấp nháy: mỗi ngôi sao nhấp nháy lệch pha
            int phase = (frameCounter + i * 15) % 60;
            int alpha = (phase < 30) ? (150 + phase * 3) : (240 - (phase - 30) * 3);
            alpha = Math.max(80, Math.min(255, alpha));

            g2.setColor(new Color(255, 215, 0, alpha));
            drawStar(g2, x, y, size);
        }
    }

    /**
     * Vẽ một ngôi sao 4 cánh tại vị trí (cx, cy) với bán kính r
     */
    private void drawStar(Graphics2D g2, int cx, int cy, int r) {
        // Vẽ hình thoi (4 cánh sao)
        int[] xPoints = {cx, cx + r, cx, cx - r};
        int[] yPoints = {cy - r, cy, cy + r, cy};
        g2.fillPolygon(xPoints, yPoints, 4);

        // Điểm sáng ở giữa
        g2.setColor(new Color(255, 255, 200, 200));
        g2.fillOval(cx - 2, cy - 2, 4, 4);
    }

    // =========================================================================
    // HÀM TIỆN ÍCH
    // =========================================================================

    /**
     * Tính toán vị trí X để căn giữa text trên màn hình
     */
    private int getXForCenteredText(String text, Graphics2D g2) {
        int length = (int) g2.getFontMetrics().getStringBounds(text, g2).getWidth();
        return gp.screenWidth / 2 - length / 2;
    }
}