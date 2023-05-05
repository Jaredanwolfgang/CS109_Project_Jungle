package view.Frame;
import controller.GameController;
import model.ChessBoard.Chessboard;
import model.User.User;

import javax.swing.*;

public class Frame {
    private GameController gameController;
    private InitFrame initFrame = new InitFrame(this);
    private LoginFrame loginFrame = new LoginFrame(this);
    private RegisterFrame registerFrame = new RegisterFrame(this);
    private StartFrame startFrame = new StartFrame(this);
    private ChessGameFrame chessGameFrame = new ChessGameFrame(this);
    private MusicPlayerFrame musicPlayerFrame = new MusicPlayerFrame(this);



    public Frame() {
        gameController= new GameController(this.chessGameFrame.getChessboardComponent(), new Chessboard());
        initFrame.setVisible(true);
    }

    public void playerClickLoginButton(){
        loginFrame.setVisible(true);
        initFrame.setVisible(false);
    }
    public void playerClickRegisterButton(){
        registerFrame.setVisible(true);
        initFrame.setVisible(false);
    }
    public void playerClickLocalPVPButton(){
        chessGameFrame.setVisible(true);
        startFrame.setVisible(false);
    }
    public void playerClickNetPVPButton(){
        chessGameFrame.setVisible(true);
        startFrame.setVisible(false);
    }
    public void playerClickPVEButton(){
        chessGameFrame.setVisible(true);
        startFrame.setVisible(false);
    }
    public void playerClickReturnButton(JFrame fromFrame,JFrame toFrame){
        fromFrame.setVisible(false);
        toFrame.setVisible(true);
    }
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
