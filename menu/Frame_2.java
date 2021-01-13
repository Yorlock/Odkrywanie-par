package menu;

import components.*;
import components.Button;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class Frame_2 extends JFrame implements ActionListener { //TODO strzelam że te wszystkie panele moge zrobic jako odzielne klasy ale no coz
    ThreadSoundEffects threadSoundEffects;
    ThreadMusic threadMusic;
    String login, currentPanelName = "MainPanel";
    PasswordFieldMy hasloUkryte;
    components.TextLabel nazwa_gry, kto_zalogowany, muzyka_text, effects_text, tyl_kartki_text, przod_kartki_tekst, poziom_bota, nazwa2gracza;
    components.Button gra_1, gra_2, turniej, opcje, statystyki, historiaGier, wyjscie, zapisz, graj;
    components.ButtonIcon audio, effects, arrow_left;
    components.Slider change_audio, change_effects, slider_par;
    components.ThreeRadioButtons back_card, front_card;
    components.ThreeRadioButtonsWithoutImage poziom_bot_radio;
    components.TextFieldMy poleDla2Gracza, turniejTextField;
    JComboBox comboBox;
    LogToTheTournament[] logToTheTournament;
    List<String> loginy = new ArrayList<>();

    public Frame_2(String login, ThreadMusic threadMusic, ThreadSoundEffects threadSoundEffects){
        this.threadSoundEffects = threadSoundEffects;
        this.threadMusic = threadMusic;
        this.login = login;
        ImageIcon icon = new ImageIcon("images\\rest\\icon.png");
        this.setTitle("Odkrywanie par");
        this.setSize(1400,800);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setIconImage(icon.getImage());
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        arrow_left = new ButtonIcon("images\\rest\\arrow_left_2_yellow.png", Color.gray, 5, 5, 40, 40);
        if(threadMusic.isAudioRunning()) audio = new ButtonIcon("images\\rest\\audio_on.png",Color.gray,45,5,40,40);
        else audio = new ButtonIcon("images\\rest\\audio_off.png",Color.gray,45,5,40,40);
        if(threadSoundEffects.getPlay()) effects = new ButtonIcon("images\\rest\\effects_on_1.png",Color.gray,85,5,40,40);
        else effects = new ButtonIcon("images\\rest\\effects_off_1.png",Color.gray,85,5,40,40);

        arrow_left.addActionListener(this);
        audio.addActionListener(this);
        effects.addActionListener(this);

        this.getContentPane().add(MainPanel());
        this.revalidate();
        this.repaint();

    }
    public JPanel MainPanel(){

        JPanel that = new JPanel();
        that.setBounds(0,0,1400,800);
        that.setLayout(null);
        that.setBackground(Color.LIGHT_GRAY);
        that.setOpaque(true);

        gra_1 = new components.Button("Gra dla 1 gracza",593,300,200,50, new Font("Silom", Font.BOLD,20));
        gra_2 = new components.Button("Gra dla 2 graczy",593,360,200,50, new Font("Silom", Font.BOLD,20));
        turniej = new components.Button("Turniej",593,420,200,50, new Font("Silom", Font.BOLD,20));
        opcje = new components.Button("Opcje",593,480,200,50, new Font("Silom", Font.BOLD,20));
        statystyki = new components.Button("Statystyki",593,540,200,50, new Font("Silom", Font.BOLD,20));
        historiaGier = new components.Button("Historia gier",593,600,200,50, new Font("Silom", Font.BOLD,20));
        wyjscie = new Button("Wyjście",593,660,200,50, new Font("Silom", Font.BOLD,20));
        int x_start = 1200, y_width = 200 + login.length()*8;
        while(this.getWidth() < x_start + (login.length() + 17)*9) x_start-= 10;
        kto_zalogowany = new TextLabel("Zalogowany jako: "+login, new Font("Silom", Font.BOLD,15),Color.BLACK, x_start ,-85, y_width,200, false);
        nazwa_gry = new TextLabel("Odkrywanie Par", new Font("Silom", Font.BOLD,60),Color.BLUE,443,50,600,200, false);

        gra_1.addActionListener(this);
        gra_2.addActionListener(this);
        turniej.addActionListener(this);
        opcje.addActionListener(this);
        statystyki.addActionListener(this);
        historiaGier.addActionListener(this);
        wyjscie.addActionListener(this);

        that.add(arrow_left);
        that.add(gra_1);
        that.add(gra_2);
        that.add(turniej);
        that.add(opcje);
        that.add(statystyki);
        that.add(historiaGier);
        that.add(wyjscie);
        that.add(nazwa_gry);
        that.add(kto_zalogowany);
        that.add(audio);
        that.add(effects);

        return that;
    }

    public JPanel Gra1Panel(){

        JPanel that = new JPanel();
        that.setBounds(0,0,1400,800);
        that.setLayout(null);
        that.setBackground(Color.LIGHT_GRAY);
        that.setOpaque(true);
        currentPanelName = "Gra dla 1 gracza";

        graj = new Button("Graj",620,675,160,75, new Font("Silom", Font.BOLD,30));
        slider_par = new Slider(2,32, 16,400,140,820,160,new Font("Silom", Font.BOLD,20),new Font("Silom", Font.ITALIC,30),Color.BLACK, 1,4,"");
        poziom_bota = new TextLabel("Poziom trudności:", new Font("Silom", Font.BOLD,20), Color.BLACK, 140, 300, 250, 100, false);
        poziom_bot_radio = new ThreeRadioButtonsWithoutImage(400, 340, 800, 100,new Font("Silom", Font.BOLD,20), new String[]{"Łatwy", "Średni", "Trudny"}, threadSoundEffects, 0);

        graj.addActionListener(this);

        that.add(audio);
        that.add(arrow_left);
        that.add(effects);
        that.add(graj);
        that.add(new TextLabel("Liczba par:", new Font("Silom", Font.BOLD,20), Color.BLACK, 140, 150, 250, 100, false));
        that.add(slider_par);
        that.add(poziom_bota);
        that.add(poziom_bot_radio);

        return that;
    }

    public JPanel Gra2Panel(){
        JPanel that = new JPanel();
        that.setBounds(0,0,1400,800);
        that.setLayout(null);
        that.setBackground(Color.LIGHT_GRAY);
        that.setOpaque(true);
        currentPanelName = "Gra dla 2 graczy";

        nazwa2gracza = new TextLabel("Drugi gracz:", new Font("Silom", Font.BOLD,20), Color.BLACK, 140, 300, 250, 100, false);
        graj = new Button("Graj",620,675,160,75, new Font("Silom", Font.BOLD,30));
        slider_par = new Slider(2,32, 16,400,140,820,160,new Font("Silom", Font.BOLD,20),new Font("Silom", Font.ITALIC,30),Color.BLACK, 1,4,"");
        poleDla2Gracza = new TextFieldMy(640, 332, 250, 40, new Font("Silom", Font.BOLD,25), "Godny przeciwnik");
        hasloUkryte = new PasswordFieldMy(640,400 ,250,40,new Font("Silom", Font.BOLD,25), "haslo");
        graj.addActionListener(this);

        that.add(poleDla2Gracza);
        that.add(hasloUkryte);
        that.add(nazwa2gracza);
        that.add(slider_par);
        that.add(new TextLabel("Liczba par:", new Font("Silom", Font.BOLD,20), Color.BLACK, 140, 150, 250, 100, false));
        that.add(graj);
        that.add(audio);
        that.add(arrow_left);
        that.add(effects);

        return that;

    }
    public JPanel TurniejPanel(){
        JPanel that = new JPanel();
        that.setBounds(0,0,1400,800);
        that.setLayout(null);
        that.setBackground(Color.LIGHT_GRAY);
        that.setOpaque(true);
        currentPanelName = "Turniej";
        loginy.clear();
        loginy.add(login);
        comboBox = new JComboBox(new String[]{"2", "3", "4", "5", "6"});
        comboBox.setSelectedItem("2");
        logToTheTournament = new LogToTheTournament[5];
        comboBox.setBounds(1050,39,40,25);
        logToTheTournament[0] = new LogToTheTournament(550,300,"Gracz 2", loginy, threadSoundEffects);
        logToTheTournament[1] = new LogToTheTournament(200,300,"Gracz 3", loginy, threadSoundEffects);
        logToTheTournament[2] = new LogToTheTournament(900,300,"Gracz 4", loginy, threadSoundEffects);
        logToTheTournament[3] = new LogToTheTournament(200,510,"Gracz 5", loginy, threadSoundEffects);
        logToTheTournament[4] = new LogToTheTournament(900,510,"Gracz 6", loginy, threadSoundEffects);
        logToTheTournament[0].setEnabled(true);
        logToTheTournament[1].setEnabled(false);
        logToTheTournament[2].setEnabled(false);
        logToTheTournament[3].setEnabled(false);
        logToTheTournament[4].setEnabled(false);
        graj = new Button("Graj",620,675,160,75, new Font("Silom", Font.BOLD,30));
        slider_par = new Slider(2,32, 16,400,140,820,160,new Font("Silom", Font.BOLD,20),new Font("Silom", Font.ITALIC,30),Color.BLACK, 1,4,"");
        turniejTextField = new TextFieldMy(425, 30, 250, 40, new Font("Silom", Font.BOLD,25), "Turniej");

        graj.addActionListener(this);
        comboBox.addActionListener(this);

        that.add(logToTheTournament[0]);
        that.add(logToTheTournament[1]);
        that.add(logToTheTournament[2]);
        that.add(logToTheTournament[3]);
        that.add(logToTheTournament[4]);


        that.add(comboBox);
        that.add(new TextLabel("Nazwa turnieju:", new Font("Silom", Font.BOLD,20), Color.BLACK, 140, 0, 250, 100, false));
        that.add(new TextLabel("Liczba uczestników:", new Font("Silom", Font.BOLD,20), Color.BLACK, 800, 0, 250, 100, false));
        that.add(slider_par);
        that.add(turniejTextField);
        that.add(new TextLabel("Liczba par:", new Font("Silom", Font.BOLD,20), Color.BLACK, 140, 150, 250, 100, false));
        that.add(graj);
        that.add(audio);
        that.add(arrow_left);
        that.add(effects);

        return that;

    }
    public JPanel OpcjePanel(){
        JPanel that = new JPanel();
        that.setBounds(0,0,1400,800);
        that.setLayout(null);
        that.setBackground(Color.LIGHT_GRAY);
        that.setOpaque(true);
        currentPanelName = "Opcje";
        muzyka_text = new TextLabel("Głośność muzyki:", new Font("Silom", Font.BOLD,20), Color.BLACK, 140, 0, 250, 100, false);
        effects_text = new TextLabel("Głośność efektów:", new Font("Silom", Font.BOLD,20), Color.BLACK, 140, 150, 250, 100, false);
        tyl_kartki_text = new TextLabel("Wybierz rewers:", new Font("Silom", Font.BOLD,20), Color.BLACK, 140, 300, 250, 100, false);
        przod_kartki_tekst = new TextLabel("Wybierz rodzaj kartek:", new Font("Silom", Font.BOLD,20), Color.BLACK, 700, 300, 250, 100, false);
        zapisz = new Button("Zapisz",620,675,160,75, new Font("Silom", Font.BOLD,30));

        String helper;
        try {
            File file = new File("src\\menu\\ustawienia\\ustawienia.txt");
            Scanner scan = new Scanner(file);
            helper = scan.next();
            float valueHolder = Float.parseFloat(helper) * 100;
            change_audio = new Slider(0,100, (int) valueHolder,400,-10,820,160,new Font("Silom", Font.BOLD,20),new Font("Silom", Font.ITALIC,30),Color.BLACK,5,25,"%");
            scan.next();
            helper = scan.next();
            valueHolder = Float.parseFloat(helper) * 100;
            change_effects = new Slider(0,100,(int) valueHolder,400,140,820,160,new Font("Silom", Font.BOLD,20),new Font("Silom", Font.ITALIC,30),Color.BLACK,5,25,"%");
            scan.next();
            helper = scan.next();
            back_card = new ThreeRadioButtons(140, 375, 450, 300,new Font("Silom", Font.BOLD,14), new String[]{"Klasyk", "Chmura", "Buzia"}, new String[]{"images\\back_cards\\back_of_card_1.png","images\\back_cards\\back_of_card_2.png","images\\back_cards\\back_of_card_3.png"}, threadSoundEffects, Integer.parseInt(helper));
            helper = scan.next();
            front_card = new ThreeRadioButtons(700, 375, 450, 300,new Font("Silom", Font.BOLD,14), new String[]{"Obrazy", "Kolory", "Cyfry"}, new String[]{"images\\front_cards\\front_card_1.png","images\\front_cards\\front_card_2.png","images\\front_cards\\front_card_3.png"}, threadSoundEffects, Integer.parseInt(helper));

        }catch(FileNotFoundException fileNotFoundException) {
            threadSoundEffects.run("effects\\mixkit-click-error-1110.wav",threadSoundEffects.getPlay());
            JOptionPane.showMessageDialog(null,"Coś poszło nie tak :(","Error",JOptionPane.ERROR_MESSAGE);
            threadSoundEffects.run("effects\\mixkit-click-error-1110.wav",threadSoundEffects.getPlay());
            fileNotFoundException.printStackTrace();
            System.exit(0);
        }

        zapisz.addActionListener(this);

        that.add(audio);
        that.add(arrow_left);
        that.add(muzyka_text);
        that.add(effects_text);
        that.add(tyl_kartki_text);
        that.add(przod_kartki_tekst);
        that.add(zapisz);
        that.add(change_audio);
        that.add(change_effects);
        that.add(back_card);
        that.add(front_card);
        that.add(effects);
        return that;

    }
    public JPanel StatystykiPanel(){
        JPanel that = new JPanel();
        that.setBounds(0,0,1400,800);
        that.setLayout(null);
        that.setBackground(Color.LIGHT_GRAY);
        that.setOpaque(true);
        currentPanelName = "Statystyki";

        try{
            Scanner scan = new Scanner(new File("src\\menu\\dane\\"+login+".txt"));
            that.add(new TextLabel("Ile wygranych gier: "+scan.next(), new Font("Silom", Font.BOLD,20),Color.BLACK,50,100,400,50, true));
            that.add(new TextLabel("Ile przegranych gier: "+scan.next(), new Font("Silom", Font.BOLD,20),Color.BLACK,500,100,400,50, true));
            that.add(new TextLabel("Ile remisów: "+scan.next(), new Font("Silom", Font.BOLD,20),Color.BLACK,950,100,400,50, true));
            that.add(new TextLabel("Ile gier z komputerem łatwym: "+scan.next(), new Font("Silom", Font.BOLD,20),Color.BLACK,50,250,400,50, true));
            that.add(new TextLabel("Ile gier z komputerem średnim: "+scan.next(), new Font("Silom", Font.BOLD,20),Color.BLACK,500,250,400,50, true));
            that.add(new TextLabel("Ile gier z komputerem trudnym: "+scan.next(), new Font("Silom", Font.BOLD,20),Color.BLACK,950,250,400,50, true));
            that.add(new TextLabel("Ile gier z przyjacielem: "+scan.next(), new Font("Silom", Font.BOLD,20),Color.BLACK,50,400,400,50, true));
            that.add(new TextLabel("Ile rozegranych gier w turnieju: "+scan.next(), new Font("Silom", Font.BOLD,20),Color.BLACK,500,400,400,50, true));
            that.add(new TextLabel("Ile wygranych gier w turnieju: "+scan.next(), new Font("Silom", Font.BOLD,20),Color.BLACK,950,400,400,50, true));
            that.add(new TextLabel("Ile odkrytych par: "+scan.next(), new Font("Silom", Font.BOLD,20),Color.BLACK,50,550,400,50, true));
            that.add(new TextLabel("Ile łącznie rozegranych tur: "+scan.next(), new Font("Silom", Font.BOLD,20),Color.BLACK,500,550,400,50, true));
            that.add(new TextLabel("Ile czasu w grze: "+scan.next()+"s", new Font("Silom", Font.BOLD,20),Color.BLACK,950,550,400,50, true));


        }catch(FileNotFoundException fileNotFoundException){
            threadSoundEffects.run("effects\\mixkit-click-error-1110.wav",threadSoundEffects.getPlay());
            JOptionPane.showMessageDialog(null,"Coś poszło nie tak :(","Error",JOptionPane.ERROR_MESSAGE);
            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());

            fileNotFoundException.printStackTrace();
            System.exit(0);
        }

        that.add(audio);
        that.add(arrow_left);
        that.add(effects);

        return that;

    }

    public JPanel HistoriaGierPanel(){
        JPanel that = new JPanel();
        that.setBounds(0,0,1400,800);
        that.setLayout(null);
        that.setBackground(Color.LIGHT_GRAY);
        that.setOpaque(true);
        currentPanelName = "Historia gier";
        try {
            String all = new Scanner(new File("src\\menu\\ustawienia\\spisgier\\"+login+".txt")).useDelimiter("\\A").next();
            final JTextArea textArea = new JTextArea(10,20);
            textArea.setBackground(Color.LIGHT_GRAY);
            textArea.setFont(new Font("Silom", Font.BOLD,20));
            textArea.setForeground(Color.BLACK);
            textArea.setEditable(false);
            JScrollPane scroll = new JScrollPane(textArea,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
            scroll.setBounds(3,100,1380,660);
            textArea.setText(all);
            textArea.setLineWrap(true);
            textArea.setWrapStyleWord(true);
            that.add(scroll);
        } catch (FileNotFoundException e) {
            threadSoundEffects.run("effects\\mixkit-click-error-1110.wav",threadSoundEffects.getPlay());
            JOptionPane.showMessageDialog(null,"Coś poszło nie tak :(","Error",JOptionPane.ERROR_MESSAGE);
            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
            e.printStackTrace();
            System.exit(0);
        }

        that.add(audio);
        that.add(arrow_left);
        that.add(effects);
        that.add(new TextLabel("Historia gier", new Font("Silom", Font.BOLD,40),Color.BLUE,600,0,250,100, false));
        //TODO wszedzie zrobic tak jak tutaj zrobilem

        return that;
    }
    public String getCurrentPanelName(){
        return currentPanelName;
    }
    public void setCurrentPanelName(String newName){ this.currentPanelName = newName; }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == gra_1){
            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
            this.getContentPane().removeAll();
            this.getContentPane().add(Gra1Panel());
            this.validate();
            this.repaint();

        }else if(e.getSource() == gra_2){
            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
            this.getContentPane().removeAll();
            this.getContentPane().add(Gra2Panel());
            this.validate();
            this.repaint();

        }else if(e.getSource() == turniej){
            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
            this.getContentPane().removeAll();
            this.getContentPane().add(TurniejPanel());
            this.validate();
            this.repaint();

        }else if(e.getSource() == opcje){
            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
            this.getContentPane().removeAll();
            this.getContentPane().add(OpcjePanel());
            this.validate();
            this.repaint();

        }else if(e.getSource() == statystyki){
            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
            this.getContentPane().removeAll();
            this.getContentPane().add(StatystykiPanel());
            this.validate();
            this.repaint();

        }else if(e.getSource() == historiaGier){
            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
            this.getContentPane().removeAll();
            this.getContentPane().add(HistoriaGierPanel());
            this.validate();
            this.repaint();
        } else if(e.getSource() == wyjscie){
            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
            int answer = JOptionPane.showConfirmDialog(null,"Czy chcesz wyjść z gry?","Wyjdź z gry.",JOptionPane.YES_NO_OPTION);
            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
            if(answer == 0) System.exit(0);
        }else if(e.getSource() == arrow_left){
            int answer;
            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
            if(this.getCurrentPanelName().equals("MainPanel")){
                answer = JOptionPane.showConfirmDialog(null,"Czy chcesz się wylogować?","Wyloguj się",JOptionPane.YES_NO_OPTION);
                if(answer == 0){
                    new Frame_1(threadMusic, threadSoundEffects);
                    this.dispose();
                }
            }else if(this.getCurrentPanelName().equals("Gra1PanelGame")){
                answer = JOptionPane.showConfirmDialog(null,"Czy chcesz wyjść z rozgrywki? Mecz nie zostanie zapisany.","Wyjdź",JOptionPane.YES_NO_OPTION);
                if(answer == 0){
                    this.setCurrentPanelName("MainPanel");
                    this.getContentPane().removeAll();
                    this.getContentPane().add(MainPanel());
                    this.validate();
                    this.repaint();
                }
            }else if(this.getCurrentPanelName().equals("Gra2PanelGame")){
                answer = JOptionPane.showConfirmDialog(null,"Czy chcesz wyjść z rozgrywki? Mecz nie zostanie zapisany.","Wyjdź",JOptionPane.YES_NO_OPTION);
                if(answer == 0) {
                    this.setCurrentPanelName("MainPanel");
                    this.getContentPane().removeAll();
                    this.getContentPane().add(MainPanel());
                    this.validate();
                    this.repaint();
                }
                }else if(this.getCurrentPanelName().equals("Game3TournamentLabel")){
                    answer = JOptionPane.showConfirmDialog(null,"Czy chcesz wyjść z turnieju? Obecny mecz nie zostanie zapisany.","Wyjdź",JOptionPane.YES_NO_OPTION);
                    if(answer == 0) {
                        this.setCurrentPanelName("MainPanel");
                        this.getContentPane().removeAll();
                        this.getContentPane().add(MainPanel());
                        this.validate();
                        this.repaint();
                    }
                }else{
                answer = JOptionPane.showConfirmDialog(null,"Czy chcesz wyjść z "+ this.getCurrentPanelName() +"?","Wyjdź",JOptionPane.YES_NO_OPTION);
                if(answer == 0){
                    this.setCurrentPanelName("MainPanel");
                    this.getContentPane().removeAll();
                    this.getContentPane().add(MainPanel());
                    this.validate();
                    this.repaint();
                }
            }
            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
        }else if(e.getSource() == audio){ //TODO mozliwe ze bylbym w stanie zrobic actionlistener w tych clasach, a nie w frame
            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
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
        }else if (e.getSource() == effects){
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

        } else if(e.getSource() == zapisz){
            try {
                FileWriter write = new FileWriter(new File("src\\menu\\ustawienia\\ustawienia.txt"), false);
                boolean musicHolder = threadMusic.isAudioRunning();
                boolean effectsHolder = threadSoundEffects.getPlay();

                float helper = change_audio.getValue()/100.0f;
                if(!(musicHolder)) threadMusic.start();
                threadMusic.setVolume(helper);
                write.write(helper +"\n");
                if(!(musicHolder)) threadMusic.pause();
                write.write(musicHolder +"\n");

                helper = change_effects.getValue()/100.0f;
                if(!(effectsHolder)) threadSoundEffects.setPlay(true);
                threadSoundEffects.run("effects\\mixkit-video-game-mystery-alert-234.wav", true);
                threadSoundEffects.setVolume(helper);
                write.write(helper +"\n");
                if(!(effectsHolder)) threadSoundEffects.setPlay(false);
                write.write(threadSoundEffects.getPlay() +"\n");
                write.write(back_card.getWhichRadioButton() +"\n");
                write.write(front_card.getWhichRadioButton() +"\n");
                write.close();
            }catch(Exception ex){
                threadSoundEffects.run("effects\\mixkit-click-error-1110.wav",threadSoundEffects.getPlay());
                JOptionPane.showMessageDialog(null,"Coś poszło nie tak :(","Error",JOptionPane.ERROR_MESSAGE);
                threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
                System.exit(0);
            }
        } else if(e.getSource() == comboBox){
            loginy.clear();
            loginy.add(login);
            if(comboBox.getSelectedItem() == "2"){
                logToTheTournament[0].setEnabled(true);
                logToTheTournament[1].setEnabled(false);
                logToTheTournament[2].setEnabled(false);
                logToTheTournament[3].setEnabled(false);
                logToTheTournament[4].setEnabled(false);
            } else if(comboBox.getSelectedItem() == "3"){
                logToTheTournament[0].setEnabled(true);
                logToTheTournament[1].setEnabled(true);
                logToTheTournament[2].setEnabled(false);
                logToTheTournament[3].setEnabled(false);
                logToTheTournament[4].setEnabled(false);
            }else if(comboBox.getSelectedItem() == "4"){
                logToTheTournament[0].setEnabled(true);
                logToTheTournament[1].setEnabled(true);
                logToTheTournament[2].setEnabled(true);
                logToTheTournament[3].setEnabled(false);
                logToTheTournament[4].setEnabled(false);
            }else if(comboBox.getSelectedItem() == "5"){
                logToTheTournament[0].setEnabled(true);
                logToTheTournament[1].setEnabled(true);
                logToTheTournament[2].setEnabled(true);
                logToTheTournament[3].setEnabled(true);
                logToTheTournament[4].setEnabled(false);
            }else{
                logToTheTournament[0].setEnabled(true);
                logToTheTournament[1].setEnabled(true);
                logToTheTournament[2].setEnabled(true);
                logToTheTournament[3].setEnabled(true);
                logToTheTournament[4].setEnabled(true);
            }
            this.revalidate();
            this.repaint();
        } else if(graj == e.getSource()){
            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
            int valueHolder = slider_par.getValue();
            switch (this.getCurrentPanelName()) {
                case "Gra dla 1 gracza":

                    int radioHolder = poziom_bot_radio.getWhichRadioButton();
                    this.getContentPane().removeAll();
                    this.getContentPane().add(new Game1Panel(valueHolder, radioHolder, login, threadSoundEffects, threadMusic, audio, arrow_left, effects, this));
                    currentPanelName = "Gra1PanelGame";
                    this.validate();
                    this.repaint();

                    break;
                case "Gra dla 2 graczy":

                    boolean poprawny_login_i_haslo = false;
                    try {
                        File file = new File("src\\menu\\ustawienia\\login_i_haslo.txt");
                        Scanner scan = new Scanner(file);
                        while(scan.hasNext()){
                            String sprawdz_login = scan.next();
                            String sprawdz_haslo = scan.next();
                            if(sprawdz_login.equals(poleDla2Gracza.getText()) && sprawdz_haslo.equals(hasloUkryte.getText()) && !sprawdz_login.equals(login)){
                                poprawny_login_i_haslo = true;
                                break;
                            }
                        }
                        if(!(poprawny_login_i_haslo)) {
                            threadSoundEffects.run("effects\\mixkit-click-error-1110.wav",threadSoundEffects.getPlay());
                            JOptionPane.showMessageDialog(null, "Nie poprawny login albo hasło albo już ten użytkownik jest zalogowany. Spróbuj jeszcze raz!", "Błąd.", JOptionPane.INFORMATION_MESSAGE);
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
                        this.getContentPane().removeAll();
                        this.getContentPane().add(new Game2Panel(valueHolder, login, poleDla2Gracza.getText(), threadSoundEffects, threadMusic, audio, arrow_left, effects, this));
                        currentPanelName = "Gra1PanelGame";
                        this.validate();
                        this.repaint();
                    }

                    break;
                case "Turniej":
                    String path = "src\\menu\\ustawienia\\turnieje\\"+turniejTextField.getText()+".txt";
                    File file = new File(path);
                    if(file.exists()){
                        int answer = JOptionPane.showConfirmDialog(null,"Turniej już istnieje. Chcesz kontynuować rozgrywkę?","",JOptionPane.YES_NO_OPTION);
                        threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
                        if(answer == 0) {
                            this.getContentPane().removeAll();
                            this.getContentPane().add(new Game3TournamentLabel(path, turniejTextField.getText(), threadSoundEffects, threadMusic, audio, arrow_left, effects, this));
                            currentPanelName = "Game3TournamentLabel";
                            this.validate();
                            this.repaint();
                        }

                    }else if(comboBox.getSelectedIndex() + 2 == loginy.size()){
                        try {

                            new Formatter(path);
                            file = new File(path);
                            FileWriter fw = new FileWriter(file, true);
                            int x = 1, y = 0, z = 2;
                            do{
                                fw.write(loginy.get(x)+" "+loginy.get(y)+" 0\n");
                                if(x == loginy.size() - 1 && y == 0) break;
                                x++;
                                y++;
                                if(x > loginy.size() - 1){
                                    x = z;
                                    y = 0;
                                    z++;
                                }
                            } while(true);
                            fw.write( String.valueOf(valueHolder));
                            //TODO tutaj dodac ile par fw.write(valueHolder);
                            fw.close();

                            this.getContentPane().removeAll();
                            this.getContentPane().add(new Game3TournamentLabel(path ,turniejTextField.getText(), threadSoundEffects, threadMusic, audio, arrow_left, effects, this));
                            currentPanelName = "Game3TournamentLabel";
                            this.validate();
                            this.repaint();

                        } catch (IOException fileNotFoundException) {
                            threadSoundEffects.run("effects\\mixkit-click-error-1110.wav",threadSoundEffects.getPlay());
                            fileNotFoundException.printStackTrace();
                            JOptionPane.showMessageDialog(null,"Coś poszło nie tak :(","Error",JOptionPane.ERROR_MESSAGE);
                            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
                            System.exit(0);
                        }
                    }
                    break;
            }
        }
    }
}
