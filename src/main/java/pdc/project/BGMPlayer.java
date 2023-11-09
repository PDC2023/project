package pdc.project;

import javax.sound.sampled.*;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class BGMPlayer {

    private static final String[] bgmFiles = {
            "/Fmaj7Gm7.mp3",
            "/FGmAmBb.mp3",
    };
    private final Random random = new Random();
    private Map<String, Clip> clips = new HashMap<>();
    private Clip currentClip;
    private boolean enabled = false;

    public BGMPlayer() {
        loadAllBGMS();
    }

    public static Clip loadClip(String bgmFile){
        try {
            InputStream audioSrc = BGMPlayer.class.getResourceAsStream(bgmFile);
            Clip clip = getClipFromInputStream(audioSrc);
            return clip;
        } catch (Exception e) {
            throw new RuntimeException("Failed to load BGM: " + bgmFile, e);
        }
    }

    public static void play(Clip clip) {
        clip.setFramePosition(0);
        clip.start();
    }

    private void loadAllBGMS() {
        for (String bgmFile : bgmFiles) {
            try {
                InputStream audioSrc = getClass().getResourceAsStream(bgmFile);
                Clip clip = getClipFromInputStream(audioSrc);
                clips.put(bgmFile, clip);

                clip.addLineListener(event -> {
                    synchronized(BGMPlayer.this) {
                        if (event.getType() == LineEvent.Type.STOP) {
                            clip.setFramePosition(0);
                            if (enabled) {
                                playRandomBGM();
                            }
                        }
                    }
                });

            } catch (Exception e) {
                throw new RuntimeException("Failed to load BGM: " + bgmFile, e);
            }
        }
    }

    public synchronized void startBGM() {
        enabled = true;
        if (currentClip != null && currentClip.isRunning()) {
            currentClip.stop();
        }
        playRandomBGM();
    }

    public synchronized void stopBGM() {
        enabled = false;
        if (currentClip != null && currentClip.isRunning()) {
            currentClip.stop();
        }
    }

    public static Clip getClipFromInputStream(InputStream audioSrc) throws Exception {
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

        Clip clip = AudioSystem.getClip();
        clip.open(decodedAudioStream);
        return clip;
    }

    private synchronized void playRandomBGM() {
        if (!enabled) {
            return;
        }

        try {
            String randomBGM = bgmFiles[random.nextInt(bgmFiles.length)];
            currentClip = clips.get(randomBGM);

            if (currentClip == null) {
                throw new RuntimeException("Clip not found for BGM: " + randomBGM);
            }

            currentClip.start();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
