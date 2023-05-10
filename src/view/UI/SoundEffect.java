package view.UI;

import java.io.*;
import javax.sound.sampled.*;

public class SoundEffect {

    private final String filePath;
    private Clip clip;

    public SoundEffect (String filePath) {
        this.filePath = filePath;
    }

    public void play() {
        try {
            // Open an audio input stream from the file
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(new File(filePath));

            // Get the format of the audio data
            AudioFormat format = audioStream.getFormat();

            // Set up a data line to play the audio
            DataLine.Info info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);

            // Open the data line with the audio format
            clip.open(audioStream);

            // Start playing the audio clip
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
            clip.flush();
            clip.close();
        }
    }
}

