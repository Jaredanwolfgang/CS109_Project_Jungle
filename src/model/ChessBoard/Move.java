package model.ChessBoard;

import model.ChessPieces.ChessPiece;

import java.awt.*;
import java.io.Serializable;

public class Move implements Serializable {
    private ChessPiece movingPiece;
    private ChessboardPoint fromPoint;
    private ChessboardPoint toPoint;
    private boolean doesCapture;
    private ChessPiece capturedPiece;

    public Move(ChessPiece movingPiece, ChessboardPoint fromPoint, ChessboardPoint toPoint, boolean doesCapture, ChessPiece capturedPiece) {
        this.movingPiece = movingPiece;
        this.fromPoint = fromPoint;
        this.toPoint = toPoint;
        this.doesCapture = doesCapture;
        this.capturedPiece = capturedPiece;
    }

    public ChessPiece getMovingPiece() {
        return movingPiece;
    }

    public void setMovingPiece(ChessPiece movingPiece) {
        this.movingPiece = movingPiece;
    }

    public ChessboardPoint getFromPoint() {
        return fromPoint;
    }

    public void setFromPoint(ChessboardPoint fromPoint) {
        this.fromPoint = fromPoint;
    }

    public ChessboardPoint getToPoint() {
        return toPoint;
    }

    public void setToPoint(ChessboardPoint toPoint) {
        this.toPoint = toPoint;
    }

    public boolean isDoesCapture() {
        return doesCapture;
    }

    public void setDoesCapture(boolean doesCapture) {
        this.doesCapture = doesCapture;
    }

    public ChessPiece getCapturedPiece() {
        return capturedPiece;
    }

    public void setCapturedPiece(ChessPiece capturedPiece) {
        this.capturedPiece = capturedPiece;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(movingPiece.getName()).append(",");
        if(movingPiece.getOwner().getColor() == Color.BLUE){
            sb.append("BLUE,");
        }else{
            sb.append("RED,");
        }
        sb.append(fromPoint.getRow()).append(",").append(fromPoint.getCol()).append(",");
        sb.append(toPoint.getRow()).append(",").append(toPoint.getCol()).append(",");
        sb.append(doesCapture);
        if(doesCapture){
            sb.append(",").append(capturedPiece.getName());
            if(capturedPiece.getOwner().getColor() == Color.BLUE) {
                sb.append(",BLUE");
            }else{
                sb.append(",RED");
            }
        }
        return sb.toString();
    }
}
