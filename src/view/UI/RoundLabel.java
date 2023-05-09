package view.UI;

import javax.swing.*;
import java.awt.*;

public class RoundLabel extends JLabel {
    private Color backgroundColor;
    private int cornerRadius;
    public RoundLabel(String text, Color backgroundColor, int cornerRadius) {
        super(text);
        this.backgroundColor = backgroundColor;
        this.cornerRadius = cornerRadius;
        setOpaque(false); // 设置透明度
    }

    public void setBackgroundColor(Color backgroundColor) {
        this.backgroundColor = backgroundColor;
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
