package components;

import menu.ThreadSoundEffects;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Scanner;

public class LogToTheTournament extends JLabel implements ActionListener {
    Button button;
    TextFieldMy login;
    PasswordFieldMy password;
    ThreadSoundEffects threadSoundEffects;
    List<String> loginy;
    Color colorOn;
    public LogToTheTournament(int x, int y, String nazwa, List<String> loginy, ThreadSoundEffects threadSoundEffects){
        this.loginy = loginy;
        this.threadSoundEffects = threadSoundEffects;
        login = new TextFieldMy(75,5,150,50,new Font("Silom", Font.BOLD,16),nazwa);
        password = new PasswordFieldMy(75,80,150,50,new Font("Silom", Font.BOLD,16),"haslo");
        button = new Button("Zaloguj",80,140,140,50,new Font("Silom", Font.BOLD,16));

        this.setBounds(x, y, 300, 200);
        this.setBackground(Color.LIGHT_GRAY);
        this.setBorder(BorderFactory.createBevelBorder(1));
        colorOn = button.getBackground();
        button.addActionListener(this);
        this.add(button);
        this.add(login);
        this.add(password);

    }

    public void setEnabled(boolean bool){
        button.setText("Zaloguj");
        login.setEnabled(bool);
        password.setEnabled(bool);
        button.setEnabled(bool);
        if(bool) {
            button.setBackground(colorOn);
            button.setForeground(Color.ORANGE);
        }
        else {
            button.setBackground(Color.DARK_GRAY);
            button.setForeground(Color.GRAY);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == button){
            boolean poprawny_login_i_haslo = false;
            try {
                File file = new File("src\\menu\\ustawienia\\login_i_haslo.txt");
                Scanner scan = new Scanner(file);
                while(scan.hasNext()){
                    boolean czyJuzIstnieje = false;
                    String sprawdz_login = scan.next();
                    String sprawdz_haslo = scan.next();

                    for(String var : loginy){
                        if(var.equals(sprawdz_login)) czyJuzIstnieje = true;
                    }
                    if(sprawdz_login.equals(login.getText()) && sprawdz_haslo.equals(password.getText()) && !czyJuzIstnieje){
                        poprawny_login_i_haslo = true;
                        break;
                    }
                }
                if(!(poprawny_login_i_haslo)) {
                    threadSoundEffects.run("effects\\mixkit-click-error-1110.wav",threadSoundEffects.getPlay());
                    JOptionPane.showMessageDialog(null, "Nie poprawny login albo hasło albo już jesteś zalogowany.", "Błąd.", JOptionPane.INFORMATION_MESSAGE);
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
                loginy.add(login.getText());
                button.setEnabled(false);
                login.setEnabled(false);
                password.setEnabled(false);
                button.setBackground(Color.ORANGE);
                button.setText("Zalogowano");

            }
        }
    }
}
