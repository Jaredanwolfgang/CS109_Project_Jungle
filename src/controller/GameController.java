package controller;


import listener.GameListener;
import model.AI.AI_MCTS;
import model.ChessBoard.Move;
import model.ChessPieces.ChessPiece;
import model.Enum.Constant;
import model.Enum.PlayerColor;
import model.ChessBoard.Chessboard;
import model.ChessBoard.ChessboardPoint;
import view.CellComponent;
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
public class GameController implements GameListener {


    private Chessboard model;
    private ChessboardComponent view;
    private PlayerColor currentPlayer;

    // Record all moves on the board.
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
        view.initiateChessComponent(model);
        view.repaint();
    }

    // after a valid move swap the player
    private void swapColor() {
        currentPlayer = currentPlayer == PlayerColor.BLUE ? PlayerColor.RED : PlayerColor.BLUE;
    }

    public void addMove(Move move) {
        allMovesOnBoard.add(move);
    }
    private boolean win() {
        if((model.getGrid()[0][3].getPiece() != null && model.getGrid()[0][3].getPiece().getOwner() == PlayerColor.RED) ||
                (model.getGrid()[8][3].getPiece() != null && model.getGrid()[8][3].getPiece().getOwner() == PlayerColor.BLUE) ||
                model.noPieceLeft()){
            return true;
        }
        return false;
    }
    // click an empty cell
    @Override
    public void onPlayerClickCell(ChessboardPoint point, CellComponent component) {
        if (selectedPoint != null) {
            try{
                model.moveChessPiece(selectedPoint, point);
                selectedPoint = null;
                this.swapColor();
            }catch (IllegalArgumentException e){
                System.out.println(e);
            }
            //view.setChessComponentAtGrid(point, view.removeChessComponentAtGrid(selectedPoint));
            //view.repaint();
        }
        if(win()){

        }
    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ElephantChessComponent component) {
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point).equals(currentPlayer)) {
                selectedPoint = point;
                //component.setSelected(true);
                //component.repaint();

                //Following code is just for debugging.
                //It will print all valid moves of the selected chess piece, once a piece is selected.
                ChessPiece piece = model.getChessPieceAt(point);
                ArrayList<Move> validMoves = piece.getAvailableMoves(point, model.getGrid());
                for (Move move : validMoves) {
                    System.out.println(move);
                }
            }
        } else{
            if (selectedPoint.equals(point)) {
                selectedPoint = null;
                //component.setSelected(false);
                //component.repaint();
            }else{
                if(model.getChessPieceAt(point).getOwner() == currentPlayer){
                    selectedPoint = point;
                }else{
                    try{
                        model.captureChessPiece(selectedPoint,point);
                        selectedPoint = null;
                        this.swapColor();
                        if(win()){

                        }
                    }catch (IllegalArgumentException e){
                        System.out.println(e);
                    }
                }
            }
        }
    }

    @Override
    public void onPlayerClickUndoButton() {
        if (allMovesOnBoard.size() > 0) {
            selectedPoint = null;
            Move lastMove = allMovesOnBoard.remove(allMovesOnBoard.size() - 1);
            model.undoMove(lastMove);
            this.swapColor();
        }else{
            System.out.println("No move to undo");
        }
    }

    @Override
    public void onPlayerClickResetButton() {
        selectedPoint = null;
        model.reset();
        this.currentPlayer = PlayerColor.BLUE;
        this.allMovesOnBoard.clear();
    }

    @Override
    public void onPlayerClickSaveButton() {

    }

    @Override
    public void onPlayerClickLoadButton() {

    }

    public void testViaKeyboard(int x,int y){
        ChessboardPoint point = new ChessboardPoint(x,y);
        if(model.getChessPieceAt(point) == null){
            onPlayerClickCell(point,null);
        }else{
            onPlayerClickChessPiece(point,null);
        }
        Chessboard.printChessBoard(model.getGrid());
        if(selectedPoint != null){
            System.out.printf("Selected piece is %s at point (%d , %d)\n",model.getChessPieceAt(selectedPoint).getName(),selectedPoint.getRow(),selectedPoint.getCol());
        }else{
            System.out.println("No point is selected");
        }
    }
    public Move AIMoveTest(){
        return AI_MCTS.findBestOneMove(model.getGrid(), currentPlayer.getColor());
    }
}
