package view.UI;

import model.Enum.Seasons;

import javax.sound.sampled.*;
import java.io.File;

public class ChessClick {
    public ChessClick() {
        play();
    }
    public void play() {
        new SoundEffect("Music/SoundEffect/ChessClick.wav");
    }
}
