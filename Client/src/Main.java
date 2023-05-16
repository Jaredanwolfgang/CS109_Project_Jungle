import controller.GameController;
import model.ChessBoard.Chessboard;
import view.Frame.Frame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GameController gameController = new GameController(new Frame(), new Chessboard());
        });
    }
}
