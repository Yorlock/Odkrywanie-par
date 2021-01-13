package menu;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThreadTime extends JLabel implements Runnable {
    int elapsedTime = 0;
    int seconds = 0;
    Timer timer;
    String time_string = String.format("%01d",seconds);
    public ThreadTime(){
        this.setText(time_string+"s");
        this.setFont(new Font("Silom", Font.BOLD,20));
        this.setForeground(Color.BLACK);
        this.setBackground(Color.LIGHT_GRAY);
        this.setBounds(1182,5,200,40);
        this.setBorder(BorderFactory.createBevelBorder(1));
        this.setOpaque(true);
        this.setHorizontalAlignment(JTextField.CENTER);
    }
    @Override
    public void run() {

        timer = new Timer(1000, new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                elapsedTime += 1000;
                seconds = elapsedTime/1000;
                time_string = String.format("%01d",seconds);
                ThreadTime.super.setText(time_string+"s");
            }
        });
        timer.start();
    }
    public int stop(){
        timer.stop();
        return seconds;
    }
}
