package view.Frame;

import javax.swing.*;

public class GameFrame extends JFrame{
    public GameFrame() {
        this.setSize(880,640);
        this.setTitle("Jungle Game");
        this.setAlwaysOnTop(true);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setVisible(true);
    }
}
