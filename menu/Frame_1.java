package menu;

import components.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Formatter;
import java.util.Scanner;

public class Frame_1 extends JFrame implements ActionListener {
    ThreadSoundEffects threadSoundEffects;
    ThreadMusic threadMusic;
    components.TextFieldMy login, haslo;
    components.PasswordFieldMy hasloUkryte;
    components.TextLabel naglowek;
    components.Button stworz, wyjscie, zaloguj, nowe_konto;
    components.ButtonIcon arrow_left;
    components.ButtonIcon audio, effects;

    Frame_1(ThreadMusic threadMusic, ThreadSoundEffects threadSoundEffects){
        this.threadSoundEffects = threadSoundEffects;
        this.threadMusic = threadMusic;
        ImageIcon icon = new ImageIcon("images\\rest\\icon.png");
        this.setTitle("Odkrywanie par");
        this.setSize(600,500);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setIconImage(icon.getImage());
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        if(threadMusic.isAudioRunning()) audio = new ButtonIcon("images\\rest\\audio_on.png",Color.gray,45,5,40,40);
        else audio = new ButtonIcon("images\\rest\\audio_off.png",Color.gray,45,5,40,40);
        if(threadSoundEffects.getPlay()) effects = new ButtonIcon("images\\rest\\effects_on_1.png",Color.gray,85,5,40,40);
        else effects = new ButtonIcon("images\\rest\\effects_off_1.png",Color.gray,85,5,40,40);
        wyjscie = new components.Button("Wyjście",215,330,150,50, new Font("Silom", Font.BOLD,20));

        wyjscie.addActionListener(this);
        audio.addActionListener(this);
        effects.addActionListener(this);

        this.getContentPane().add(LogInPanel());
        this.revalidate();
        this.repaint();
    }

    public JPanel LogInPanel(){
        JPanel that = new JPanel();
        that.setBounds(0,0,600,500);
        that.setLayout(null);
        that.setBackground(Color.LIGHT_GRAY);
        that.setOpaque(true);

        login = new TextFieldMy(170,150,240,40,new Font("Silom", Font.BOLD,25), "login");
        hasloUkryte = new PasswordFieldMy(170,200 ,240,40,new Font("Silom", Font.BOLD,25), "haslo");
        zaloguj = new components.Button("Zaloguj",215,260,150,50, new Font("Silom", Font.BOLD,20));
        nowe_konto = new components.Button("Stwórz konto!",215,400,150,50, new Font("Silom", Font.BOLD,16));
        naglowek = new TextLabel("Zaloguj się", new Font("Silom", Font.BOLD,50), Color.BLUE, 165, -40, 600, 200, false);

        zaloguj.addActionListener(this);
        nowe_konto.addActionListener(this);

        that.add(zaloguj);
        that.add(wyjscie);
        that.add(login);
        that.add(hasloUkryte);
        that.add(nowe_konto);
        that.add(naglowek);
        that.add(audio);
        that.add(effects);

        return that;
    }
    public JPanel CreateAccountPanel(){

        JPanel that = new JPanel();
        that.setBounds(0,0,600,500);
        that.setLayout(null);
        that.setBackground(Color.LIGHT_GRAY);
        that.setOpaque(true);

        login = new TextFieldMy(170, 150, 240, 40, new Font("Silom", Font.BOLD,25), "login"); //TODO OGRANICZYC DO 16/24 znakow?
        haslo = new TextFieldMy(170, 200, 240, 40, new Font("Silom", Font.BOLD,25), "haslo");
        stworz = new components.Button("Stwórz", 215, 260, 150, 50, new Font("Silom", Font.BOLD,20));
        arrow_left = new ButtonIcon("images\\rest\\arrow_left_2_yellow.png", Color.gray, 5, 5, 40, 40);
        naglowek = new TextLabel("Stwórz konto", new Font("Silom", Font.BOLD,50), Color.magenta, 140, -40, 600, 200, false);

        stworz.addActionListener(this);
        arrow_left.addActionListener(this);

        that.add(login);
        that.add(haslo);
        that.add(stworz);
        that.add(wyjscie);
        that.add(naglowek);
        that.add(arrow_left);
        that.add(audio);
        that.add(effects);


        return that;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == zaloguj){
            boolean poprawny_login_i_haslo = false;
            try {
                File file = new File("src\\menu\\ustawienia\\login_i_haslo.txt");
                Scanner scan = new Scanner(file);
                while(scan.hasNext()){
                    String sprawdz_login = scan.next();
                    String sprawdz_haslo = scan.next();
                    if(sprawdz_login.equals(login.getText()) && sprawdz_haslo.equals(hasloUkryte.getText())){
                        poprawny_login_i_haslo = true;
                        break;
                    }
                }
                if(!(poprawny_login_i_haslo)) {
                    threadSoundEffects.run("effects\\mixkit-click-error-1110.wav",threadSoundEffects.getPlay());
                    JOptionPane.showMessageDialog(null, "Nie poprawny login albo hasło. Spróbuj jeszcze raz!", "Błąd.", JOptionPane.INFORMATION_MESSAGE);
                    threadSoundEffects.run("effects\\mixkit-select-click-1109.wav",threadSoundEffects.getPlay());
                }
            } catch (FileNotFoundException fileNotFoundException) {
                threadSoundEffects.run("effects\\mixkit-click-error-1110.wav",threadSoundEffects.getPlay());
                JOptionPane.showMessageDialog(null,"Coś poszło nie tak :(","Error",JOptionPane.ERROR_MESSAGE);
                threadSoundEffects.run("effects\\mixkit-click-error-1110.wav",threadSoundEffects.getPlay());
                fileNotFoundException.printStackTrace();
                System.exit(0);
            }
            if(poprawny_login_i_haslo) {
                threadSoundEffects.run("effects\\mixkit-select-click-1109.wav",threadSoundEffects.getPlay());
                this.dispose();
                new Frame_2(login.getText(), threadMusic, threadSoundEffects);
            }
        }else if(e.getSource() == wyjscie){
            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav",threadSoundEffects.getPlay());
            int answer = JOptionPane.showConfirmDialog(null,"Czy chcesz wyjść z gry?","Wyjdź z gry.",JOptionPane.YES_NO_OPTION);
            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav",threadSoundEffects.getPlay());
            if(answer == 0) System.exit(0);

        } else if(e.getSource() == nowe_konto){
            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav",threadSoundEffects.getPlay());
            this.getContentPane().removeAll();
            this.getContentPane().add(CreateAccountPanel());
            this.validate();
            this.repaint();
        } else if(e.getSource() == audio){
            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav",threadSoundEffects.getPlay());
            if(audio.getPath().equals("images\\rest\\audio_on.png")){
                Runnable threadUpdateStatistics = new Thread(new ThreadAudioOrEffectsChange("src\\menu\\ustawienia\\ustawienia.txt",1,"false", threadSoundEffects));
                Thread thread = new Thread(threadUpdateStatistics);
                thread.start();
                threadMusic.pause();
                audio.changeIcon("images\\rest\\audio_off.png");
            }else{
                Runnable threadUpdateStatistics = new Thread(new ThreadAudioOrEffectsChange("src\\menu\\ustawienia\\ustawienia.txt",1,"true", threadSoundEffects));
                Thread thread = new Thread(threadUpdateStatistics);
                thread.start();
                threadMusic.start();
                audio.changeIcon("images\\rest\\audio_on.png");
            }
            repaint();
        }else if (e.getSource() == stworz) {
            boolean istnieje_nazwa = false;
            try {
                File file = new File("src\\menu\\ustawienia\\login_i_haslo.txt");
                Scanner scan = new Scanner(file);
                while(scan.hasNext()){
                    String helper = scan.next();
                    if(helper.equals(login.getText())){
                        istnieje_nazwa = true;
                        threadSoundEffects.run("effects\\mixkit-click-error-1110.wav",threadSoundEffects.getPlay());
                        JOptionPane.showMessageDialog(null,"Niestety nazwa "+ login.getText()+" jest już zajęta. Wybierz inną.","Istnieje już taki użytkownik.",JOptionPane.INFORMATION_MESSAGE);
                        threadSoundEffects.run("effects\\mixkit-select-click-1109.wav",threadSoundEffects.getPlay());
                        scan.close();
                        break;
                    }
                }
            } catch (FileNotFoundException fileNotFoundException) {
                threadSoundEffects.run("effects\\mixkit-click-error-1110.wav",threadSoundEffects.getPlay());
                JOptionPane.showMessageDialog(null,"Coś poszło nie tak :(","Error",JOptionPane.ERROR_MESSAGE);
                threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
                fileNotFoundException.printStackTrace();
                System.exit(0);
            }
            if(!(istnieje_nazwa)){
                try {
                    File file = new File("src\\menu\\ustawienia\\login_i_haslo.txt");
                    FileWriter fw = new FileWriter(file, true);
                    fw.write(login.getText() +" "+ haslo.getText()+"\n");
                    fw.close();
                    try{
                        new Formatter("src\\menu\\dane\\"+login.getText()+".txt");
                        file = new File("src\\menu\\dane\\"+login.getText()+".txt");
                        fw = new FileWriter(file, true);
                        fw.write("0\n"+"0\n"+"0\n"+"0\n"+"0\n"+"0\n"+"0\n"+"0\n"+"0\n"+"0\n"+"0\n"+"0");
                        fw.close();
                        new Formatter("src\\menu\\ustawienia\\spisgier\\"+login.getText()+".txt");
                        file = new File("src\\menu\\ustawienia\\spisgier\\"+login.getText()+".txt");
                        fw = new FileWriter(file, true);
                        fw.write("Historia gier wszystkich uzytkownikow:\n");
                        fw.close();
                    }catch (Exception ex){
                        threadSoundEffects.run("effects\\mixkit-click-error-1110.wav",threadSoundEffects.getPlay());
                        JOptionPane.showMessageDialog(null,"Coś poszło nie tak :(","Error",JOptionPane.ERROR_MESSAGE);
                        threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
                        System.exit(0);
                    }
                } catch (IOException ioException) {
                    threadSoundEffects.run("effects\\mixkit-click-error-1110.wav",threadSoundEffects.getPlay());
                    JOptionPane.showMessageDialog(null,"Coś poszło nie tak :(","Error",JOptionPane.ERROR_MESSAGE);
                    threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
                    ioException.printStackTrace();
                    System.exit(0);
                }
                threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
                this.dispose();
                new Frame_2(login.getText(), threadMusic, threadSoundEffects);
            }
        } else if (e.getSource() == arrow_left){
            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
            int answer = JOptionPane.showConfirmDialog(null,"Na pewno?","Przerwij tworzenie konta.",JOptionPane.YES_NO_OPTION);
            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
            if(answer == 0) {
                this.getContentPane().removeAll();
                this.getContentPane().add(LogInPanel());
                this.validate();
                this.repaint();
            }
        } else if (e.getSource() == effects){
            if(effects.getPath().equals("images\\rest\\effects_on_1.png")){
                threadSoundEffects.setPlay(false);
                Runnable threadUpdateStatistics = new Thread(new ThreadAudioOrEffectsChange("src\\menu\\ustawienia\\ustawienia.txt",3,"false", threadSoundEffects));
                Thread thread = new Thread(threadUpdateStatistics);
                thread.start();
                threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
                effects.changeIcon("images\\rest\\effects_off_1.png");
            }else{
                threadSoundEffects.setPlay(true);
                Runnable threadUpdateStatistics = new Thread(new ThreadAudioOrEffectsChange("src\\menu\\ustawienia\\ustawienia.txt",3,"true", threadSoundEffects));
                Thread thread = new Thread(threadUpdateStatistics);
                thread.start();
                threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
                effects.changeIcon("images\\rest\\effects_on_1.png");
            }
            repaint();

        }
    }
}
