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

public class Game2Panel extends JLabel implements ActionListener {
    String login, oponent;
    ThreadSoundEffects threadSoundEffects;
    JLabel daneLabel, poleGryLabel;
    ThreadTime threadtime;
    ThreadMusic threadMusic;
    Card[] karty;
    components.TextLabel ktoVsKtoIPkt, kogoTura;
    int kogoTuraInt = 0, width, height;
    components.ButtonIcon audio, arrow_left, effects;
    int hGap = 12, vGap = 6, x_start, ileOdkrytychPar = 0;
    int graczaPkt = 0,gracza2Pkt = 0, ktoraTura = 1, ilePar, revers, frontCard,xIle, yIle;
    int liczbaKlikniecWTurze = 0;
    Frame_2 frame;
    List<Card> kartyOdkryteWTurze = new ArrayList<>();
    public Game2Panel(int ilePar, String login, String oponent, ThreadSoundEffects threadSoundEffects, ThreadMusic threadMusic, ButtonIcon audio, ButtonIcon arrow_left, ButtonIcon effects, Frame_2 frame){
        this.ilePar = ilePar;
        this.login = login;
        this.oponent = oponent;
        this.threadSoundEffects = threadSoundEffects;
        this.threadMusic = threadMusic;
        this.audio = audio;
        this.arrow_left = arrow_left;
        this.effects = effects;
        this.frame = frame;

        width = 1381;
        height = 712;
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

        x_start = 900 - (login.length() + oponent.length() + 14)*10;
        kogoTura = new TextLabel("Tura "+ktoraTura+": "+login, new Font("Silom", Font.BOLD,20),Color.BLACK,130,0,330,50, false);
        ktoVsKtoIPkt = new TextLabel(login+" - "+graczaPkt+" VS "+oponent+" - "+ gracza2Pkt, new Font("Silom", Font.BOLD,20),Color.BLACK,x_start,0,1000,50, false);
        threadtime = new ThreadTime();
        threadtime.run();

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
            int helper = Math.abs(gracza2Pkt - graczaPkt);
            ktoVsKtoIPkt.setText(login+" - "+graczaPkt+" VS "+oponent+" - "+ gracza2Pkt);
            int seconds = threadtime.stop();
            List<Integer> staty = new ArrayList<>();
            List<Integer> staty2Gracz = new ArrayList<>();
            Runnable threadUpdateHistoryOfGames;
            if(gracza2Pkt > graczaPkt){

                staty.add(0);
                staty.add(1);
                staty.add(0);

                staty2Gracz.add(1);
                staty2Gracz.add(0);
                staty2Gracz.add(0);

                threadUpdateHistoryOfGames = new ThreadUpdateHistoryOfGames("\nLiczba tur: "+ktoraTura+", liczba par: "+ilePar+", wygral "+oponent+" z "+login+", w czasie: "+seconds+" sekund z przewaga punktow: "+helper, threadSoundEffects, "src\\menu\\ustawienia\\spisgier\\"+login+".txt");
                Thread thread2 = new Thread(threadUpdateHistoryOfGames);
                thread2.start();
                threadUpdateHistoryOfGames = new ThreadUpdateHistoryOfGames("\nLiczba tur: "+ktoraTura+", liczba par: "+ilePar+", wygral "+oponent+" z "+login+", w czasie: "+seconds+" sekund z przewaga punktow: "+helper, threadSoundEffects, "src\\menu\\ustawienia\\spisgier\\"+oponent+".txt");
                Thread thread3 = new Thread(threadUpdateHistoryOfGames);
                thread3.start();
                threadSoundEffects.run("effects\\mixkit-video-game-win-2016.wav", threadSoundEffects.getPlay());
                JOptionPane.showMessageDialog(null,"Liczba tur: "+ktoraTura+", "+oponent+" wygrał o "+helper+" pkt.",oponent+" wygrał!",JOptionPane.INFORMATION_MESSAGE);
                threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());

            }else if(gracza2Pkt < graczaPkt){

                staty.add(1);
                staty.add(0);
                staty.add(0);

                staty2Gracz.add(0);
                staty2Gracz.add(1);
                staty2Gracz.add(0);

                threadUpdateHistoryOfGames = new ThreadUpdateHistoryOfGames("\nLiczba tur: "+ktoraTura+", liczba par: "+ilePar+", wygral "+login+" z "+oponent+", w czasie: "+seconds+" sekund z przewaga punktow: "+helper, threadSoundEffects, "src\\menu\\ustawienia\\spisgier\\"+login+".txt");
                Thread thread2 = new Thread(threadUpdateHistoryOfGames);
                thread2.start();
                threadUpdateHistoryOfGames = new ThreadUpdateHistoryOfGames("\nLiczba tur: "+ktoraTura+", liczba par: "+ilePar+", wygral "+login+" z "+oponent+", w czasie: "+seconds+" sekund z przewaga punktow: "+helper, threadSoundEffects, "src\\menu\\ustawienia\\spisgier\\"+oponent+".txt");
                Thread thread3 = new Thread(threadUpdateHistoryOfGames);
                thread3.start();
                threadSoundEffects.run("effects\\mixkit-video-game-win-2016.wav", threadSoundEffects.getPlay());
                JOptionPane.showMessageDialog(null,"Liczba tur: "+ktoraTura+", "+login+" wygrał o "+helper+" pkt.","Wygrałeś!",JOptionPane.INFORMATION_MESSAGE);
                threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
            }else{

                staty.add(0);
                staty.add(0);
                staty.add(1);

                staty2Gracz.add(0);
                staty2Gracz.add(0);
                staty2Gracz.add(1);

                threadUpdateHistoryOfGames = new ThreadUpdateHistoryOfGames("\nLiczba tur: "+ktoraTura+", liczba par: "+ilePar+", remis "+login+" z "+oponent+", w czasie: "+seconds+" sekund z liczba punktow: "+ilePar, threadSoundEffects, "src\\menu\\ustawienia\\spisgier\\"+login+".txt");
                Thread thread2 = new Thread(threadUpdateHistoryOfGames);
                thread2.start();

                threadUpdateHistoryOfGames = new ThreadUpdateHistoryOfGames("\nLiczba tur: "+ktoraTura+", liczba par: "+ilePar+", remis "+login+" z "+oponent+", w czasie: "+seconds+" sekund z liczba punktow: "+ilePar, threadSoundEffects, "src\\menu\\ustawienia\\spisgier\\"+oponent+".txt");
                Thread thread3 = new Thread(threadUpdateHistoryOfGames);
                thread3.start();

                threadSoundEffects.run("effects\\mixkit-video-game-win-2016.wav", threadSoundEffects.getPlay());
                JOptionPane.showMessageDialog(null,"Liczba tur: "+ktoraTura+", każdy z graczy miał "+graczaPkt+" pkt.","Remis!",JOptionPane.INFORMATION_MESSAGE);
                threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
            }
            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());

            staty.add(0);
            staty.add(0);
            staty.add(0);
            staty.add(1);
            staty.add(0);
            staty.add(0);
            staty.add(graczaPkt);
            staty.add(ktoraTura);
            staty.add(seconds);
            Runnable threadUpdateStatistics = new ThreadUpdateStatistics(staty, threadSoundEffects, "src\\menu\\dane\\"+login+".txt");
            Thread thread = new Thread(threadUpdateStatistics);
            thread.start();

            staty2Gracz.add(0);
            staty2Gracz.add(0);
            staty2Gracz.add(0);
            staty2Gracz.add(1);
            staty2Gracz.add(0);
            staty2Gracz.add(0);
            staty2Gracz.add(gracza2Pkt);
            staty2Gracz.add(ktoraTura);
            staty2Gracz.add(seconds);
            Runnable threadUpdateStatistics2 = new ThreadUpdateStatistics(staty2Gracz, threadSoundEffects, "src\\menu\\dane\\"+oponent+".txt");
            Thread thread2 = new Thread(threadUpdateStatistics2);
            thread2.start();

            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
            frame.setCurrentPanelName("MainPanel");
            frame.getContentPane().removeAll();
            frame.getContentPane().add(frame.MainPanel());
            frame.validate();
            frame.repaint();

            //TODO koniec gry (prosty komunikat jaki jak error albo yes/no, napisac kto wygral, ile do ilu i czy chcesz jeszcze raz zagrac) + zapis do pliku
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(liczbaKlikniecWTurze == 0 && kogoTuraInt == 0){
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
        }else if(liczbaKlikniecWTurze == 1 && kogoTuraInt == 0){
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
                        ktoVsKtoIPkt.setText(login+" - "+graczaPkt+" VS "+oponent+" - "+ gracza2Pkt);
                        liczbaKlikniecWTurze = 0;
                        if(ileOdkrytychPar == ilePar){
                            checkIfEnd();
                        }
                    } else{
                        threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
                        liczbaKlikniecWTurze++;
                    }
                    break;
                }
            }
        }else if(liczbaKlikniecWTurze == 2 && kogoTuraInt == 0){
            kartyOdkryteWTurze.get(0).changeIcon();
            kartyOdkryteWTurze.get(1).changeIcon();
            kartyOdkryteWTurze.get(0).setPicked(false);
            kartyOdkryteWTurze.get(1).setPicked(false);
            kartyOdkryteWTurze.clear();
            ktoVsKtoIPkt.setText(login+" - "+graczaPkt+" VS "+oponent+" - "+ gracza2Pkt);
            kogoTura.setText("Tura "+ktoraTura+": "+oponent);
            liczbaKlikniecWTurze = 0;
            kogoTuraInt = 1;
        } else if(liczbaKlikniecWTurze == 0 && kogoTuraInt == 1){
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
        }else if(liczbaKlikniecWTurze == 1 && kogoTuraInt == 1){
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
                        gracza2Pkt++;
                        ileOdkrytychPar++;
                        ktoVsKtoIPkt.setText(login+" - "+graczaPkt+" VS "+oponent+" - "+ gracza2Pkt);
                        liczbaKlikniecWTurze = 0;
                        if(ileOdkrytychPar == ilePar){
                            checkIfEnd();
                        }
                    } else{
                        threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
                        liczbaKlikniecWTurze++;
                    }
                    break;
                }
            }
        }else if(liczbaKlikniecWTurze == 2 && kogoTuraInt == 1){
            kartyOdkryteWTurze.get(0).changeIcon();
            kartyOdkryteWTurze.get(1).changeIcon();
            kartyOdkryteWTurze.get(0).setPicked(false);
            kartyOdkryteWTurze.get(1).setPicked(false);
            kartyOdkryteWTurze.clear();
            ktoraTura++;
            ktoVsKtoIPkt.setText(login+" - "+graczaPkt+" VS "+oponent+" - "+ gracza2Pkt);
            kogoTura.setText("Tura "+ktoraTura+": "+login);
            liczbaKlikniecWTurze = 0;
            kogoTuraInt = 0;
        }
    }
}
