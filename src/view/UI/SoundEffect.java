package view.UI;

import java.io.*;
import javax.sound.sampled.*;

public class SoundEffect {
    private final String filePath;
    private Clip clip;

    public SoundEffect (String filePath) {
        this.filePath = filePath;
        this.play();
    }

    public void play() {
        try {
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(new File(filePath));
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

