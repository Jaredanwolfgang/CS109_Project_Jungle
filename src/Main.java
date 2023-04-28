import controller.GameController;
import model.ChessBoard.Chessboard;
import view.Frame.ChessGameFrame;

import javax.swing.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            ChessGameFrame mainFrame = new ChessGameFrame(1100, 810);
            GameController gameController = new GameController(mainFrame.getChessboardComponent(), new Chessboard());
            mainFrame.setVisible(true);

            //testing code
            System.out.println(gameController.AIMoveTest());
            Scanner input = new Scanner(System.in);
            while(true){
                System.out.println("Enter -1 to exit");
                int x = input.nextInt();
                if(x == -1){
                    System.exit(0);
                }
                if(x == -2){
                    gameController.onPlayerClickUndoButton();
                    continue;
                }
                if(x == -3){
                    gameController.onPlayerClickResetButton();
                    continue;
                }
                int y = input.nextInt();
                gameController.testViaKeyboard(x, y);
            }
        });
    }
}
