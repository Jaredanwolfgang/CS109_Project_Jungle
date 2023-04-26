package model.ChessBoard;

import model.ChessPieces.ChessPiece;
import model.Enum.PlayerColor;

import java.io.Serializable;
/**
 * This class describe the slot for Chess in Chessboard
 * */
public class Cell {
    // the position for chess
    private ChessPiece piece = null;
    private PlayerColor owner = null;

    private boolean isTrap = false;
    private boolean isRiver = false;
    private boolean isDen = false;

    public Cell() {
    }

    public PlayerColor getOwner() {
        return owner;
    }

    public void setOwner(PlayerColor owner) {
        this.owner = owner;
    }

    public boolean isTrap() {
        return isTrap;
    }

    public boolean isRiver() {
        return isRiver;
    }

    public boolean isDen() {
        return isDen;
    }

    public void setTrap(boolean trap) {
        isTrap = trap;
    }

    public void setRiver(boolean river) {
        isRiver = river;
    }

    public void setDen(boolean den) {
        isDen = den;
    }

    public ChessPiece getPiece() {
        return piece;
    }

    public void setPiece(ChessPiece piece) {
        this.piece = piece;
    }

    public void removePiece() {
        this.piece = null;
    }
}
