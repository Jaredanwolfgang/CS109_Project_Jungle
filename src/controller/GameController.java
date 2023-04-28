package controller;


import listener.GameListener;
import model.ChessBoard.Move;
import model.ChessPieces.ChessPiece;
import model.Enum.Constant;
import model.Enum.PlayerColor;
import model.ChessBoard.Chessboard;
import model.ChessBoard.ChessboardPoint;
import model.User.User;
import view.CellComponent;
import view.ChessComponent.ChessComponent;
import view.ChessComponent.ElephantChessComponent;
import view.ChessboardComponent;

import java.util.ArrayList;

/**
 * Controller is the connection between model and view,
 * when a Controller receive a request from a view, the Controller
 * analyzes and then hands over to the model for processing
 * [in this demo the request methods are onPlayerClickCell() and onPlayerClickChessPiece()]
 *
*/
//FIXME: Add user and component here.
public class GameController implements GameListener {
    private Chessboard model;
    private ChessboardComponent view;
    private PlayerColor currentPlayer;
    private ArrayList<Move> allMovesOnBoard;
    // Record whether there is a selected piece before
    private ChessboardPoint selectedPoint;

    public GameController(ChessboardComponent view, Chessboard model) {
        this.view = view;
        this.model = model;
        this.currentPlayer = PlayerColor.BLUE;
        this.allMovesOnBoard = new ArrayList<>();

        model.registerController(this);
        view.registerController(this);
        initialize();
        view.initiateChessComponent(model);
        view.repaint();
    }
    //TODO: The initialize method should be applied upon different chess pieces.
    private void initialize() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {

            }
        }
    }

    // after a valid move swap the player
    private void swapColor() {
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
    }

    public void addMove(Move move) {
        allMovesOnBoard.add(move);
    }
    //@Jaredan TODO:The win method does not specify user or component, it only judges whether the game ends.

    private boolean win() {
        if((model.getGrid()[0][3].getPiece() != null && model.getGrid()[0][3].getPiece().getOwner() == PlayerColor.RED) ||
                (model.getGrid()[8][3].getPiece() != null && model.getGrid()[8][3].getPiece().getOwner() == PlayerColor.BLUE)){
            return true;
        }
        return false;
    }


    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
        if (selectedPoint != null && model.isValidMove(selectedPoint, point)) {
            model.moveChessPiece(selectedPoint, point);
            view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            selectedPoint = null;
            swapColor();
            view.repaint();
            // TODO: if the chess enter Dens or Traps and so on, the win method can be merged here?
        }
    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ChessComponent component) {
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                selectedPoint = point;
                component.setSelected(true);
                component.repaint();

                //Following code is just for debugging.
                //It will print all valid moves of the selected chess piece, once a piece is selected.
                //@Jaredan TODO:Associate the valid Moves with GUI: change the color of the chessboard
                ChessPiece piece = model.getChessPieceAt(point);
                ArrayList<Move> validMoves = piece.getAvailableMoves(point, model.getGrid());
                for (Move move : validMoves) {
                    System.out.println(move);
                }
            }
        } else{
            if (selectedPoint.equals(point)) {
                selectedPoint = null;
                component.setSelected(false);
                component.repaint();
            }
        }
        // TODO: Implement capture function
    }
}
