package model.ChessPieces;
import model.ChessBoard.Cell;
import model.ChessBoard.Chessboard;
import model.ChessBoard.ChessboardPoint;
import model.ChessBoard.Move;
import model.Enum.*;

import java.util.ArrayList;

public class LeopardChessPiece extends ChessPiece{
    public LeopardChessPiece(PlayerColor owner) {
        super(owner, Category.LEOPARD);
    }

    @Override
    public boolean canCapture(ChessPiece target) {
        if(this.getOwner() == target.getOwner()){
            return false;
        }
        if(this.isTrapped()){
            return false;
        }
        if(target.isTrapped()){
            return true;
        }
        return (5 >= target.getRank());
    }

    @Override
    public Move moveTo(ChessboardPoint fromPoint, ChessboardPoint toPoint, Cell[][] board){
        //Check if the target point is in the reach.
        if(Chessboard.getDistance(fromPoint, toPoint) != 1){
            return null;
        }

        //Check if the target point is in the river.
        if(board[toPoint.getRow()][toPoint.getCol()].isRiver()){
            return null;
        }

        //Check if the target point is in the same den as this piece's owner.
        if(board[toPoint.getRow()][toPoint.getCol()].isDen() && board[toPoint.getRow()][toPoint.getCol()].getOwner() == this.getOwner()){
            return null;
        }

        ChessPiece toPiece = board[toPoint.getRow()][toPoint.getCol()].getPiece();

        if(toPiece != null){
            if(this.canCapture(toPiece)){
                return new Move(this, fromPoint, toPoint, true, toPiece);
            }else{
                return null;
            }
        }else{
            return new Move(this, fromPoint, toPoint, false, null);
        }
    }
    @Override
    public ArrayList<Move> getAvailableMoves(ChessboardPoint point, Cell[][] board) {

        ArrayList<Move> possibleMoves = new ArrayList<>();
        ArrayList<ChessboardPoint> targetPoints = new ArrayList<>();
        int currentRow = point.getRow();
        int currentCol = point.getCol();
        int[] moveX = {0, 1, 0, -1};
        int[] moveY = {1, 0, -1, 0};

        for (int i = 0; i < 4; i++) {
            if(currentRow + moveX[i] >= 0 && currentRow + moveX[i] < Constant.CHESSBOARD_ROW_SIZE.getNum() &&
                    currentCol + moveY[i] >= 0 && currentCol + moveY[i] < Constant.CHESSBOARD_COL_SIZE.getNum()){
                targetPoints.add(new ChessboardPoint(currentRow + moveX[i], currentCol + moveY[i]));
            }
        }

        for (int i = 0; i < targetPoints.size(); i++) {
            possibleMoves.add(moveTo(point, targetPoints.get(i), board));
        }
        possibleMoves.removeIf(move -> move == null);
        return possibleMoves;
    }
}
