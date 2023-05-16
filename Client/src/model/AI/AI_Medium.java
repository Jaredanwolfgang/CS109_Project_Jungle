package model.AI;

import model.ChessBoard.Cell;
import model.ChessBoard.Chessboard;
import model.ChessBoard.ChessboardPoint;
import model.ChessBoard.Move;
import model.Enum.PlayerColor;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

public class AI_Medium {
    public static Move findBestOneMove(Cell[][] board, Color player){
        ArrayList<Move> possibleMoves = Chessboard.getAllPossibleMoveOnBoard(board, player);
        ArrayList<MoveWithValue> movesWithValues = new ArrayList<>();
        for(Move move : possibleMoves){
            int value = 0;
            int[] moveX = {0,0,1,-1};
            int[] moveY = {1,-1,0,0};

            if(move.getMovingPiece().getOwner() == PlayerColor.BLUE){
                value += Chessboard.getDistance(move.getFromPoint() , new ChessboardPoint(8,3)) - Chessboard.getDistance(move.getToPoint(), new ChessboardPoint(8,3));
            }else{
                value += Chessboard.getDistance(move.getFromPoint() , new ChessboardPoint(0,3)) - Chessboard.getDistance(move.getToPoint(), new ChessboardPoint(0,3));
            }
            if(move.isDoesCapture()){
                value += Math.pow(move.getCapturedPiece().getRank() , 2);
            }
            if(board[move.getToPoint().getRow()][move.getToPoint().getCol()].isDen() && board[move.getToPoint().getRow()][move.getToPoint().getCol()].getOwner() != move.getMovingPiece().getOwner()){
                value = Integer.MAX_VALUE;
            }
            if(board[move.getToPoint().getRow()][move.getToPoint().getCol()].isTrap() && board[move.getToPoint().getRow()][move.getToPoint().getCol()].getOwner() != move.getMovingPiece().getOwner()){
                boolean hasEnemyPiece = false;
                for (int i = 0; i < 4; i++) {
                    int x = move.getToPoint().getRow() + moveX[i];
                    int y = move.getToPoint().getCol() + moveY[i];
                    if(x >= 0 && x < 9 && y >= 0 && y < 7){
                        if(board[x][y].getPiece() != null && board[x][y].getPiece().getOwner() != move.getMovingPiece().getOwner()){
                            hasEnemyPiece = true;
                            break;
                        }
                    }
                    if(hasEnemyPiece){
                        value = Integer.MIN_VALUE;
                    }else{
                        value = Integer.MAX_VALUE;
                    }
                }
            }
            movesWithValues.add(new MoveWithValue(move, value));
        }
        movesWithValues.sort(Comparator.comparing(MoveWithValue::getValue));
        int random = (int)(Math.random() * 3);
        return movesWithValues.get(movesWithValues.size() - 1 - random).getMove();
    }
}
class MoveWithValue{
    Move move;
    int value;
    public MoveWithValue(Move move, int value){
        this.move = move;
        this.value = value;
    }

    public Move getMove() {
        return move;
    }

    public void setMove(Move move) {
        this.move = move;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }
}
