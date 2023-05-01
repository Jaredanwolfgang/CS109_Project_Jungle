package listener;

import model.ChessBoard.ChessboardPoint;
import view.CellComponent;
import view.ChessComponent.ElephantChessComponent;

public interface GameListener {

    //call this method when player click an empty cell
    void onPlayerClickCell(ChessboardPoint point);

    //call this method when player click a chess piece
    void onPlayerClickChessPiece(ChessboardPoint point);

    //call this method when player click undo button
    //method will return false if there is no move to undo or the mode is online pvp
    boolean onPlayerClickUndoButton();

    //call this method when player click reset button
    void onPlayerClickResetButton();

    //call this method when player click save button
    void onPlayerClickSaveButton(String filePath);

    //call this method when player click load button
    //please warn the player this will reset the game (even if the file is invalid) and ask for confirmation
    //only available in local pvp mode
    void onPlayerClickLoadButton(String filePath);

    //call this method when player click login button
    //return true if login success
    boolean onPlayerClickLoginButton(String username, String password);

    //call this method when player click register button
    //return true if register success
    boolean onPlayerClickRegisterButton(String username, String password);

    //call this method when player click logout button
    void onPlayerClickLogoutButton();

    //call this method when player click AIMove button(possible function: let AI do one move for the current player)
    void onPlayerClickAIMoveButton();

    //call this method when player choose local pvp mode
    //input username and password for the second player
    //return true if login success
    boolean onPlayerSelectLocalPVPMode(String username, String password);

    //call this method when player choose pve mode
    void onPlayerSelectLocalPVEMode();

    //call this method when player choose online pvp mode
    void onPlayerSelectOnlinePVPMode();

    //call this method when player exit the game frame and go back to the mode selection frame
    //(I need to reset the second user to null and reset the chessboard)
    void onPlayerExitGameFrame();

}
