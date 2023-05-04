import controller.GameController;
import model.ChessBoard.Chessboard;
import model.Enum.Mode;
import model.User.User;
import view.Dialog.SuccessDialog;
import view.Frame.*;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new MusicPlayerFrame();
            Frame frame = new Frame();
        });
    }
}
