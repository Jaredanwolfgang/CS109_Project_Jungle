import javax.swing.*;
import java.awt.*;

public class Test extends JFrame {

    private JPanel boardPanel;

    public Test() {
        super("Chessboard Frame");

        // create the board panel and add components to it
        boardPanel = new JPanel(new GridLayout(9, 7));
        for (int i = 0; i < 9 * 7; i++) {
            JPanel cell = new JPanel();
            cell.setBackground((i / 7 + i % 7) % 2 == 0 ? Color.WHITE : Color.BLACK);
            boardPanel.add(cell);
        }

        // add the board panel to the frame's content pane
        getContentPane().add(boardPanel);

        // set the frame size and center it on the screen
        setSize(500, 400);
        setLocationRelativeTo(null);

        // set empty borders around the board panel to create blank space
/*
        int borderSize = 80;
        boardPanel.setBorder(BorderFactory.createEmptyBorder(borderSize, borderSize, borderSize, borderSize));
*/

        // set a layout manager on the content pane to center the board panel
        setLayout(new GridBagLayout());
        add(boardPanel, new GridBagConstraints());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Test frame = new Test();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
        });
    }
}

