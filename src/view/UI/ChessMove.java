package view.UI;

import model.Enum.Seasons;
import view.Frame.Frame;

import javax.sound.sampled.*;
import java.io.File;

public class ChessMove{
    public ChessMove(Frame frame) {
        if(frame.getChessGameFrame().getChessboardComponent().getSeason() == Seasons.WINTER){
            new SoundEffect("Music/SoundEffect/Move_on_ice.wav");
        }else if(frame.getChessGameFrame().getChessboardComponent().getSeason() == Seasons.SPRING){
            new SoundEffect("Music/SoundEffect/Move_in_grass.wav");
        }else{
            new SoundEffect("Music/SoundEffect/ChessMove.wav");
        }
    }
}
