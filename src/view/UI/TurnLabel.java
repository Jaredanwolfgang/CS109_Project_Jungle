package view.UI;

import controller.GameController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class TurnLabel extends JLabel {
    private Color backgroundColor;
    private int cornerRadius;

    public TurnLabel(String text, Color bgColor, int cornerRadius) {
        super(text);
        this.backgroundColor = bgColor;
        this.cornerRadius = cornerRadius;
        setOpaque(false); // 设置透明度
        this.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
                JLabel source = (JLabel) e.getSource();
                ToolTipManager.sharedInstance().setInitialDelay(0);
                Color blueColor = new Color(78, 150, 253);
                Color redColor = new Color(218, 60, 45);
                if (backgroundColor.getRGB() == blueColor.getRGB()) {
                    source.setToolTipText(String.format("Player: %s Score: %.2f Win rate: %.2f", GameController.user1.getUsername(), GameController.user1.getScore(), GameController.user1.getWinRate()));
                } else if (backgroundColor.getRGB() == redColor.getRGB()) {
                    source.setToolTipText(String.format("Player: %s Score: %.2f Win rate: %.2f", GameController.user2.getUsername(), GameController.user2.getScore(), GameController.user2.getWinRate()));
                }
            }

            @Override
            public void mouseExited(MouseEvent e) {
                JLabel source = (JLabel) e.getSource();
                source.setToolTipText(null);
            }
        });
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
    }

    public Color getBackgroundColor() {
        return backgroundColor;
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 绘制圆角矩形
        g2d.setColor(backgroundColor);
        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);

        // 绘制边框
        g2d.setColor(Color.WHITE);
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, cornerRadius, cornerRadius);

        super.paintComponent(g);
    }

}
