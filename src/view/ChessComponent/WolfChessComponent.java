package view.ChessComponent;

import model.Enum.PlayerColor;

import javax.swing.*;
import java.awt.*;

public class WolfChessComponent extends ChessComponent{
    private int size;

    public WolfChessComponent(PlayerColor owner, int size) {
        setOwner(owner);
        setSelected(false);
        this.size = size;
        setSize(size, size);
        setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        ImageIcon img = new ImageIcon("Image\\Chess\\5_Wolf.png");
        Image image = img.getImage();
        this.setBorder(new RoundBorder(10,getOwner().getColor()));
        g2.drawImage(image, 0, 0, 35, 35, null);
        if(isSelected()){
            this.setBorder(new RoundBorder(10,getOwner().getColor()));
            g2.drawImage(image, 0, 0, 35, 35, getOwner().getColor(), null);
        }
    }
}
