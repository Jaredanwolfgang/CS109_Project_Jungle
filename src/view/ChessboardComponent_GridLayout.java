package view;

import controller.GameController;
import model.ChessBoard.Cell;
import model.ChessBoard.Chessboard;
import model.ChessBoard.ChessboardPoint;
import model.ChessPieces.ChessPiece;
import view.ChessComponent.*;

import javax.swing.*;
import java.awt.*;
import java.util.HashSet;
import java.util.Set;

import static model.Enum.Constant.CHESSBOARD_COL_SIZE;
import static model.Enum.Constant.CHESSBOARD_ROW_SIZE;

public class ChessboardComponent_GridLayout extends JComponent {
    private final CellComponent[][] gridComponents = new CellComponent[CHESSBOARD_ROW_SIZE.getNum()][CHESSBOARD_COL_SIZE.getNum()];
    private int chessSize;
    private final Set<ChessboardPoint> riverCell = new HashSet<>();
    private GameController gameController;

    public ChessboardComponent_GridLayout(int chessSize, GameController gameController) {
        this.chessSize = chessSize;
        this.gameController = gameController;
        setLayout(new GridLayout(9, 7));//Use GridLayout to realize the chessboard
        setSize(chessSize * 7, chessSize * 9);
        enableEvents(AWTEvent.MOUSE_EVENT_MASK);// Allow mouse events to occur
        System.out.printf("chessboard width, height = [%d : %d], chess size = %d\n", chessSize * 7, chessSize * 9, chessSize);


    }
//    public void initiateChessComponent(Chessboard chessboard) {
//        Cell[][] grid = chessboard.getGrid();
//        for (int i = 0; i < CHESSBOARD_ROW_SIZE.getNum(); i++) {
//            for (int j = 0; j < CHESSBOARD_COL_SIZE.getNum(); j++) {
//                // TODO: Implement the initialization checkerboard
//                if (grid[i][j].getPiece() != null) {
//                    ChessPiece chessPiece = grid[i][j].getPiece();
//                    System.out.println(chessPiece.getOwner());
//                    switch(chessPiece.getCategory()){
//                        case ELEPHANT -> gridComponents[i][j].add( new ElephantChessComponent(chessPiece.getOwner(), CHESS_SIZE));
//                        case LION -> gridComponents[i][j].add(new LionChessComponent(chessPiece.getOwner(), CHESS_SIZE));
//                        case TIGER -> gridComponents[i][j].add(new TigerChessComponent(chessPiece.getOwner(), CHESS_SIZE));
//                        case LEOPARD -> gridComponents[i][j].add(new LeopardChessComponent(chessPiece.getOwner(), CHESS_SIZE));
//                        case WOLF -> gridComponents[i][j].add(new WolfChessComponent(chessPiece.getOwner(), CHESS_SIZE));
//                        case DOG -> gridComponents[i][j].add(new DogChessComponent(chessPiece.getOwner(), CHESS_SIZE));
//                        case CAT -> gridComponents[i][j].add(new CatChessComponent(chessPiece.getOwner(), CHESS_SIZE));
//                        case RAT -> gridComponents[i][j].add(new RatChessComponent(chessPiece.getOwner(), CHESS_SIZE));
//                    };
//
//                }
//            }
//        }
//    }
}
