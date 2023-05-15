package view.UI;

import controller.GameController;
import model.Enum.PlayerColor;
import model.User.User;

import javax.swing.*;
import java.awt.*;

public class WinLabel extends JLabel {
    private double initFrameResize = 1.0;
    private final Color backgroundColor = new Color(246, 240, 226);
    private final Color borderColor = new Color(0,0,0);
    private final int cornerRadius = 10;

    public WinLabel(PlayerColor winPlayer, double initFrameResize) {
        this.initFrameResize = initFrameResize;

        JLabel usernameLabel = new JLabel();
        usernameLabel.setForeground(winPlayer.getColor());
        usernameLabel.setBounds((int) (initFrameResize * 20), 0, (int) (initFrameResize * 80), (int) (initFrameResize * 50));
        usernameLabel.setFont(new Font("Britannic Bold", Font.BOLD, (int) (initFrameResize * 18)));
        usernameLabel.setHorizontalAlignment(LEFT);
        if (winPlayer == PlayerColor.BLUE) {
            usernameLabel.setText(GameController.user1.getUsername());
        } else {
            usernameLabel.setText(GameController.user2.getUsername());
        }

        JLabel contentLabel1 = new JLabel("wins the game in ");
        contentLabel1.setForeground(Color.BLACK);
        contentLabel1.setBounds((int) (initFrameResize * 100), 0, (int) (initFrameResize * 150), (int) (initFrameResize * 50));
        contentLabel1.setFont(new Font("Britannic Bold", Font.PLAIN, (int) (initFrameResize * 18)));
        contentLabel1.setHorizontalAlignment(CENTER);

        JLabel turnLabel = new JLabel(String.format("%d", GameController.turnCount));
        turnLabel.setForeground(winPlayer.getColor());
        turnLabel.setBounds((int) (initFrameResize * 250), 0, (int) (initFrameResize * 50), (int) (initFrameResize * 50));
        turnLabel.setFont(new Font("Britannic Bold", Font.BOLD, (int) (initFrameResize * 18)));
        turnLabel.setHorizontalAlignment(CENTER);

        JLabel contentLabel2 = new JLabel("turns");
        contentLabel2.setForeground(Color.BLACK);
        contentLabel2.setBounds((int) (initFrameResize * 300), 0, (int) (initFrameResize * 50), (int) (initFrameResize * 50));
        contentLabel2.setFont(new Font("Britannic Bold", Font.PLAIN, (int) (initFrameResize * 18)));
        contentLabel2.setHorizontalAlignment(CENTER);

        add(usernameLabel);
        add(contentLabel1);
        add(turnLabel);
        add(contentLabel2);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fill round rectangular
        g2d.setColor(backgroundColor);
        g2d.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 5, cornerRadius, cornerRadius);


        // Paint round border
        g2d.setColor(borderColor);
        g2d.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 5, cornerRadius, cornerRadius);

        super.paintComponent(g);
    }
}
