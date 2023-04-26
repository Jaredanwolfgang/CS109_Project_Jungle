package model.ChessBoard;

import controller.GameController;
import model.ChessPieces.*;
import model.Enum.Constant;
import model.Enum.PlayerColor;

import java.util.ArrayList;

/**
 * This class store the real chess information.
 * The Chessboard has 9*7 cells, and each cell has a position for chess
 */
public class Chessboard {
    private Cell[][] grid;

    private GameController gameController;

    public Chessboard() {
        this.grid = new Cell[Constant.CHESSBOARD_ROW_SIZE.getNum()][Constant.CHESSBOARD_COL_SIZE.getNum()];//19X19
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
        grid[0][0].setPiece(new LionChessPiece(PlayerColor.BLUE));
        grid[0][6].setPiece(new TigerChessPiece(PlayerColor.BLUE));
        grid[1][1].setPiece(new DogChessPiece(PlayerColor.BLUE));
        grid[1][5].setPiece(new CatChessPiece(PlayerColor.BLUE));
        grid[2][0].setPiece(new RatChessPiece(PlayerColor.BLUE));
        grid[2][2].setPiece(new LeopardChessPiece(PlayerColor.BLUE));
        grid[2][4].setPiece(new WolfChessPiece(PlayerColor.BLUE));
        grid[2][6].setPiece(new ElephantChessPiece(PlayerColor.BLUE));

        grid[8][0].setPiece(new TigerChessPiece(PlayerColor.RED));
        grid[8][6].setPiece(new LionChessPiece(PlayerColor.RED));
        grid[7][1].setPiece(new CatChessPiece(PlayerColor.RED));
        grid[7][5].setPiece(new DogChessPiece(PlayerColor.RED));
        grid[6][0].setPiece(new ElephantChessPiece(PlayerColor.RED));
        grid[6][2].setPiece(new WolfChessPiece(PlayerColor.RED));
        grid[6][4].setPiece(new LeopardChessPiece(PlayerColor.RED));
        grid[6][6].setPiece(new RatChessPiece(PlayerColor.RED));
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
        ArrayList<Move> moves = srcPiece.getAvailableMoves(src , grid);
        for (Move move : moves) {
            if (move.getToPoint().equals(dest)){
                gameController.addMove(move);
                break;
            }
        }
        getGridAt(dest).removePiece();
        setChessPiece(dest, removeChessPiece(src));
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
    public void printChessBoard(){
        System.out.println("    0  1  2  3  4  5  6 ");
        for (int i = 0; i < Constant.CHESSBOARD_ROW_SIZE.getNum(); i++) {
            System.out.printf(" %d ",i);
            for (int j = 0; j < Constant.CHESSBOARD_COL_SIZE.getNum(); j++) {
                if(this.grid[i][j].getPiece() == null) {
                    System.out.print(" * ");
                }else{
                    System.out.printf(" %c ",this.grid[i][j].getPiece().getName().charAt(0));
                }
            }
            System.out.println();
        }
    }
}
