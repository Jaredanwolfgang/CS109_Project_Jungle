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
            //System.out.println(gameController.AIMoveTest());
            Scanner input = new Scanner(System.in);
            System.out.println("Test mode: input made via keyboard");
            while(true){
                System.out.println("Current game mode: " + gameController.getGameMode());
                StringBuilder sb = new StringBuilder();
                sb.append("Enter -1 to exit");
                sb.append("\n");
                sb.append("Enter -2 to undo");
                sb.append("\n");
                sb.append("Enter -3 to reset");
                sb.append("\n");
                sb.append("Enter -4 to save");
                sb.append("\n");
                sb.append("Enter -5 to load");
                sb.append("\n");
                sb.append("Enter -6 to test user login");
                sb.append("\n");
                sb.append("Enter -7 to test online pvp mode");
                sb.append("\n");
                sb.append("Enter -8 to test local pve mode");
                sb.append("\n");
                sb.append("Enter -9 to test local pvp mode");
                sb.append("\n");
                sb.append("Enter -10 to test AI move");
                sb.append("\n");
                sb.append("Enter -11 to login");
                sb.append("\n");
                sb.append("Enter -12 to reset game mode");
                sb.append("\n");
                System.out.print(sb);
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
                if(x == -4){
                    gameController.onPlayerClickSaveButton("save.txt");
                    continue;
                }
                if(x == -5){
                    gameController.onPlayerClickLoadButton("save.txt");
                    continue;
                }
                if(x == -6){
                    System.out.println(gameController.onPlayerClickLoginButton("xyc","234567"));
                    System.out.println(gameController.onPlayerClickLoginButton("AI_MCTS","000000"));
                    continue;
                }
                if(x == -7){
                    gameController.onPlayerSelectOnlinePVPMode();
                    continue;
                }
                if(x == -8){
                    gameController.onPlayerSelectLocalPVEMode();
                    continue;
                }
                if(x == -9){
                    System.out.println(gameController.onPlayerSelectLocalPVPMode("xyc","234567"));
                    continue;
                }
                if(x == -10){
                    gameController.onPlayerClickAIMoveButton();
                    continue;
                }
                if(x == -11){
                    System.out.println(gameController.onPlayerClickLoginButton("wmx","123456"));
                    continue;
                }
                if(x == -12){
                    gameController.onPlayerExitGameFrame();
                    continue;
                }
                int y = input.nextInt();
                gameController.testViaKeyboard(x, y);
            }
        });
    }
}
