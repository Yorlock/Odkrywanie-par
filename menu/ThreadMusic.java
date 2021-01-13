package menu;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.swing.*;
import java.io.File;

public class ThreadMusic{
    Clip clip;
    float dB;
    double gainHolder = 0.1;
    FloatControl gainControl;
    public void run(String filepath){
        try{
            File musicPath = new File(filepath);
            if(musicPath.exists()){
                AudioInputStream audioInput = AudioSystem.getAudioInputStream(musicPath);
                clip = AudioSystem.getClip();
                clip.open(audioInput);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
            } else {
                JOptionPane.showMessageDialog(null,"Ścieżka do audio nie została znaleziona.");
            }
        } catch(Exception e){
            JOptionPane.showMessageDialog(null,"Coś poszło nie tak :(","Error",JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
    }
    public void pause(){
        clip.stop();
    }
    public void start(){
        clip.loop(Clip.LOOP_CONTINUOUSLY);
    }
    public void setVolume(double gain){
        gainHolder = gain;
        gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
        dB = (float) (Math.log(gainHolder) / Math.log(10.0) * 20.0);
        gainControl.setValue(dB);
    }
    public double getVolume(){ return gainHolder; }
    public boolean isAudioRunning(){
        return clip.isRunning();
    }
}
