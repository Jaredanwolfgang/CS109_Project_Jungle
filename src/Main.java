import controller.GameController;
import model.ChessBoard.Chessboard;
import model.Enum.Mode;
import model.User.User;
import view.Dialog.SuccessDialog;
import view.Frame.ChessGameFrame;
import view.Frame.InitFrame;
import view.Frame.LoginFrame;
import view.Frame.StartFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            view.Frame.MusicPlayerFrame.playMusic();
            new view.Frame.InitFrame(800, 600);
        });

    }
}
