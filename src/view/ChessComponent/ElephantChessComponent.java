package view.ChessComponent;


import model.Enum.PlayerColor;
import model.User.User;
import view.ChessboardComponent;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.*;
import java.util.Vector;

//Draw the chesspiece on the board.
public class ElephantChessComponent extends ChessComponent {

    public ElephantChessComponent(PlayerColor owner, int size) {
        setOwner(owner);
        setSelected(false);
        setSize(size/2, size/2);
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        ImageIcon img = new ImageIcon("Image\\Chess\\1_Elephant.png");
        Image image = img.getImage();
        this.setBorder(new RoundBorder(10, getOwner().getColor()));
        g2.drawImage(image, 0, 0, getWidth(), getHeight(), null);
        RoundRectangle2D clipShape = new RoundRectangle2D.Float(0, 0, getWidth(), getHeight(), 10, 10);
        g2.setClip(clipShape);
        if (isSelected()) {
            g2.drawImage(image, 0, 0, getWidth(), getHeight(), getOwner().getColor(), null);
        }
    }
}
