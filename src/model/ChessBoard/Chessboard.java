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

    public void registerController(GameController gameController){
        this.gameController = gameController;
    }

    //This method is originally a private method, but I change it to public for testing.
    //The only place that calls this method for testing is in GameController.java.
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

    public void moveChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (!isValidMove(src, dest)) {
            throw new IllegalArgumentException("Illegal chess move!");
        }
        ChessPiece srcPiece = getChessPieceAt(src);
        Move move = srcPiece.moveTo(src, dest, this.grid);
        gameController.addMove(move);
        setChessPiece(dest, removeChessPiece(src));

        if(getGridAt(dest).isTrap() && getGridAt(dest).getOwner() != srcPiece.getOwner()){
            srcPiece.setTrapped(true);
        }
        if(getGridAt(src).isTrap() && getGridAt(src).getOwner() != srcPiece.getOwner()){
            srcPiece.setTrapped(false);
        }
    }

    public void captureChessPiece(ChessboardPoint src, ChessboardPoint dest) {
        if (isValidCapture(src, dest)) {
            throw new IllegalArgumentException("Illegal chess capture!");
        }
        ChessPiece srcPiece = getChessPieceAt(src);
        Move move = srcPiece.moveTo(src, dest, this.grid);
        if(move.getCapturedPiece().getOwner() == PlayerColor.BLUE) {
            bluePieces.remove(move.getCapturedPiece());
        }else{
            redPieces.remove(move.getCapturedPiece());
        }
        gameController.addMove(move);
        getGridAt(dest).removePiece();
        setChessPiece(dest, removeChessPiece(src));

        if(getGridAt(dest).isTrap() && getGridAt(dest).getOwner() != srcPiece.getOwner()){
            srcPiece.setTrapped(true);
        }
    }

    public Cell[][] getGrid() {
        return grid;
    }
    public PlayerColor getChessPieceOwner(ChessboardPoint point) {
        return getGridAt(point).getPiece().getOwner();
    }

    public boolean isValidMove(ChessboardPoint src, ChessboardPoint dest) {
        ChessPiece srcPiece = getChessPieceAt(src);
        if(srcPiece.moveTo(src, dest, this.grid) == null){
            return false;
        }
        return true;
    }


    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) {
        ChessPiece srcPiece = getChessPieceAt(src);
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
        System.out.println("    0  1  2  3  4  5  6 ");
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            System.out.printf(" %d ",i);
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if(board[i][j].getPiece() == null) {
                    System.out.print(" * ");
                }else{
                    System.out.printf(" %c ",board[i][j].getPiece().getName().charAt(0));
                }
            }
            System.out.println();
        }
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
            setChessPiece(lastMove.getToPoint(), lastMove.getCapturedPiece());
            if(this.getGridAt(lastMove.getToPoint()).isTrap()){
                lastMove.getCapturedPiece().setTrapped(true);
            }
            if(!this.getGridAt(lastMove.getToPoint()).isTrap()){
                lastMove.getCapturedPiece().setTrapped(false);
            }
        }
    }
    public boolean isGameOver(){
        if(this.bluePieces.size() == 0 || this.redPieces.size() == 0){
            return true;
        }
        return false;
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
                    if(category == Category.ELEPHANT) {
                        newBoard[i][j].setPiece(new ElephantChessPiece(board[i][j].getPiece().getOwner()));
                    }else if(category == Category.LION){
                        newBoard[i][j].setPiece(new LionChessPiece(board[i][j].getPiece().getOwner()));
                    }else if(category == Category.TIGER) {
                        newBoard[i][j].setPiece(new TigerChessPiece(board[i][j].getPiece().getOwner()));
                    }else if(category == Category.LEOPARD){
                        newBoard[i][j].setPiece(new LeopardChessPiece(board[i][j].getPiece().getOwner()));
                    }else if(category == Category.WOLF){
                        newBoard[i][j].setPiece(new WolfChessPiece(board[i][j].getPiece().getOwner()));
                    }else if(category == Category.DOG){
                        newBoard[i][j].setPiece(new DogChessPiece(board[i][j].getPiece().getOwner()));
                    }else if(category == Category.CAT){
                        newBoard[i][j].setPiece(new CatChessPiece(board[i][j].getPiece().getOwner()));
                    }else if(category == Category.RAT){
                        newBoard[i][j].setPiece(new RatChessPiece(board[i][j].getPiece().getOwner()));
                    }
                }
                newBoard[i][j].setOwner(board[i][j].getOwner());
                newBoard[i][j].setDen(board[i][j].isDen());
                newBoard[i][j].setTrap(board[i][j].isTrap());
                newBoard[i][j].setRiver(board[i][j].isRiver());
            }
        }
        return newBoard;
    }
}
