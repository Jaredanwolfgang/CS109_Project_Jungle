package view.ChessComponent;


import model.Enum.PlayerColor;
import model.User.User;
import view.ChessboardComponent;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.image.*;
import java.util.Vector;

//Draw the chesspiece on the board.
public class ElephantChessComponent extends ChessComponent {
    private int size;

    public ElephantChessComponent(PlayerColor owner, int size) {
        setOwner(owner);
        setSelected(false);
        this.size = size;
        setSize(size, size);
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        ImageIcon img = new ImageIcon("Image\\Chess\\1_Elephant.png");
        Image image = img.getImage();
        this.setBorder(new RoundBorder(10,getOwner().getColor()));
        g2.drawImage(image, 0, 0, 35, 35, null);
        if(isSelected()){
            this.setBorder(new RoundBorder(10,getOwner().getColor()));
            g2.drawImage(image, 0, 0, 35, 35, getOwner().getColor(), null);
        }
    }

    private static class RoundBorder extends AbstractBorder {
        private final int radius;
        private final Color color;

        public RoundBorder(int radius, Color color) {
            this.radius = radius;
            this.color = color;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(color);
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2d.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
            g2d.dispose();
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius + 1, radius + 1, radius + 2, radius);
        }

        @Override
        public Insets getBorderInsets(Component c, Insets insets) {
            insets.left = insets.right = radius + 1;
            insets.top = radius + 1;
            insets.bottom = radius + 2;
            return insets;
        }
    }
}
