package strath.cs308.gizmoball.model.triggeringsystem.actions;

import strath.cs308.gizmoball.model.triggeringsystem.IAction;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

public class PlaySoundAction implements IAction {
    @Override
    public void doAction(Object args) {
        try {
            AudioInputStream inputStream;
            inputStream = AudioSystem.getAudioInputStream(getClass().getResource("/sounds/regular.wav"));
            Clip clip = AudioSystem.getClip();
            clip.open(inputStream);
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
