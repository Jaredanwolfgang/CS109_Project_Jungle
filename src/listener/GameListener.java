package listener;

import model.ChessBoard.ChessboardPoint;
import model.Enum.AIDifficulty;
import model.Enum.PlayerColor;
import model.User.User;
import view.CellComponent;
import view.ChessComponent.ElephantChessComponent;

import java.util.ArrayList;

public interface GameListener {

    //call this method when player click an empty cell
    void onPlayerClickCell(ChessboardPoint point, PlayerColor playerColor);

    //call this method when player click a chess piece

    /**
     * Now this method also need a playerColor variable.(To differentiate the source of the event, e.g.AI? player? online opponent?)
     * In view, please always set the playerColor parameter to gameController.getColorOfUser().
     */
    void onPlayerClickChessPiece(ChessboardPoint point, PlayerColor playerColor);

    //call this method when player click undo button
    //method will return false if there is no move to undo or the mode is online pvp
    boolean onPlayerClickUndoButton();

    //call this method when player click playback button
    //only available in local pvp mode and pve mode
    void onPlayerClickPlayBackButton();

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


    //call this method when player choose local pvp mode

    /**
     * Now this method don't need any information
     * but you should reuse the login method before you call this method
     */
    void onPlayerSelectLocalPVPMode();

    //call this method when player choose pve mode
    void onPlayerSelectLocalPVEMode(AIDifficulty difficulty);

    //call this method when player choose online pvp mode
    void onPlayerSelectOnlinePVPMode();

    //call this method when player click the rank list button
    //return an array list of user, sorted by their score already
    ArrayList<User> onPlayerClickRankListButton();

    //call this method when player exit the game frame and go back to the mode selection frame
    //(I need to reset the second user to null and reset the chessboard)
    void onPlayerExitGameFrame();

}
