package view.Frame;

import controller.GameController;
import model.ChessBoard.Chessboard;
import model.ChessBoard.ChessboardPoint;
import model.ChessBoard.Move;
import model.Enum.Seasons;
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
    public void move(ChessboardPoint point, ChessboardPoint selectedPoint){
        ChessComponent chessComponent = (ChessComponent) this.getChessGameFrame().getChessboardComponent().getGridComponentAt(selectedPoint).getComponents()[0];
        chessComponent.setSelected(false);
        this.getChessGameFrame().getChessboardComponent().setChessComponentAtGrid(point,this.getChessGameFrame().getChessboardComponent().removeChessComponentAtGrid(selectedPoint));
    }
    public void eat(ChessboardPoint point, ChessboardPoint selectedPoint){
        ChessComponent predator = (ChessComponent) this.getChessGameFrame().getChessboardComponent().getGridComponentAt(selectedPoint).getComponents()[0];
        ChessComponent prey = this.getChessGameFrame().getChessboardComponent().removeChessComponentAtGrid(point);
        predator.setSelected(false);
        this.getChessGameFrame().getChessboardComponent().setChessComponentAtGrid(point,this.getChessGameFrame().getChessboardComponent().removeChessComponentAtGrid(selectedPoint));
    }
    public void showAllPossibleMoves(ArrayList<Move> Moves){
        for (int i = 0; i < Moves.size(); i++) {
            System.out.println(Moves.get(i).getToPoint());
            this.getChessGameFrame().getChessboardComponent().getGridComponentAt(Moves.get(i).getToPoint()).setLabelled(true);
            this.getChessGameFrame().getChessboardComponent().getGridComponentAt(Moves.get(i).getToPoint()).repaint();
        }
    }
    public void removeAllPossibleMoves(ArrayList<Move> Moves){
        for (int i = 0; i < Moves.size(); i++) {
            System.out.println(Moves.get(i).getToPoint());
            this.getChessGameFrame().getChessboardComponent().getGridComponentAt(Moves.get(i).getToPoint()).setLabelled(false);
            this.getChessGameFrame().getChessboardComponent().getGridComponentAt(Moves.get(i).getToPoint()).repaint();
        }
    }
    public void updateTurnAccount(int turnAccount){

    }
    //FIXME: The reset function is not functioning properly.
    public void resetChessBoardComponent(){
        this.getChessGameFrame().getChessboardComponent().setSeason(Seasons.SPRING);
        this.getChessGameFrame().getChessboardComponent().removeAllComponents();
        this.getChessGameFrame().getChessboardComponent().initiateChessComponent(this.getGameController().getModel());
        this.getChessGameFrame().getChessboardComponent().initiateGridComponents();
    }
    //Gettersbelow
    public GameController getGameController() {
        return gameController;
    }
    public InitFrame getInitFrame() {
        return initFrame;
    }
    public LoginFrame getLoginFrame() {
        return loginFrame;
    }
    public RegisterFrame getRegisterFrame() {
        return registerFrame;
    }
    public StartFrame getStartFrame() {
        return startFrame;
    }
    public ChessGameFrame getChessGameFrame() {
        return chessGameFrame;
    }
    public MusicPlayerFrame getMusicPlayerFrame() {
        return musicPlayerFrame;
    }
}
