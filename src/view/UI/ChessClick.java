package view.UI;

import model.Enum.Seasons;

import javax.sound.sampled.*;
import javax.swing.*;
import java.io.File;

public class ChessClick {
    public ChessClick() {
        ToolTipManager.sharedInstance().setInitialDelay(0);
        new SoundEffect("Music/SoundEffect/ChessClick.wav");
    }
}
