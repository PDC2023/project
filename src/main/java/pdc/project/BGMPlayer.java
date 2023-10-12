package pdc.project;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Random;

public class BGMPlayer {

    private String[] bgmFiles = {
            "/LakhNES1.mp3",
            "/LakhNES2.mp3",
    };
    private Random random = new Random();
    private Clip currentClip;
    private boolean enabled = false;

    public synchronized void startBGM() {
        enabled = true;
        playRandomBGM();
    }

    public synchronized void stopBGM() {
        enabled = false;
        if (currentClip != null && currentClip.isRunning()) {
            currentClip.stop();
            currentClip.close();
        }
    }

    private synchronized void playRandomBGM() {
        if (!enabled) {
            return;
        }

        try {
            String randomBGM = bgmFiles[random.nextInt(bgmFiles.length)];
            InputStream audioSrc = getClass().getResourceAsStream(randomBGM);
            InputStream bufferedIn = new BufferedInputStream(audioSrc);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(bufferedIn);

            AudioFormat baseFormat = audioStream.getFormat();
            AudioFormat decodedFormat = new AudioFormat(
                    AudioFormat.Encoding.PCM_SIGNED,
                    baseFormat.getSampleRate(),
                    16,
                    baseFormat.getChannels(),
                    baseFormat.getChannels() * 2,
                    baseFormat.getSampleRate(),
                    false
            );
            AudioInputStream decodedAudioStream = AudioSystem.getAudioInputStream(decodedFormat, audioStream);

            currentClip = AudioSystem.getClip();
            currentClip.open(decodedAudioStream);

            currentClip.addLineListener(event -> {
                synchronized(BGMPlayer.this) {
                    if (event.getType() == LineEvent.Type.STOP) {
                        currentClip.close();
                        if (enabled) {
                            playRandomBGM();
                        }
                    }
                }
            });

            currentClip.start();

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
