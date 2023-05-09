package model.ChessBoard;

import controller.GameController;
import model.ChessPieces.*;
import model.Enum.Category;
import model.Enum.Constant;
import model.Enum.PlayerColor;

import java.awt.*;
import java.util.ArrayList;

/**
 * This class store the real chess information.
 * The Chessboard has 9*7 cells, and each cell has a position for chess
 */
public class Chessboard {
    private Cell[][] grid;
    private ArrayList<ChessPiece> bluePieces;
    private ArrayList<ChessPiece> redPieces;
    private GameController gameController;

    public Chessboard() {
        this.grid = new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];//19X19
        this.bluePieces = new ArrayList<>();
        this.redPieces = new ArrayList<>();
        initGrid();
        initPieces();
    }

    //initGrid and initPieces methods are used to initialize the chessboard.
    //They are also used to reset the chessboard.
    private void initGrid() {
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                grid[i][j] = new Cell();
            }
        }

        grid[0][3].setDen(true);
        grid[0][3].setOwner(PlayerColor.BLUE);
        grid[8][3].setDen(true);
        grid[8][3].setOwner(PlayerColor.RED);

        grid[0][2].setTrap(true);
        grid[0][2].setOwner(PlayerColor.BLUE);
        grid[0][4].setTrap(true);
        grid[0][4].setOwner(PlayerColor.BLUE);
        grid[1][3].setTrap(true);
        grid[1][3].setOwner(PlayerColor.BLUE);
        grid[8][2].setTrap(true);
        grid[8][2].setOwner(PlayerColor.RED);
        grid[8][4].setTrap(true);
        grid[8][4].setOwner(PlayerColor.RED);
        grid[7][3].setTrap(true);
        grid[7][3].setOwner(PlayerColor.RED);

        grid[3][1].setRiver(true);
        grid[3][2].setRiver(true);
        grid[3][4].setRiver(true);
        grid[3][5].setRiver(true);
        grid[4][1].setRiver(true);
        grid[4][2].setRiver(true);
        grid[4][4].setRiver(true);
        grid[4][5].setRiver(true);
        grid[5][1].setRiver(true);
        grid[5][2].setRiver(true);
        grid[5][4].setRiver(true);
        grid[5][5].setRiver(true);
    }
    private void initPieces() {
        //This method place all chess pieces on the chessboard.
        bluePieces.clear();
        redPieces.clear();

        ChessPiece piece;
        piece = new LionChessPiece(PlayerColor.BLUE);
        grid[0][0].setPiece(piece);
        bluePieces.add(piece);
        piece = new TigerChessPiece(PlayerColor.BLUE);
        grid[0][6].setPiece(piece);
        bluePieces.add(piece);
        piece = new DogChessPiece(PlayerColor.BLUE);
        grid[1][1].setPiece(piece);
        bluePieces.add(piece);
        piece = new CatChessPiece(PlayerColor.BLUE);
        grid[1][5].setPiece(piece);
        bluePieces.add(piece);
        piece = new RatChessPiece(PlayerColor.BLUE);
        grid[2][0].setPiece(piece);
        bluePieces.add(piece);
        piece = new LeopardChessPiece(PlayerColor.BLUE);
        grid[2][2].setPiece(piece);
        bluePieces.add(piece);
        piece = new WolfChessPiece(PlayerColor.BLUE);
        grid[2][4].setPiece(piece);
        bluePieces.add(piece);
        piece = new ElephantChessPiece(PlayerColor.BLUE);
        grid[2][6].setPiece(piece);
        bluePieces.add(piece);

        piece = new TigerChessPiece(PlayerColor.RED);
        grid[8][0].setPiece(piece);
        redPieces.add(piece);
        piece = new LionChessPiece(PlayerColor.RED);
        grid[8][6].setPiece(piece);
        redPieces.add(piece);
        piece = new CatChessPiece(PlayerColor.RED);
        grid[7][1].setPiece(piece);
        redPieces.add(piece);
        piece = new DogChessPiece(PlayerColor.RED);
        grid[7][5].setPiece(piece);
        redPieces.add(piece);
        piece = new ElephantChessPiece(PlayerColor.RED);
        grid[6][0].setPiece(piece);
        redPieces.add(piece);
        piece = new WolfChessPiece(PlayerColor.RED);
        grid[6][2].setPiece(piece);
        redPieces.add(piece);
        piece = new LeopardChessPiece(PlayerColor.RED);
        grid[6][4].setPiece(piece);
        redPieces.add(piece);
        piece = new RatChessPiece(PlayerColor.RED);
        grid[6][6].setPiece(piece);
        redPieces.add(piece);
    }

    //Only used once when model is created in GameController.java.
    public void registerController(GameController gameController){
        this.gameController = gameController;
    }

    public Move moveChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        //First check if the move is valid.
        //If not, throw an exception and end the method.
        if (!isValidMove(src, dest)) {
            throw new IllegalArgumentException("Invalid move(piece can't reach the destination)");
        }

        //If the move is valid, generate a move instance.
        ChessPiece srcPiece = getChessPieceAt(src);
        Move move = srcPiece.moveTo(src, dest, this.grid);

        //Move the piece on the chessboard.
        setChessPiece(dest, removeChessPiece(src));

        //Check if the chess piece gets trapped or gets out of the trap.
        if(getGridAt(dest).isTrap() && getGridAt(dest).getOwner() != srcPiece.getOwner()){
            srcPiece.setTrapped(true);
        }
        if(getGridAt(src).isTrap() && getGridAt(src).getOwner() != srcPiece.getOwner()){
            srcPiece.setTrapped(false);
        }

        //If the move is invalid, an exception will be thrown and the method will end ahead.
        //So we can guarantee that the move is valid here.
        return move;
    }

    public Move captureChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        //First check if the capture is valid.
        //If not, throw an exception and end the method.
        if (!isValidCapture(src, dest)) {
            throw new IllegalArgumentException("Invalid move(piece can't reach the destination or piece can't capture the target)");
        }

        //If the capture is valid, generate a move instance.
        ChessPiece srcPiece = getChessPieceAt(src);
        Move move = srcPiece.moveTo(src, dest, this.grid);

        //Remove the captured piece from the list.
        if(move.getCapturedPiece().getOwner() == PlayerColor.BLUE) {
            bluePieces.remove(move.getCapturedPiece());
        }else{
            redPieces.remove(move.getCapturedPiece());
        }

        //Move the chess piece on the chessboard.
        getGridAt(dest).removePiece();
        setChessPiece(dest, removeChessPiece(src));

        //Check if the chess piece gets trapped.
        if(getGridAt(dest).isTrap() && getGridAt(dest).getOwner() != srcPiece.getOwner()){
            srcPiece.setTrapped(true);
        }

        //If the move is invalid, an exception will be thrown and the method will end ahead.
        //So we can garantee that the move is valid here.
        return move;
    }

    public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest) {
        ChessPiece srcPiece = getChessPieceAt(src);
        System.out.println(srcPiece.getName());
        if(srcPiece == null){
            return false;
        }
        if(getChessPieceAt(dest) != null){
            return false;
        }
        //If the move is invalid, the moveTo method will return null.
        if(srcPiece.moveTo(src, dest, this.grid) == null){
            return false;
        }
        return true;
    }

    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) {
        ChessPiece srcPiece = getChessPieceAt(src);
        if(srcPiece == null){
            return false;
        }
        if(getChessPieceAt(dest) == null){
            return false;
        }
        //If the move is invalid, the moveTo method will return null.
        if(srcPiece.moveTo(src, dest, this.grid) == null){
            return false;
        }
        return true;
    }

    public static int getDistance(ChessboardPoint point1, ChessboardPoint point2){
        return Math.abs(point1.getRow() - point2.getRow()) + Math.abs(point1.getCol() - point2.getCol());
    }

    //This method is only for testing.
    public static void printChessBoard(Cell[][] board){
        StringBuilder sb = new StringBuilder();
        sb.append("   0  1  2  3  4  5  6 \n");
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            sb.append(i).append(" ");
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if(board[i][j].getPiece() == null) {
                    sb.append(" * ");
                }else{
                    sb.append(" ").append(board[i][j].getPiece().getName().charAt(0)).append(" ");
                }
            }
            sb.append("\n");
        }
        System.out.println(sb);
    }

    public void undoMove(Move lastMove) {
        setChessPiece(lastMove.getFromPoint(), removeChessPiece(lastMove.getToPoint()));
        if(this.getGridAt(lastMove.getFromPoint()).isTrap()){
            lastMove.getMovingPiece().setTrapped(true);
        }
        if(!this.getGridAt(lastMove.getFromPoint()).isTrap()){
            lastMove.getMovingPiece().setTrapped(false);
        }

        if(lastMove.isDoesCapture()) {
            if(lastMove.getCapturedPiece().getOwner() == PlayerColor.BLUE) {
                bluePieces.add(lastMove.getCapturedPiece());
            }else{
                redPieces.add(lastMove.getCapturedPiece());
            }
            setChessPiece(lastMove.getToPoint(), lastMove.getCapturedPiece());
            if(this.getGridAt(lastMove.getToPoint()).isTrap()){
                lastMove.getCapturedPiece().setTrapped(true);
            }
            if(!this.getGridAt(lastMove.getToPoint()).isTrap()){
                lastMove.getCapturedPiece().setTrapped(false);
            }
        }
    }

    public PlayerColor noPieceLeft(){
        if(this.bluePieces.size() == 0){
            return PlayerColor.BLUE;
        }
        if(this.redPieces.size() == 0){
            return PlayerColor.RED;
        }
        return null;
    }
    public static ArrayList<Move> getAllPossibleMoveOnBoard(Cell[][] board, Color PlayerColor){
        ArrayList<Move> moves = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 7; j++) {
                if(board[i][j].getPiece() != null && board[i][j].getPiece().getOwner().getColor() == PlayerColor){
                    ArrayList<Move> possibleMoves = board[i][j].getPiece().getAvailableMoves(new ChessboardPoint(i,j),board);
                    moves.addAll(possibleMoves);
                }
            }
        }
        return moves;
    }
    public static Cell[][] cloneBoard(Cell[][] board){
        Cell[][] newBoard = new Cell[9][7];
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                newBoard[i][j] = new Cell();
                if(board[i][j].getPiece() != null){
                    Category category = board[i][j].getPiece().getCategory();
                    switch (category) {
                        case ELEPHANT -> newBoard[i][j].setPiece(new ElephantChessPiece(board[i][j].getPiece().getOwner()));
                        case LION -> newBoard[i][j].setPiece(new LionChessPiece(board[i][j].getPiece().getOwner()));
                        case TIGER -> newBoard[i][j].setPiece(new TigerChessPiece(board[i][j].getPiece().getOwner()));
                        case LEOPARD -> newBoard[i][j].setPiece(new LeopardChessPiece(board[i][j].getPiece().getOwner()));
                        case WOLF -> newBoard[i][j].setPiece(new WolfChessPiece(board[i][j].getPiece().getOwner()));
                        case DOG -> newBoard[i][j].setPiece(new DogChessPiece(board[i][j].getPiece().getOwner()));
                        case CAT -> newBoard[i][j].setPiece(new CatChessPiece(board[i][j].getPiece().getOwner()));
                        case RAT -> newBoard[i][j].setPiece(new RatChessPiece(board[i][j].getPiece().getOwner()));
                    }
                }
            }
        }
        newBoard[0][3].setDen(true);
        newBoard[0][3].setOwner(PlayerColor.BLUE);
        newBoard[8][3].setDen(true);
        newBoard[8][3].setOwner(PlayerColor.RED);

        newBoard[0][2].setTrap(true);
        newBoard[0][2].setOwner(PlayerColor.BLUE);
        newBoard[0][4].setTrap(true);
        newBoard[0][4].setOwner(PlayerColor.BLUE);
        newBoard[1][3].setTrap(true);
        newBoard[1][3].setOwner(PlayerColor.BLUE);
        newBoard[8][2].setTrap(true);
        newBoard[8][2].setOwner(PlayerColor.RED);
        newBoard[8][4].setTrap(true);
        newBoard[8][4].setOwner(PlayerColor.RED);
        newBoard[7][3].setTrap(true);
        newBoard[7][3].setOwner(PlayerColor.RED);

        newBoard[3][1].setRiver(true);
        newBoard[3][2].setRiver(true);
        newBoard[3][4].setRiver(true);
        newBoard[3][5].setRiver(true);
        newBoard[4][1].setRiver(true);
        newBoard[4][2].setRiver(true);
        newBoard[4][4].setRiver(true);
        newBoard[4][5].setRiver(true);
        newBoard[5][1].setRiver(true);
        newBoard[5][2].setRiver(true);
        newBoard[5][4].setRiver(true);
        newBoard[5][5].setRiver(true);
        return newBoard;
    }

    public void reset() {
        initGrid();
        initPieces();
    }

    //getter and setter
    public ChessPiece getChessPieceAt(ChessboardPoint point) {
        return getGridAt(point).getPiece();
    }

    private Cell getGridAt(ChessboardPoint point) {
        return grid[point.getRow()][point.getCol()];
    }

    private ChessPiece removeChessPiece(ChessboardPoint point) {
        ChessPiece chessPiece = getChessPieceAt(point);
        getGridAt(point).removePiece();
        return chessPiece;
    }

    private void setChessPiece(ChessboardPoint point, ChessPiece chessPiece) {
        getGridAt(point).setPiece(chessPiece);
    }

    public Cell[][] getGrid() {
        return grid;
    }

    public PlayerColor getChessPieceOwner(ChessboardPoint point) {
        return getGridAt(point).getPiece().getOwner();
    }
}
