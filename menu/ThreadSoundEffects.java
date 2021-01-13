package menu;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.io.File;

public class ThreadSoundEffects {
    Clip clip;
    float dB;
    double gain = 1.0;
    FloatControl gainControl;
    boolean play = true;
    public void run(String filepath, boolean play){
        if(play){ //tu usunalem gain != 0.0
            try{
                File musicPath = new File(filepath);
                if(musicPath.exists()){
                    AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                    clip = AudioSystem.getClip();
                    clip.open(audioInput);
                    clip.start();
                    this.setVolume(gain);
                } else {
                    JOptionPane.showMessageDialog(null,"Ścieżka do audio nie została znaleziona.");
                }
            } catch(Exception e){
                JOptionPane.showMessageDialog(null,"Coś poszło nie tak :(","Error",JOptionPane.ERROR_MESSAGE);
                System.exit(0);
            }
        }
    }
    public void setPlay(boolean setter){ this.play = setter; }
    public boolean getPlay(){ return this.play; }
    public void setVolume(double gain){
        this.gain = gain;
        gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        dB = (float) (Math.log(gain) / Math.log(10.0) * 20.0);
        gainControl.setValue(dB);
    }
    public double getVolume(){ return gain; }
}
