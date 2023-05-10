package view.Frame;

import controller.GameController;
import model.ChessBoard.Chessboard;
import model.ChessBoard.ChessboardPoint;
import model.ChessBoard.Move;
import model.ChessPieces.ChessPiece;
import model.Enum.PlayerColor;
import model.Enum.Seasons;
import model.User.User;
import view.ChessComponent.ChessComponent;
import view.Dialog.SuccessDialog;
import view.UI.SoundEffect;

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
    private OutputFrame outputFrame;
    private FileChooserFrame fileChooserFrame;

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
    public void playerClickSaveButton(){
        outputFrame = new OutputFrame(this);
        outputFrame.setVisible(true);
    }
    public void playerClickLoadButton(){
        fileChooserFrame = new FileChooserFrame(this);
        fileChooserFrame.setVisible(true);
    }

    //Here are the methods for all the moves on board.
    public void move(ChessboardPoint point, ChessboardPoint selectedPoint){
        if(this.getChessGameFrame().getChessboardComponent().getSeason() == Seasons.WINTER){
            SoundEffect player = new SoundEffect("Music/SoundEffect/Move_on_ice.wav");
            player.play();
        }else if(this.getChessGameFrame().getChessboardComponent().getSeason() == Seasons.SPRING){
            SoundEffect player = new SoundEffect("Music/SoundEffect/Move_in_grass.wav");
            player.play();
        }else{
            SoundEffect player = new SoundEffect("Music/SoundEffect/ChessMove.wav");
            player.play();
        }
        ChessComponent chessComponent = (ChessComponent) this.getChessGameFrame().getChessboardComponent().getGridComponentAt(selectedPoint).getComponents()[0];
        chessComponent.setSelected(false);
        this.getChessGameFrame().getChessboardComponent().setChessComponentAtGrid(point,this.getChessGameFrame().getChessboardComponent().removeChessComponentAtGrid(selectedPoint));
    }
    public void eat(ChessboardPoint point, ChessboardPoint selectedPoint){
        ChessPiece toGetPredatorSound = this.getGameController().getModel().getChessPieceAt(point);
        switch (toGetPredatorSound.getCategory()){
            case CAT -> {
                SoundEffect player = new SoundEffect("Music/SoundEffect/Cat.wav");
                player.play();
            }
            case DOG -> {
                SoundEffect player = new SoundEffect("Music/SoundEffect/Dog.wav");
                player.play();
            }
            case ELEPHANT -> {
                SoundEffect player = new SoundEffect("Music/SoundEffect/Elephant.wav");
                player.play();
            }
            case LION -> {
                SoundEffect player = new SoundEffect("Music/SoundEffect/Lion.wav");
                player.play();
            }
            case LEOPARD, TIGER -> {
                SoundEffect player = new SoundEffect("Music/SoundEffect/Tiger_Leopard.wav");
                player.play();
            }
            case RAT -> {
                SoundEffect player = new SoundEffect("Music/SoundEffect/Rat.wav");
                player.play();
            }
            case WOLF -> {
                SoundEffect player = new SoundEffect("Music/SoundEffect/Wolf.wav");
                player.play();
            }
            default -> {}

        }
        ChessComponent predator = (ChessComponent) this.getChessGameFrame().getChessboardComponent().getGridComponentAt(selectedPoint).getComponents()[0];
        ChessComponent prey = this.getChessGameFrame().getChessboardComponent().removeChessComponentAtGrid(point);
        predator.setSelected(false);
        this.getChessGameFrame().getChessboardComponent().setChessComponentAtGrid(point,this.getChessGameFrame().getChessboardComponent().removeChessComponentAtGrid(selectedPoint));
    }
    public void undo(Move move){
        if(move.isDoesCapture()){
            this.getChessGameFrame().getChessboardComponent().setChessComponentAtGrid(move.getFromPoint(),this.getChessGameFrame().getChessboardComponent().removeChessComponentAtGrid(move.getToPoint()));
            this.getChessGameFrame().getChessboardComponent().setChessComponentAtGrid(move.getToPoint(),this.getChessGameFrame().getChessComponent(move.getCapturedPiece()));
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

        try {
            this.getChessGameFrame().changeTurnLabel(1, PlayerColor.BLUE);
            this.getChessGameFrame().getChessboardComponent().initiateGridComponents();
            this.getChessGameFrame().getChessboardComponent().initiateChessComponent(this.getGameController().getModel());
        } catch (Exception e) {
            System.out.println("The Chess Component reset failed");
            throw new RuntimeException(e);
        }

        this.getChessGameFrame().revalidate();
        this.getChessGameFrame().repaint();
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
}
