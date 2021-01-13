package components;

import menu.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game3TournamentLabel extends JLabel implements ActionListener {
    List<String> bufferMeczy = new ArrayList<>();
    int ilePar, width, height, revers, frontCard,kogoTuraInt = 0,graczaPkt = 0,gracza2Pkt = 0, ktoraTura = 1;
    int hGap = 12, vGap = 6, x_start, ileOdkrytychPar = 0,xIle, yIle, liczbaKlikniecWTurze = 0;
    JLabel daneLabel, poleGryLabel, rankingLabel;
    ThreadTime threadtime;
    Card[] karty;
    User[] users;
    ThreadSoundEffects threadSoundEffects;
    components.TextLabel ktoVsKtoIPkt, kogoTura;
    ThreadMusic threadMusic;
    ButtonIcon audio, arrow_left, effects;
    Button rozpocznijKolejnyMecz;
    Frame_2 frame;
    String login, oponent, listaMeczyText;
    List<Card> kartyOdkryteWTurze = new ArrayList<>();
    List<String> usersList = new ArrayList<>();
    String nazwaTurnieju;
    String path;
    int liczbaRozegranychMeczy;
    public Game3TournamentLabel(String path, String nazwaTurnieju, ThreadSoundEffects threadSoundEffects, ThreadMusic threadMusic, ButtonIcon audio, ButtonIcon arrow_left, ButtonIcon effects, Frame_2 frame){

        this.path = path;
        this.threadSoundEffects = threadSoundEffects;
        this.threadMusic = threadMusic;
        this.audio = audio;
        this.arrow_left = arrow_left;
        this.effects = effects;
        this.frame = frame;
        this.nazwaTurnieju = nazwaTurnieju;

        try {
            List<String> helper = new ArrayList<>();
            Scanner scan = new Scanner(new File(path));
            while(scan.hasNextLine()) helper.add(scan.nextLine());
            scan.close();
            ilePar = Integer.parseInt(helper.get(helper.size() - 1));
            helper.remove(helper.size() - 1);
            for(String var : helper){
                String[] help = var.split(" ");
                if(!usersList.contains(help[0])) usersList.add(help[0]);
                if(!usersList.contains(help[1])) usersList.add(help[1]);
            }
            users = new User[usersList.size()];
            for(int i = 0; i < usersList.size(); i++) users[i] = new User(0.0f, usersList.get(i));
        }catch (FileNotFoundException e) {
            threadSoundEffects.run("effects\\mixkit-click-error-1110.wav",threadSoundEffects.getPlay());
            JOptionPane.showMessageDialog(null,"Coś poszło nie tak :(","Error",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
            System.exit(0);
        }

        width = 1381;
        height = 712;

        List<String> buffer = getInfoAboutCards("src\\menu\\ustawienia\\ustawienia.txt");
        revers = Integer.parseInt(buffer.get(0));
        frontCard = Integer.parseInt(buffer.get(1));

        this.setBounds(0,0,1400,800);
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(null);
        this.setOpaque(true);

        this.add(this.createLabelForStats());
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
    private JLabel createLabelForStats(){
        rankingLabel = new JLabel();

        rankingLabel.setLayout(null);
        rankingLabel.setBounds(0,0,1400,800);
        rankingLabel.setBackground(Color.LIGHT_GRAY);
        rankingLabel.setOpaque(true);

        kogoTuraInt = 0;
        graczaPkt = 0;
        gracza2Pkt = 0;
        ktoraTura = 1;
        ileOdkrytychPar = 0;
        liczbaKlikniecWTurze = 0;

        bufferMeczy.clear();
        try {
            Scanner scan = new Scanner(new File(path));
            while(scan.hasNextLine()) bufferMeczy.add(scan.nextLine());
            scan.close();
            bufferMeczy.remove(bufferMeczy.size() - 1);
        }catch (FileNotFoundException e) {
            threadSoundEffects.run("effects\\mixkit-click-error-1110.wav",threadSoundEffects.getPlay());
            JOptionPane.showMessageDialog(null,"Coś poszło nie tak :(","Error",JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
            System.exit(0);
        }

        JLabel obecnyRanking = new JLabel();
        obecnyRanking.setLayout(null);
        obecnyRanking.setBounds(10,100,400,550);
        obecnyRanking.setBackground(Color.LIGHT_GRAY);
        obecnyRanking.setBorder(BorderFactory.createBevelBorder(1));
        obecnyRanking.setOpaque(true);
        obecnyRanking.add(new TextLabel("Ranking", new Font("Silom", Font.BOLD,30),Color.BLUE,135,0,200,50, false));

        List<String> textRanking = new ArrayList<>();
        for(User user : users) {
            user.punkty = 0.0f;
        }

        for(String var : bufferMeczy){
            String[] helper = var.split(" ");
            for(User user : users){
                if(helper[2].equals("1") && user.nazwa.equals(helper[0])) user.punkty += (float) 1.0;
                else if(helper[2].equals("2") && user.nazwa.equals(helper[1])) user.punkty += (float) 1.0;
                else if(helper[2].equals("3") && (user.nazwa.equals(helper[1]) || user.nazwa.equals(helper[0]))) user.punkty += (float) 0.5;
            }
        }
        for(float i = 6.0f; i >= 0.0f; i-=0.5f){
            for(User user : users)
                if(user.punkty == i) textRanking.add(user.nazwa+": "+user.punkty+"\n");
        }
        TextArea textAreaRanking = new TextArea();
        textAreaRanking.setForeground(Color.black);
        textAreaRanking.setFont(new Font("Silom", Font.BOLD,20));
        textAreaRanking.setForeground(Color.BLACK);
        textAreaRanking.setEditable(false);
        String helper = String.join("",textRanking);
        textAreaRanking.setText(helper);
        textAreaRanking.setBounds(0,50,420,520);
        textAreaRanking.setEnabled(false);
        obecnyRanking.add(textAreaRanking);

        List<String> textMecze = new ArrayList<>();
        liczbaRozegranychMeczy = 0;
        for(String mecze : bufferMeczy){
            String[] help = mecze.split(" ");
            liczbaRozegranychMeczy++;
            if(help[2].equals("1")) textMecze.add(help[0]+"  1 : 0  "+help[1]+"\n");
            else if(help[2].equals("2")) textMecze.add(help[0]+"  0 : 1  "+help[1]+"\n");
            else if(help[2].equals("3")) textMecze.add(help[0]+"  0.5 : 0.5  "+help[1]+"\n");
            else {textMecze.add(help[0]+"  0 : 0  "+help[1]+"\n"); liczbaRozegranychMeczy--;}
        }
        JLabel listaMeczy = new JLabel();
        listaMeczy.setLayout(null);
        listaMeczy.setBounds(550,100,800,550);
        listaMeczy.setBackground(Color.LIGHT_GRAY);
        listaMeczy.setBorder(BorderFactory.createBevelBorder(1));
        listaMeczy.setOpaque(true);
        listaMeczy.add(new TextLabel("Lista meczy:", new Font("Silom", Font.BOLD,30),Color.BLUE,315,0,200,50, false));

        TextArea textAreaMecze = new TextArea();
        textAreaMecze.setForeground(Color.black);
        textAreaMecze.setFont(new Font("Silom", Font.BOLD,20));
        textAreaMecze.setForeground(Color.BLACK);
        textAreaMecze.setEditable(false);
        listaMeczyText = "";
        listaMeczyText = String.join("",textMecze);
        textAreaMecze.setText(listaMeczyText);
        textAreaMecze.setBounds(0,50,800,520);
        textAreaMecze.setEnabled(false);
        listaMeczy.add(textAreaMecze);

        rozpocznijKolejnyMecz = new Button("Kolejny mecz",593,680,200,50, new Font("Silom", Font.BOLD,20));
        //TODO po kliknieciu guzika odpal kolejny mecz (czytajac dane zapisac sobie kto ma grac?)
        //TODO jakos ogarnac by to sie latwo jakos ladnie zapetlalo
        //TODO po zakonczeniu meczu OD RAZU ZAPISAC i zrobic przycisk by wrocic do rankingu?
        if(liczbaRozegranychMeczy == bufferMeczy.size()) rozpocznijKolejnyMecz.setText("Zakoncz turniej");
        rozpocznijKolejnyMecz.addActionListener(this);
        rankingLabel.add(new TextLabel(nazwaTurnieju, new Font("Silom", Font.BOLD,60),Color.BLUE,550,-50,600,200, false));
        rankingLabel.add(audio);
        rankingLabel.add(arrow_left);
        rankingLabel.add(effects);
        rankingLabel.add(obecnyRanking);
        rankingLabel.add(listaMeczy);
        rankingLabel.add(rozpocznijKolejnyMecz);

        return rankingLabel;
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

                Runnable threadUpdateTournament = new Thread(new ThreadUpdateTournament(path,liczbaRozegranychMeczy,login+" "+oponent+" 2", threadSoundEffects));
                Thread thread = new Thread(threadUpdateTournament);
                thread.start();

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

                Runnable threadUpdateTournament = new Thread(new ThreadUpdateTournament(path,liczbaRozegranychMeczy,login+" "+oponent+" 1", threadSoundEffects));
                Thread thread = new Thread(threadUpdateTournament);
                thread.start();

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

                Runnable threadUpdateTournament = new Thread(new ThreadUpdateTournament(path,liczbaRozegranychMeczy,login+" "+oponent+" 3", threadSoundEffects));
                Thread thread = new Thread(threadUpdateTournament);
                thread.start();

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

            staty.add(0);
            staty.add(1);

            if(gracza2Pkt > graczaPkt) staty.add(0);
            else if (gracza2Pkt < graczaPkt) staty.add(1);

            staty.add(graczaPkt);
            staty.add(ktoraTura);
            staty.add(seconds);
            Runnable threadUpdateStatistics = new ThreadUpdateStatistics(staty, threadSoundEffects, "src\\menu\\dane\\"+login+".txt");
            Thread thread = new Thread(threadUpdateStatistics);
            thread.start();

            staty2Gracz.add(0);
            staty2Gracz.add(0);
            staty2Gracz.add(0);

            staty2Gracz.add(0);
            staty2Gracz.add(1);
            
            if(gracza2Pkt > graczaPkt) staty2Gracz.add(1);
            else if (gracza2Pkt < graczaPkt) staty2Gracz.add(0);

            staty2Gracz.add(gracza2Pkt);
            staty2Gracz.add(ktoraTura);
            staty2Gracz.add(seconds);
            Runnable threadUpdateStatistics2 = new ThreadUpdateStatistics(staty2Gracz, threadSoundEffects, "src\\menu\\dane\\"+oponent+".txt");
            Thread thread2 = new Thread(threadUpdateStatistics2);
            thread2.start();

            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());

            this.removeAll();
            this.add(createLabelForStats());
            this.revalidate();
            this.repaint();

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == rozpocznijKolejnyMecz && rozpocznijKolejnyMecz.getText().equals("Zakoncz turniej")){
            File file = new File(path);
            try{

                File fileFull = new File(file.getAbsolutePath());
                fileFull.delete();

                frame.setCurrentPanelName("MainPanel");
                frame.getContentPane().removeAll();
                frame.getContentPane().add(frame.MainPanel());
                frame.validate();
                frame.repaint();

            }catch(Exception x){
                threadSoundEffects.run("effects\\mixkit-click-error-1110.wav",threadSoundEffects.getPlay());
                JOptionPane.showMessageDialog(null,"Coś poszło nie tak :(","Error",JOptionPane.ERROR_MESSAGE);
                threadSoundEffects.run("effects\\mixkit-click-error-1110.wav",threadSoundEffects.getPlay());
                x.printStackTrace();
                System.exit(0);
            }


        }else if(e.getSource() == rozpocznijKolejnyMecz && liczbaRozegranychMeczy != bufferMeczy.size()){
            String[] help = bufferMeczy.get(liczbaRozegranychMeczy).split(" ");
            this.removeAll();
            login = help[0];
            oponent = help[1];
            this.add(createLabelForGameField());
            this.add(createLabelForData());
            this.revalidate();
            this.repaint();
        }else if(liczbaKlikniecWTurze == 0 && kogoTuraInt == 0){
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
