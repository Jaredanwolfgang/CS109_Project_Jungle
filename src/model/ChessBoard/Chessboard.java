package model.ChessBoard;

import controller.GameController;
import model.ChessPieces.ChessPiece;
import model.ChessPieces.ElephantChessPiece;
import model.Enum.Category;
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
        grid[8][3].setDen(true);

        grid[0][2].setTrap(true);
        grid[0][4].setTrap(true);
        grid[1][3].setTrap(true);
        grid[8][2].setTrap(true);
        grid[8][4].setTrap(true);
        grid[7][3].setTrap(true);

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
        grid[0][0].setPiece(new ElephantChessPiece(PlayerColor.BLUE));
        grid[8][6].setPiece(new ElephantChessPiece(PlayerColor.RED));
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
        ArrayList<Move> moves = srcPiece.getAvailableMoves(src , grid);
        for (Move move : moves) {
            if (move.getToPoint().equals(dest)){
                gameController.addMove(move);
                break;
            }
        }
        setChessPiece(dest, removeChessPiece(src));
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
        ArrayList<Move> moves = srcPiece.getAvailableMoves(src , grid);
        for (Move move : moves) {
            if (move.getToPoint().equals(dest) && !move.isDoesCapture() && getChessPieceAt(dest) == null) {
                return true;
            }
        }
        return false;
    }


    public boolean isValidCapture(ChessboardPoint src, ChessboardPoint dest) {
        ChessPiece srcPiece = getChessPieceAt(src);
        ArrayList<Move> moves = srcPiece.getAvailableMoves(src , grid);
        for (Move move : moves) {
            if (move.getToPoint().equals(dest) && move.isDoesCapture() && move.getCapturedPiece().equals(getChessPieceAt(dest))) {
                return true;
            }
        }
        return false;
    }
}
