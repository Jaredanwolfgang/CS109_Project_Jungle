package controller;


import listener.GameListener;
import model.ChessBoard.Move;
import model.ChessPieces.ChessPiece;
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
*/
public class GameController implements GameListener {

    private Chessboard model;
    private ChessboardComponent view;
    private PlayerColor currentPlayer;

    // Record all moves on the board.
    private ArrayList<Move> allMovesOnBoard;

    // Record whether there is a selected piece before and where
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

    //Judge if there is a winner in two ways.
    //First: One player's piece enters the other player's den.
    //Second: After a capture, one player has no piece left.
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
            //Try to move the selected piece to the clicked cell.
            try{
                allMovesOnBoard.add(model.moveChessPiece(selectedPoint, point));
                //If the move is invalid, the try sentence ends here.

                //Here should be code for GUI to repaint the board.(One piece moved)

                selectedPoint = null;
                this.swapColor();
            }catch (IllegalArgumentException e){
                //Print error message.
                System.out.println(e);
            }
        }
        if(win()){

            // TO DO: What should we do after one player wins?

        }
    }

    // click a cell with a chess
    @Override
    public void onPlayerClickChessPiece(ChessboardPoint point, ElephantChessComponent component) {
        if (selectedPoint == null) {
            if (model.getChessPieceOwner(point) == currentPlayer) {
                //If the clicked piece is the current player's piece, select it.
                selectedPoint = point;

                //Following code is just for debugging.
                //It will print all valid moves of the selected chess piece, once a piece is selected.
                /*ChessPiece piece = model.getChessPieceAt(point);
                ArrayList<Move> validMoves = piece.getAvailableMoves(point, model.getGrid());
                for (Move move : validMoves) {
                    System.out.println(move);
                }*/

            }
        }else{
            if (selectedPoint.equals(point)) {
                //If the clicked piece is the selected piece, deselect it.
                selectedPoint = null;
            }else{
                if(model.getChessPieceOwner(point) == currentPlayer){
                    //If the clicked piece is the current player's piece, select it.
                    selectedPoint = point;
                }else{
                    //Try to capture the clicked piece with the selected piece.
                    try{
                        allMovesOnBoard.add(model.captureChessPiece(selectedPoint,point));
                        //If the capture is invalid, the try sentence ends here.

                        //Here should be code for GUI to repaint the board.(One piece captured)

                        selectedPoint = null;
                        this.swapColor();
                    }catch (IllegalArgumentException e){
                        //Print error message.
                        System.out.println(e);
                    }
                    if(win()){

                        // TO DO: What should we do after one player wins?

                    }
                }
            }
        }
    }

    @Override
    public void onPlayerClickUndoButton() {
        if (allMovesOnBoard.size() > 0) {
            Move lastMove = allMovesOnBoard.remove(allMovesOnBoard.size() - 1);
            model.undoMove(lastMove);

            //Here should be code for GUI to repaint the board.(Move undone)

            this.swapColor();
            selectedPoint = null;
        }else{
            //Print error message.
            System.out.println("No move to undo");
        }
    }

    @Override
    public void onPlayerClickResetButton() {
        selectedPoint = null;
        model.reset();
        this.currentPlayer = PlayerColor.BLUE;
        this.allMovesOnBoard.clear();

        //Here should be code for GUI to repaint the board.(Board reset)

    }

    @Override
    public void onPlayerClickSaveButton() {

        //TO DO: Decide how to save the game.

    }

    @Override
    public void onPlayerClickLoadButton() {

        //TO DO: Decide how to load the game.

    }
    /*
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
    }*/
}
