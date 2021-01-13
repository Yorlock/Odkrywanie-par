package components;

import menu.ThreadSoundEffects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Card extends JButton {
    int para;
    int revers;
    int front;
    boolean picked = false;
    ImageIcon scaledIconRevers, scaledIconFront;
    ThreadSoundEffects threadSoundEffects;
    public Card(int para, int revers, int front, int width, int height, ThreadSoundEffects threadSoundEffects){

        this.para = para;
        this.revers = revers;
        this.front = front;
        this.threadSoundEffects = threadSoundEffects;

        this.setBorderPainted(false);
        this.setSize(Math.floorDiv(width+height,2), height);
        this.setOpaque(false);
        this.setBackground(Color.LIGHT_GRAY);
        //this.addActionListener(this);

        ImageIcon reversIcon, frontIcon;
        if(revers == 0){ reversIcon = new ImageIcon("images\\back_cards\\back_of_card_1.png"); }
        else if(revers == 1) { reversIcon = new ImageIcon("images\\back_cards\\back_of_card_2.png"); }
        else { reversIcon = new ImageIcon("images\\back_cards\\back_of_card_3.png"); }
        Image img = reversIcon.getImage();
        Image imgScale;
        if(width > height){ imgScale = img.getScaledInstance(height, height,Image.SCALE_SMOOTH); }
        else{ imgScale = img.getScaledInstance(width, width,Image.SCALE_SMOOTH);}
        scaledIconRevers = new ImageIcon(imgScale);
        this.setIcon(scaledIconRevers);
        if(front == 0){ frontIcon = new ImageIcon("images\\cards_0\\card_"+para+".png"); }
        else if(front == 1) { frontIcon = new ImageIcon("images\\cards_1\\card_"+para+".png"); }
        else { frontIcon = new ImageIcon("images\\cards_2\\card_"+para+".png"); }
        img = frontIcon.getImage();
        if(width > height){ imgScale = img.getScaledInstance(height, height,Image.SCALE_SMOOTH); }
        else{ imgScale = img.getScaledInstance(width, width,Image.SCALE_SMOOTH);}
        scaledIconFront = new ImageIcon(imgScale);

    }
    public void changeIcon(){
        if(this.getIcon() == scaledIconRevers){
            this.setIcon(scaledIconFront);
        } else {
            this.setIcon(scaledIconRevers);
        }
    }
    public void setPicked(boolean setter){
        picked = setter;
    }
    public int getPair(){
        return para;
    }
    public int getPara(){
        return para;
    }
/*
    @Override
    public void actionPerformed(ActionEvent e) {
        if(this == e.getSource()){
            threadSoundEffects.run("effects\\mixkit-video-game-mystery-alert-234.wav",threadSoundEffects.getPlay());
            this.changeIcon();
        }
    }

 */
}
