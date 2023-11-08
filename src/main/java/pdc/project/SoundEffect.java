package pdc.project;

import javax.sound.sampled.Clip;

public class SoundEffect {
    public static final Clip death = BGMPlayer.loadClip("/death.mp3");

    public static void play(Clip clip) {
        BGMPlayer.play(clip);
    }
}
