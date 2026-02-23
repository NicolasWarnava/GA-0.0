import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

public class AudioPlayer {
    private Clip clip;
    private FloatControl volumeControl; // controle de volume

    // Carrega o arquivo de áudio (caminho relativo via getResource)
    public void loadSound(String soundFilePath) {
        try {
            AudioInputStream audioIn =
                    AudioSystem.getAudioInputStream(getClass().getResource(soundFilePath));
            clip = AudioSystem.getClip();
            clip.open(audioIn);

            // tenta pegar o controle de volume desse clip
            if (clip.isControlSupported(FloatControl.Type.MASTER_GAIN)) {
                volumeControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // volume de 0.0 (mudo) até 1.0 (máximo do clip)
    public void setVolume(float volume) {
        if (volumeControl == null) return;
        if (volume < 0f) volume = 0f;
        if (volume > 1f) volume = 1f;

        float min = volumeControl.getMinimum(); // geralmente algo como -80.0
        float max = volumeControl.getMaximum(); // geralmente 0.0
        float gain = min + (max - min) * volume;
        volumeControl.setValue(gain);
    }

    // Toca o som a partir do início; interrompe se já estiver tocando
    public void play() {
        if (clip == null) return;

        if (clip.isRunning()) {
            clip.stop();
        }
        clip.setFramePosition(0);
        clip.start();
    }

    // Para o som
    public void stop() {
        if (clip != null && clip.isRunning()) {
            clip.stop();
        }
    }

    // Retorna se está tocando
    public boolean isPlaying() {
        return clip != null && clip.isRunning();
    }
}
