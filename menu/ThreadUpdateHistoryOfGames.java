package menu;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ThreadUpdateHistoryOfGames implements Runnable{
    String dopisac;
    ThreadSoundEffects threadSoundEffects;
    String path;
    public ThreadUpdateHistoryOfGames(String dopisac, ThreadSoundEffects threadSoundEffects, String path){
        this.dopisac = dopisac;
        this.threadSoundEffects = threadSoundEffects;
        this.path = path;
    }

    @Override
    public void run() {
        try {
            FileWriter write = new FileWriter(new File(path), true);
            write.write(dopisac);
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
