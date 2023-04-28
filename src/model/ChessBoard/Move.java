package model.ChessBoard;

import model.ChessPieces.ChessPiece;

public class Move{
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
        if(doesCapture){
            return "Move{" +
                    "movingPiece=" + movingPiece.getName() +
                    ", fromPoint=" + "(" +fromPoint.getRow() + "," + fromPoint.getCol() + ")" +
                    ", toPoint=" + "(" + toPoint.getRow() + "," + toPoint.getCol() + ")" +
                    ", doesCapture=" + doesCapture +
                    ", capturedPiece=" + capturedPiece.getName() +
                    '}';
        }else{
            return "Move{" +
                    "movingPiece=" + movingPiece.getName() +
                    ", fromPoint=" + "(" +fromPoint.getRow() + "," + fromPoint.getCol() + ")" +
                    ", toPoint=" + "(" + toPoint.getRow() + "," + toPoint.getCol() + ")" +
                    ", doesCapture=" + doesCapture +
                    '}';
        }
    }
}
