package view.Frame;

import controller.GameController;
import model.ChessBoard.ChessboardPoint;
import model.ChessBoard.Move;
import model.Enum.PlayerColor;
import view.ChessComponent.ChessComponent;
import view.Dialog.SuccessDialog;
import view.UI.ChessMove;

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
    private SelectPVEModeFrame selectPVEFrame = new SelectPVEModeFrame(this);
    private SelectOnlinePVPModeFrame selectOnlinePVPFrame = new SelectOnlinePVPModeFrame(this);
    private RankFrame rankFrame;
    private SaveFileFrame saveFileFrame;
    private LoadFileFrame loadFileFrame;

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
        loginFrame.setVisible(true);
        new SuccessDialog("Please log in User 2");
        startFrame.setVisible(false);
    }

    public void playerClickNetPVPButton() {
        selectOnlinePVPFrame.setVisible(true);
        startFrame.setVisible(false);
    }

    public void playerClickPVEButton() {
        selectPVEFrame.setVisible(true);
        startFrame.setVisible(false);
    }

    public void playerClickReturnButton(JFrame fromFrame, JFrame toFrame) {
        fromFrame.setVisible(false);
        toFrame.setVisible(true);
    }
    public void playerClickSaveButton(){
        saveFileFrame = new SaveFileFrame(this);
        saveFileFrame.setVisible(true);
    }
    public void playerClickLoadButton(){
        loadFileFrame = new LoadFileFrame(this);
        loadFileFrame.setVisible(true);
    }
    public void playerClickRankButton(){
        rankFrame = new RankFrame(this);
        startFrame.setVisible(false);
        rankFrame.setVisible(true);
    }

    //Here are the methods for all the moves on board.
    public void move(ChessboardPoint point, ChessboardPoint selectedPoint){
        new ChessMove(this);
        ChessComponent chessComponent = (ChessComponent) this.getChessGameFrame().getChessboardComponent().getGridComponentAt(selectedPoint).getComponents()[0];
        chessComponent.setSelected(false);
        this.getChessGameFrame().getChessboardComponent().setChessComponentAtGrid(point,this.getChessGameFrame().getChessboardComponent().removeChessComponentAtGrid(selectedPoint));
    }
    public void eat(ChessboardPoint point, ChessboardPoint selectedPoint){
        ChessComponent predator = (ChessComponent) this.getChessGameFrame().getChessboardComponent().getGridComponentAt(selectedPoint).getComponents()[0];
        predator.play();
        ChessComponent prey = this.getChessGameFrame().getChessboardComponent().removeChessComponentAtGrid(point);
        if (gameController.getCurrentPlayer() == PlayerColor.BLUE) {
            getChessGameFrame().addInRedStack(prey);
        }else{
            getChessGameFrame().addInBlueStack(prey);
        }
        predator.setSelected(false);
        this.getChessGameFrame().getChessboardComponent().setChessComponentAtGrid(point,this.getChessGameFrame().getChessboardComponent().removeChessComponentAtGrid(selectedPoint));
    }
    public void undo(Move move){
        if(move.isDoesCapture()){
            this.getChessGameFrame().getChessboardComponent().setChessComponentAtGrid(move.getFromPoint(),this.getChessGameFrame().getChessboardComponent().removeChessComponentAtGrid(move.getToPoint()));
            this.getChessGameFrame().getChessboardComponent().setChessComponentAtGrid(move.getToPoint(),this.getChessGameFrame().getChessComponent(move.getCapturedPiece()));
            if(move.getCapturedPiece().getOwner().getColor() == Color.BLUE) {
                this.getChessGameFrame().removeOutBlueStack();
            }else{
                this.getChessGameFrame().removeOutRedStack();
            }
        }else{
            this.getChessGameFrame().getChessboardComponent().setChessComponentAtGrid(move.getFromPoint(),this.getChessGameFrame().getChessboardComponent().removeChessComponentAtGrid(move.getToPoint()));
        }
    }
    public void showAllPossibleMoves(ArrayList<Move> Moves){
        for (int i = 0; i < Moves.size(); i++) {
//            System.out.println(Moves.get(i).getToPoint());
            this.getChessGameFrame().getChessboardComponent().getGridComponentAt(Moves.get(i).getToPoint()).setLabelled(true);
            this.getChessGameFrame().getChessboardComponent().getGridComponentAt(Moves.get(i).getToPoint()).repaint();
        }
    }
    public void removeAllPossibleMoves(ArrayList<Move> Moves){
        for (int i = 0; i < Moves.size(); i++) {
//            System.out.println(Moves.get(i).getToPoint());
            this.getChessGameFrame().getChessboardComponent().getGridComponentAt(Moves.get(i).getToPoint()).setLabelled(false);
            this.getChessGameFrame().getChessboardComponent().getGridComponentAt(Moves.get(i).getToPoint()).repaint();
        }
    }
    public void updateTurnAccount(int turnAccount){
        this.getChessGameFrame().changeTurnLabel(turnAccount, gameController.getCurrentPlayer());
    }

    public void resetChessBoardComponent(){
        System.out.println("Chess Component resetting");
        this.getChessGameFrame().getChessboardComponent().removeAll();
        this.getChessGameFrame().initBlueStackBoard();
        this.getChessGameFrame().initRedStackBoard();
        try {
            this.getChessGameFrame().changeTurnLabel(1, PlayerColor.BLUE);
            this.getChessGameFrame().changeTimeLabel(45);
            this.getChessGameFrame().getChessboardComponent().initiateGridComponents();
            this.getChessGameFrame().getChessboardComponent().initiateChessComponent(this.getGameController().getModel());

        } catch (Exception e) {
            System.out.println("The Chess Component reset failed");
            throw new RuntimeException(e);
        }

        this.getChessGameFrame().revalidate();
        this.getChessGameFrame().repaint();
    }
    public void playerClickMusicButton() {
        musicPlayerFrame.setVisible(!musicPlayerFrame.isVisible());
    }
    //Getters below
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
    public SelectPVEModeFrame getSelectPVEModeFrame() {
        return selectPVEFrame;
    }
    public RankFrame getRankFrame() {
        return rankFrame;
    }
    public SelectOnlinePVPModeFrame getSelectOnlinePVPFrame() {
        return selectOnlinePVPFrame;
    }
}
