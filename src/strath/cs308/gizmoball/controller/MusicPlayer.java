package strath.cs308.gizmoball.controller;

import javax.sound.sampled.*;
import java.io.IOException;

public class MusicPlayer {
    private Clip clip;

    void musicOn() {
        AudioInputStream inputStream;

        if (clip != null) {
            clip.stop();

        }
        try {
            inputStream = AudioSystem.getAudioInputStream(getClass().getResource("/sounds/backgroundmusic.wav"));
            clip = AudioSystem.getClip();
            clip.open(inputStream);
        } catch (LineUnavailableException | UnsupportedAudioFileException | IOException e) {
            e.printStackTrace();
        }
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }

    void musicOff() {
        if (clip != null) {
            clip.stop();
        }
        clip = null;
    }

    boolean isPlaying() {
        return clip != null;
    }


    public void setVolume(int volume) {
        if (clip != null) {
            FloatControl floatControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            floatControl.setValue(volume);
        }
    }

}
