package menu;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class main_menu {
    public static void main(String[] args){
        ThreadMusic threadMusic = new ThreadMusic();
        ThreadSoundEffects threadSoundEffects = new ThreadSoundEffects(); // ustaw tworzac
        threadSoundEffects.setPlay(true);
        Frame_1 frame_1;
        Frame_2 frame_2;
        try {
            pobierzUstawieniaZPliku("src\\menu\\ustawienia\\ustawienia.txt", threadMusic, threadSoundEffects);
            new Frame_1(threadMusic, threadSoundEffects);
        }catch (Exception e){
            threadSoundEffects.run("effects\\mixkit-click-error-1110.wav",threadSoundEffects.getPlay());
            JOptionPane.showMessageDialog(null,"Coś poszło nie tak :(","Error",JOptionPane.ERROR_MESSAGE);
            threadSoundEffects.run("effects\\mixkit-click-error-1110.wav",threadSoundEffects.getPlay());
            System.exit(0);
        }
    }
    public static void pobierzUstawieniaZPliku(String path, ThreadMusic threadMusic, ThreadSoundEffects threadSoundEffects){
        String helper;
        try{
            Scanner scan = new Scanner(new File(path));

            threadMusic.run("music\\mixkit-pop-03-700.wav");
            helper = scan.next();
            threadMusic.setVolume(Float.parseFloat(helper));

            helper = scan.next();
            if(helper.equals("false")) threadMusic.pause();

            helper = scan.next();
            threadSoundEffects.run("effects\\mixkit-fantasy-game-success-notification-270.wav", threadSoundEffects.getPlay());
            threadSoundEffects.setVolume(Float.parseFloat(helper));

            helper = scan.next();
            if(helper.equals("false")) threadSoundEffects.setPlay(false);

        }catch(FileNotFoundException fileNotFoundException){
            JOptionPane.showMessageDialog(null,"Coś poszło nie tak :(","Error",JOptionPane.ERROR_MESSAGE);
            fileNotFoundException.printStackTrace();
            System.exit(0);
        }
    }
}
