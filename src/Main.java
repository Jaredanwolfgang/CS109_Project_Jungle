import controller.GameController;
import model.ChessBoard.Chessboard;
import view.Frame.ChessGameFrame;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        view.Frame.MusicPlayerFrame.playMusic();
        new view.Frame.InitFrame(800,600);
//        SwingUtilities.invokeLater(() -> {
//            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
//            GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard());
//            mainFrame.setVisible(true);
//        });
    }
}
