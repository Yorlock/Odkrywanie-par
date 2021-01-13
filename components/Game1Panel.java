package components;

import menu.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game1Panel extends JLabel implements ActionListener { //TODO guzik "zakoncz turę" i keybinding na spacje?
    int width, height, ilePar, revers, frontCard;
    int xIle, yIle, levelBot;
    int hGap = 12, vGap = 6, x_start, ileOdkrytychPar = 0;
    String botNazwa, login;
    Frame_2 frame;
    int graczaPkt = 0,botPkt = 0, ktoraTura = 1;
    Card[] karty;
    ThreadSoundEffects threadSoundEffects;
    ThreadMusic threadMusic;
    JLabel daneLabel, poleGryLabel;
    components.TextLabel ktoVsKtoIPkt, kogoTura;
    components.ButtonIcon audio, arrow_left, effects;
    int liczbaKlikniecWTurze = 0;
    List<Card> kartyOdkryteWTurze = new ArrayList<>();
    ThreadTime threadtime;

    public Game1Panel(int ilePar,int levelBot, String login, ThreadSoundEffects threadSoundEffects, ThreadMusic threadMusic, ButtonIcon audio, ButtonIcon arrow_left, ButtonIcon effects, Frame_2 frame){

        this.frame = frame;
        this.audio = audio;
        this.arrow_left = arrow_left;
        this.effects = effects;
        this.ilePar = ilePar;
        width = 1381;
        height = 712;
        this.levelBot = levelBot;
        this.login = login;
        this.threadSoundEffects = threadSoundEffects;
        this.threadMusic = threadMusic;
        List<String> buffer = getInfoAboutCards("src\\menu\\ustawienia\\ustawienia.txt");
        revers = Integer.parseInt(buffer.get(0));
        frontCard = Integer.parseInt(buffer.get(1));
        this.setBounds(0,0,1400,800);
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(null);
        this.setOpaque(true);
        this.add(this.createLabelForData());
        this.add(this.createLabelForGameField());

    }
    public int[] getXAndYScaled(){
        return new int[] {Math.floorDiv(width,xIle) - hGap, Math.floorDiv(height,yIle) - vGap};
    }
    private List<Integer> losujKolejnosc(int min, int max){
        java.util.List<Integer> kolejnosc = new ArrayList<>();
        for(int i = min; i <= max; i++) kolejnosc.add(i);
        List<Integer> losowo = new ArrayList<>();
        int random;
        while(kolejnosc.size() != 0){
            random = (int)(Math.random()*(kolejnosc.size()));
            losowo.add(kolejnosc.get(random));
            kolejnosc.remove(random);
        }
        return losowo;
    }
    private List<String> getInfoAboutCards(String path){
        List<String> buffer = new ArrayList<>();
        try{
            Scanner scan = new Scanner(new File(path));
            scan.next(); scan.next(); scan.next(); scan.next();
            buffer.add(scan.next()); buffer.add(scan.next());
            scan.close();
        } catch(Exception e){
            threadSoundEffects.run("effects\\mixkit-click-error-1110.wav",threadSoundEffects.getPlay());
            JOptionPane.showMessageDialog(null,"Coś poszło nie tak :(","Error",JOptionPane.ERROR_MESSAGE);
            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
            System.exit(0);
        }
        return buffer;
    }
    private JLabel createLabelForGameField(){
        poleGryLabel = new JLabel();
        poleGryLabel.setBounds(2,49, width, height);
        poleGryLabel.setBackground(Color.LIGHT_GRAY);
        poleGryLabel.setOpaque(true);
        poleGryLabel.setBorder(BorderFactory.createBevelBorder(1));
        int ileObjektow = 2*ilePar;
        for(int i = 2; i <= 8; i++){
            if(i*i >= ileObjektow){
                poleGryLabel.setLayout(new GridLayout(i,i,hGap,vGap));
                xIle = i;
                yIle = i;
                break;
            }
        }

        karty = new Card[ilePar*2];
        int[] xAndY = getXAndYScaled();
        for(int i = 1; i <= 2*ilePar; i++) karty[i-1] = new Card(Math.floorDiv(i+1,2),revers,frontCard,xAndY[1],xAndY[1], threadSoundEffects);
        for(Integer var : losujKolejnosc(0,ilePar*2 - 1)) {poleGryLabel.add(karty[var]); karty[var].addActionListener(this);}

        return  poleGryLabel;
    }
    private JLabel createLabelForData(){
        if(levelBot == 0) botNazwa = "Nowiciusz";
        else if(levelBot == 1) botNazwa = "Doświadczony zawodnik";
        else botNazwa = "Arcymistrz";


        x_start = 900 - (login.length() + botNazwa.length() + 14)*10;
        kogoTura = new TextLabel("Tura "+ktoraTura+": "+login, new Font("Silom", Font.BOLD,20),Color.BLACK,130,0,330,50, false);
        ktoVsKtoIPkt = new TextLabel(login+" - "+graczaPkt+" VS "+botNazwa+" - "+ botPkt, new Font("Silom", Font.BOLD,20),Color.BLACK,x_start,0,1000,50, false);
        threadtime = new ThreadTime();
        threadtime.run();
        //Thread threadtime = new Thread(time);
        //threadtime.start();

        daneLabel = new JLabel();
        daneLabel.setLayout(null);
        daneLabel.setBounds(0,0,1400,50);
        daneLabel.setBackground(Color.LIGHT_GRAY);
        daneLabel.setOpaque(true);
        daneLabel.add(audio);
        daneLabel.add(arrow_left);
        daneLabel.add(effects);
        daneLabel.add(threadtime);
        daneLabel.add(ktoVsKtoIPkt);
        daneLabel.add(kogoTura);
        return daneLabel;
    }
    private void checkIfEnd(){
        if(ileOdkrytychPar == ilePar){
            int helper = Math.abs(botPkt - graczaPkt);
            ktoVsKtoIPkt.setText(login+" - "+graczaPkt+" VS "+botNazwa+" - "+ botPkt);
            int seconds = threadtime.stop();
            Runnable threadUpdateHistoryOfGames;
            List<Integer> staty = new ArrayList<>();
            if(botPkt > graczaPkt){
                staty.add(0);
                staty.add(1);
                staty.add(0);
                threadUpdateHistoryOfGames = new ThreadUpdateHistoryOfGames("\nLiczba tur: "+ktoraTura+", liczba par: "+ilePar+", wygral bot "+botNazwa+" z "+login+", w czasie: "+seconds+" sekund z przewaga punktow: "+helper, threadSoundEffects, "src\\menu\\ustawienia\\spisgier\\"+login+".txt");
                Thread thread2 = new Thread(threadUpdateHistoryOfGames);
                thread2.start();
                threadSoundEffects.run("effects\\mixkit-retro-arcade-lose-2027.wav", threadSoundEffects.getPlay());
                JOptionPane.showMessageDialog(null,"Liczba tur: "+ktoraTura+", "+botNazwa+" wygrał o "+helper+" pkt.",botNazwa+" wygrał!",JOptionPane.INFORMATION_MESSAGE);
                threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());

            }else if(botPkt < graczaPkt){
                staty.add(1);
                staty.add(0);
                staty.add(0);
                threadUpdateHistoryOfGames = new ThreadUpdateHistoryOfGames("\nLiczba tur: "+ktoraTura+", liczba par: "+ilePar+", wygral "+login+" z botem "+botNazwa+", w czasie: "+seconds+" sekund z przewaga punktow: "+helper, threadSoundEffects, "src\\menu\\ustawienia\\spisgier\\"+login+".txt");
                Thread thread2 = new Thread(threadUpdateHistoryOfGames);
                thread2.start();
                threadSoundEffects.run("effects\\mixkit-video-game-win-2016.wav", threadSoundEffects.getPlay());
                JOptionPane.showMessageDialog(null,"Liczba tur: "+ktoraTura+", "+login+" wygrał o "+helper+" pkt.","Wygrałeś!",JOptionPane.INFORMATION_MESSAGE);
                threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
            }else{
                staty.add(0);
                staty.add(0);
                staty.add(1);
                threadUpdateHistoryOfGames = new ThreadUpdateHistoryOfGames("\nLiczba tur: "+ktoraTura+",liczba par: "+ilePar+", remis gracza "+login+" z botem "+botNazwa+", w czasie: "+seconds+" sekund z liczbą punktow: "+ilePar, threadSoundEffects, "src\\menu\\ustawienia\\spisgier\\"+login+".txt");
                Thread thread2 = new Thread(threadUpdateHistoryOfGames);
                thread2.start();
                threadSoundEffects.run("effects\\mixkit-video-game-win-2016.wav", threadSoundEffects.getPlay());
                JOptionPane.showMessageDialog(null,"Liczba tur: "+ktoraTura+", każdy z graczy miał "+graczaPkt+" pkt.","Remis!",JOptionPane.INFORMATION_MESSAGE);
                threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
            }
            if(levelBot == 0){
                staty.add(1);
                staty.add(0);
                staty.add(0);
            } else if(levelBot == 1){
                staty.add(0);
                staty.add(1);
                staty.add(0);
            }else{
                staty.add(0);
                staty.add(0);
                staty.add(1);
            }
            staty.add(0);
            staty.add(0);
            staty.add(0);
            staty.add(graczaPkt);
            staty.add(ktoraTura);
            staty.add(seconds);
            Runnable threadUpdateStatistics = new ThreadUpdateStatistics(staty, threadSoundEffects, "src\\menu\\dane\\"+login+".txt");
            Thread thread = new Thread(threadUpdateStatistics);
            thread.start();
            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());

            frame.setCurrentPanelName("MainPanel");
            frame.getContentPane().removeAll();
            frame.getContentPane().add(frame.MainPanel());
            frame.validate();
            frame.repaint();
        }
    }
    private void bot(int szansa){
        int random = (int) (Math.random() * szansa);
        if(random == 1){
            int ile = 0;
            for(Card var : karty){
                if(!var.picked){
                    var.changeIcon();
                    var.picked = true;
                    var.setEnabled(false);
                    ile++;
                }
                if(ile == 2) break;
            }
            ileOdkrytychPar++;
            botPkt++;
        }
        if(ileOdkrytychPar == ilePar - 1){
            for(Card var : karty){
                if(!var.picked){
                    var.changeIcon();
                    var.picked = true;
                    var.setEnabled(false);
                }
            }
            ileOdkrytychPar++;
            botPkt++;
            checkIfEnd();
        } else {
            while(ileOdkrytychPar != ilePar - 1){
                if(kartyOdkryteWTurze.size() == 2){
                    if(kartyOdkryteWTurze.get(0).getPara() == kartyOdkryteWTurze.get(1).getPara()){
                        kartyOdkryteWTurze.get(0).setEnabled(false);
                        kartyOdkryteWTurze.get(1).setEnabled(false);
                        kartyOdkryteWTurze.clear();
                        ileOdkrytychPar++;
                        botPkt++;
                        if(ileOdkrytychPar == ilePar - 1){
                            for(Card var : karty){
                                if(!var.picked){
                                    var.changeIcon();
                                    var.picked = true;
                                    var.setEnabled(false);
                                }
                            }
                            ileOdkrytychPar++;
                            botPkt++;
                            checkIfEnd();
                            break;
                        }
                    } else break;
                }
                random = (int)(Math.random()*(2*ilePar - 1));
                if (!karty[random].picked){
                    kartyOdkryteWTurze.add(karty[random]);
                    karty[random].picked = true;
                    karty[random].changeIcon();
                }
            }
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {

        if(liczbaKlikniecWTurze == 0){
            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
            for(Card var : karty){
                if(e.getSource() == var && !(var.picked)){
                    var.changeIcon();
                    var.setPicked(true);
                    kartyOdkryteWTurze.add(var);
                    liczbaKlikniecWTurze++;
                    break;
                }
            }
        }else if(liczbaKlikniecWTurze == 1){
            for(Card var : karty){
                if(e.getSource() == var && !(var.picked)){
                    var.changeIcon();
                    var.setPicked(true);
                    kartyOdkryteWTurze.add(var);
                    if(kartyOdkryteWTurze.get(0).getPara() == kartyOdkryteWTurze.get(1).getPara()){
                        threadSoundEffects.run("effects\\mixkit-fantasy-game-success-notification-270.wav", threadSoundEffects.getPlay());
                        kartyOdkryteWTurze.get(0).setEnabled(false);
                        kartyOdkryteWTurze.get(1).setEnabled(false);
                        kartyOdkryteWTurze.clear();
                        graczaPkt++;
                        ileOdkrytychPar++;
                        ktoVsKtoIPkt.setText(login+" - "+graczaPkt+" VS "+botNazwa+" - "+ botPkt);
                        liczbaKlikniecWTurze = 0;
                        if(ileOdkrytychPar == ilePar){
                            //winner = login;
                            checkIfEnd();
                        }
                    } else{
                        threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
                        liczbaKlikniecWTurze++;
                    }
                    break;
                }
            }
        }else if(liczbaKlikniecWTurze == 2){
            kartyOdkryteWTurze.get(0).changeIcon();
            kartyOdkryteWTurze.get(1).changeIcon();
            kartyOdkryteWTurze.get(0).setPicked(false);
            kartyOdkryteWTurze.get(1).setPicked(false);
            kartyOdkryteWTurze.clear();
            ktoVsKtoIPkt.setText(login+" - "+graczaPkt+" VS "+botNazwa+" - "+ botPkt);
            kogoTura.setText("Tura "+ktoraTura+": "+botNazwa);
            if(levelBot == 0){
                bot(100);
            }else if(levelBot == 1){
                bot(20);
            }else{
                bot(4);
            }
            liczbaKlikniecWTurze = 3;
        } else if (liczbaKlikniecWTurze == 3){
            kartyOdkryteWTurze.get(0).changeIcon();
            kartyOdkryteWTurze.get(1).changeIcon();
            kartyOdkryteWTurze.get(0).setPicked(false);
            kartyOdkryteWTurze.get(1).setPicked(false);
            kartyOdkryteWTurze.clear();
            ktoraTura++;
            ktoVsKtoIPkt.setText(login+" - "+graczaPkt+" VS "+botNazwa+" - "+ botPkt);
            kogoTura.setText("Tura "+ktoraTura+": "+login);
            liczbaKlikniecWTurze = 0;
        }
    }
}
