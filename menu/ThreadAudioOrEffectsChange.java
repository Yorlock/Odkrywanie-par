package menu;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ThreadAudioOrEffectsChange implements Runnable {
    String path;
    int line;
    String text;
    ThreadSoundEffects threadSoundEffects;
    ThreadAudioOrEffectsChange(String path, int line, String text, ThreadSoundEffects threadSoundEffects){
        this.path = path;
        this.line = line;
        this.text = text;
        this.threadSoundEffects = threadSoundEffects;
    }
    public void run(){
        List<String> buffer = new ArrayList<>();
        try {
            Scanner scan = new Scanner(new File(path));
            while(scan.hasNext()) buffer.add(scan.next());
        }catch (FileNotFoundException e) {
            threadSoundEffects.run("effects\\mixkit-click-error-1110.wav",threadSoundEffects.getPlay());
            JOptionPane.showMessageDialog(null,"Coś poszło nie tak :(","Error",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
            System.exit(0);
        }
        try {
            FileWriter write = new FileWriter(new File(path), false);
            int i = 0;
            while(buffer.size() != 0){
                if(i == line){
                    write.write(text+"\n");
                }else {
                    write.write(buffer.get(0)+"\n");
                }
                buffer.remove(0);
                i++;
            }
            write.close();
        } catch (IOException e) {
            threadSoundEffects.run("effects\\mixkit-click-error-1110.wav",threadSoundEffects.getPlay());
            JOptionPane.showMessageDialog(null,"Coś poszło nie tak :(","Error",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
            System.exit(0);
        }
    }
}
