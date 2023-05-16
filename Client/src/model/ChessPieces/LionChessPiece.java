package model.ChessPieces;

import model.ChessBoard.Cell;
import model.ChessBoard.Chessboard;
import model.ChessBoard.ChessboardPoint;
import model.ChessBoard.Move;
import model.Enum.Category;
import model.Enum.Constant;
import model.Enum.PlayerColor;

import java.util.ArrayList;
import java.util.Objects;

public class LionChessPiece extends ChessPiece{
    public LionChessPiece(PlayerColor owner) {
        super(owner, Category.LION);
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
        return (7 >= target.getRank());
    }

    @Override
    public Move moveTo(ChessboardPoint fromPoint, ChessboardPoint toPoint, Cell[][] board){
        if(Chessboard.getDistance(fromPoint, toPoint) == 1){
            //This branch is for normal moves.

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
        }else{
            //This branch is for jumping over the river.

            //Check if the target point is in the river.
            if(board[toPoint.getRow()][toPoint.getCol()].isRiver()){
                return null;
            }

            //Check if the target point is the same as the current point.
            if(Chessboard.getDistance(fromPoint, toPoint) == 0){
                return null;
            }

            //Check if the target point is in the same row or column as the current point.
            if(fromPoint.getRow() != toPoint.getRow() && fromPoint.getCol() != toPoint.getCol()){
                return null;
            }

            //Check if the river-jumping is valid.
            boolean isValidJump = true;
            if(fromPoint.getRow() == toPoint.getRow()){
                if(fromPoint.getCol() < toPoint.getCol()){
                    for (int i = fromPoint.getCol() + 1; i < toPoint.getCol(); i++) {
                        if(!board[fromPoint.getRow()][i].isRiver() || board[fromPoint.getRow()][i].getPiece() != null){
                            isValidJump = false;
                            break;
                        }
                    }
                }else{
                    for (int i = toPoint.getCol() + 1; i < fromPoint.getCol(); i++) {
                        if(!board[fromPoint.getRow()][i].isRiver() || board[fromPoint.getRow()][i].getPiece() != null){
                            isValidJump = false;
                            break;
                        }
                    }
                }
            }else if(fromPoint.getCol() == toPoint.getCol()){
                if(fromPoint.getRow() < toPoint.getRow()){
                    for (int i = fromPoint.getRow() + 1; i < toPoint.getRow(); i++) {
                        if(!board[i][fromPoint.getCol()].isRiver() || board[i][fromPoint.getCol()].getPiece() != null){
                            isValidJump = false;
                            break;
                        }
                    }
                }else{
                    for (int i = toPoint.getRow() + 1; i < fromPoint.getRow(); i++) {
                        if(!board[i][fromPoint.getCol()].isRiver() || board[i][fromPoint.getCol()].getPiece() != null){
                            isValidJump = false;
                            break;
                        }
                    }
                }
            }
            if(!isValidJump){
                return null;
            }else{
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

        //Jumping over the river (up and down)
        if(currentRow == 2 && currentCol != 0 && currentCol != 3 && currentCol != 6){
            targetPoints.add(new ChessboardPoint(6, currentCol));
        }
        if(currentRow == 6 && currentCol != 0 && currentCol != 3 && currentCol != 6){
            targetPoints.add(new ChessboardPoint(2, currentCol));
        }

        //Jumping over the river (left and right)
        if(currentCol == 0 && (currentRow == 3 || currentRow == 4 || currentRow == 5)){
            targetPoints.add(new ChessboardPoint(currentRow, 3));
        }
        if(currentCol == 6 && (currentRow == 3 || currentRow == 4 || currentRow == 5)){
            targetPoints.add(new ChessboardPoint(currentRow, 3));
        }
        if(currentCol == 3 && (currentRow == 3 || currentRow == 4 || currentRow == 5)){
            targetPoints.add(new ChessboardPoint(currentRow, 0));
            targetPoints.add(new ChessboardPoint(currentRow, 6));
        }

        for (int i = 0; i < targetPoints.size(); i++) {
            possibleMoves.add(moveTo(point, targetPoints.get(i), board));
        }

        //This line removes all null elements in the list.
        possibleMoves.removeIf(Objects::isNull);

        return possibleMoves;
    }
}
