package menu;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ThreadUpdateStatistics implements Runnable{
    List<Integer> staty;
    ThreadSoundEffects threadSoundEffects;
    String path;
    public ThreadUpdateStatistics(List<Integer> staty, ThreadSoundEffects threadSoundEffects, String path){
        this.staty = staty;
        this.threadSoundEffects = threadSoundEffects;
        this.path = path;
    }
    @Override
    public void run() {
        List<Integer> buffer = new ArrayList<>();
        try {
            Scanner scan = new Scanner(new File(path));
            while(scan.hasNext()) buffer.add(Integer.valueOf(scan.next()));
        }catch (FileNotFoundException e) {
            threadSoundEffects.run("effects\\mixkit-click-error-1110.wav",threadSoundEffects.getPlay());
            JOptionPane.showMessageDialog(null,"Coś poszło nie tak :(","Error",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
            System.exit(0);
        }
        try {
            FileWriter write = new FileWriter(new File(path), false);
            int helper;
            while(buffer.size() != 0){
                helper = staty.get(0) + buffer.get(0);
                write.write(helper+"\n");
                buffer.remove(0);
                staty.remove(0);
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
