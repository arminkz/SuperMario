package game;

import javax.sound.sampled.*;
import java.io.File;

public class AudioPlayer {

    public static Clip playAudio(String filename){
        try {
            File wavFile = new File("./assets/audio/" + filename);
            AudioInputStream stream;
            AudioFormat format;
            DataLine.Info info;
            Clip clip;

            stream = AudioSystem.getAudioInputStream(wavFile);
            format = stream.getFormat();
            info = new DataLine.Info(Clip.class, format);
            clip = (Clip) AudioSystem.getLine(info);
            clip.open(stream);
            clip.start();
            return clip;
        }
        catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

}
