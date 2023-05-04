package view.Frame;
import controller.GameController;
import model.ChessBoard.Chessboard;
import model.User.User;

import javax.swing.*;

public class Frame {
    private static User currentUser;
    private static User component;
    private GameController gameController;
    private InitFrame initFrame = new InitFrame(this);
    private LoginFrame loginFrame = new LoginFrame(this);
    private RegisterFrame registerFrame = new RegisterFrame(this);
    private StartFrame startFrame = new StartFrame(this);
    private ChessGameFrame chessGameFrame = new ChessGameFrame(this);

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
    public void playerLogin(String username, String password){
        //Read all users in file and check if username and password is correct
        if(gameController.onPlayerClickLoginButton(username, password)){
            currentUser = new User(username,password);//User with username and password
            loginFrame.setVisible(false);
            startFrame.setVisible(true);
        }else{
//            loginFrame.displayLoginFailMessage();
        }
    }


    public static User getCurrentUser() {
        return currentUser;
    }

    public static void setCurrentUser(User currentUser) {
        Frame.currentUser = currentUser;
    }

    public static User getComponent() {
        return component;
    }

    public static void setComponent(User component) {
        Frame.component = component;
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
}
