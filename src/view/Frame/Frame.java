package view.Frame;

import controller.GameController;
import model.ChessBoard.Chessboard;
import model.ChessBoard.Move;
import model.User.User;
import view.ChessComponent.ChessComponent;
import view.Dialog.SuccessDialog;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class Frame {
    private GameController gameController;
    private InitFrame initFrame = new InitFrame(this);
    private LoginFrame loginFrame = new LoginFrame(this);
    private RegisterFrame registerFrame = new RegisterFrame(this);
    private StartFrame startFrame = new StartFrame(this);
    private ChessGameFrame chessGameFrame = new ChessGameFrame(this);
    private MusicPlayerFrame musicPlayerFrame = new MusicPlayerFrame(this);
    private SelectPVEModeFrame selectFrame = new SelectPVEModeFrame(this);


    public Frame() {
        initFrame.setVisible(true);
    }

    //Register Code
    public void registerFrame(GameController gameController) {
        this.gameController = gameController;
    }

    //Button Logic in GUI below.
    public void playerClickLoginButton() {
        loginFrame.setVisible(true);
        initFrame.setVisible(false);
    }

    public void playerClickRegisterButton() {
        registerFrame.setVisible(true);
        initFrame.setVisible(false);
    }

    public void playerClickLocalPVPButton() {
        new SuccessDialog("Please log in User 2", loginFrame);
        startFrame.setVisible(false);
    }

    public void playerClickNetPVPButton() {
        chessGameFrame.setVisible(true);
        startFrame.setVisible(false);
    }

    public void playerClickPVEButton() {
        selectFrame.setVisible(true);
        startFrame.setVisible(false);
    }

    public void playerClickModesButton() {
        chessGameFrame.setVisible(true);
        selectFrame.setVisible(false);
    }

    public void playerClickReturnButton(JFrame fromFrame, JFrame toFrame) {
        fromFrame.setVisible(false);
        toFrame.setVisible(true);
    }
    //Here are the methods for all the moves on board.
    public void move(){

    }
    public void eat(){

    }
    public void showAllPossibleMoves(ArrayList<Move> Moves){

    }
    public void removeAllPossibleMoves(ArrayList<Move> Moves){

    }

    //Getter and Setter below
    public GameController getGameController() {
        return gameController;
    }

    public void setGameController(GameController gameController) {
        this.gameController = gameController;
    }

    public InitFrame getInitFrame() {
        return initFrame;
    }

    public void setInitFrame(InitFrame initFrame) {
        this.initFrame = initFrame;
    }

    public LoginFrame getLoginFrame() {
        return loginFrame;
    }

    public void setLoginFrame(LoginFrame loginFrame) {
        this.loginFrame = loginFrame;
    }

    public RegisterFrame getRegisterFrame() {
        return registerFrame;
    }

    public void setRegisterFrame(RegisterFrame registerFrame) {
        this.registerFrame = registerFrame;
    }

    public StartFrame getStartFrame() {
        return startFrame;
    }

    public void setStartFrame(StartFrame startFrame) {
        this.startFrame = startFrame;
    }

    public ChessGameFrame getChessGameFrame() {
        return chessGameFrame;
    }

    public void setChessGameFrame(ChessGameFrame chessGameFrame) {
        this.chessGameFrame = chessGameFrame;
    }

    public MusicPlayerFrame getMusicPlayerFrame() {
        return musicPlayerFrame;
    }

    public void setMusicPlayerFrame(MusicPlayerFrame musicPlayerFrame) {
        this.musicPlayerFrame = musicPlayerFrame;
    }
}
