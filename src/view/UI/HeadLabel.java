package view.UI;

import model.User.User;

import javax.swing.*;
import java.awt.*;

public class HeadLabel extends JLabel {
    private final Color backgroundColor = new Color(224, 230, 250);
    private final Color borderColor = new Color(0, 0, 0);
    private final int cornerRadius = 10;


    public HeadLabel() {
        JLabel rankLabel = new JLabel("");
        rankLabel.setForeground(Color.BLACK);
        rankLabel.setBounds(0, 0, 50, 35);
        rankLabel.setFont(new Font("Britannic Bold", Font.BOLD, 16));
        rankLabel.setHorizontalAlignment(CENTER);
        rankLabel.setOpaque(false);

        JLabel usernameLabel = new JLabel("Username");
        usernameLabel.setForeground(Color.BLACK);
        usernameLabel.setBounds(20, 0, 150, 35);
        usernameLabel.setFont(new Font("Britannic Bold", Font.BOLD, 16));
        usernameLabel.setHorizontalAlignment(LEFT);

        JLabel scoreLabel = new JLabel("Score");
        scoreLabel.setForeground(Color.BLACK);
        scoreLabel.setBounds(200, 0, 100, 35);
        scoreLabel.setFont(new Font("Britannic Bold", Font.PLAIN, 16));
        scoreLabel.setHorizontalAlignment(CENTER);

        JLabel winrateLabel = new JLabel("Win Rate");
        winrateLabel.setForeground(Color.BLACK);
        winrateLabel.setBounds(300, 0, 100, 35);
        winrateLabel.setFont(new Font("Britannic Bold", Font.PLAIN, 16));
        winrateLabel.setHorizontalAlignment(CENTER);

        add(rankLabel);
        add(usernameLabel);
        add(scoreLabel);
        add(winrateLabel);

    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fill round rectangular
        g2d.setColor(backgroundColor);
        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 15, cornerRadius, cornerRadius);

        // Paint round border
        g2d.setColor(borderColor);
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 15, cornerRadius, cornerRadius);

        super.paintComponent(g);
    }

}
