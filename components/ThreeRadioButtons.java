package components;

import menu.ThreadSoundEffects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThreeRadioButtons extends JPanel implements ActionListener {
    ButtonGroup group;
    JRadioButton one, two, three;
    ImageIcon helper;
    JLabel icon;
    String[] paths;
    ThreadSoundEffects threadSoundEffects;
    public ThreeRadioButtons(int x, int y, int width, int height, Font font, String texts[], String paths[], ThreadSoundEffects threadSoundEffects, int whichOne){
        this.threadSoundEffects = threadSoundEffects;
        this.paths = paths;

        one = new JRadioButton(texts[0]);
        two = new JRadioButton(texts[1]);
        three = new JRadioButton(texts[2]);
        one.setFont(font);
        two.setFont(font);
        three.setFont(font);
        icon = new JLabel();


        this.setLayout(null);
        this.setBounds(x,y,width,height);
        this.setBackground(Color.LIGHT_GRAY);

        icon.setBounds(100,0,300,300);
        icon.setLayout(null);
        //icon.setOpaque(true);
        try {
            helper = new ImageIcon(paths[0]);
            Image img = helper.getImage();
            Image imgScale = img.getScaledInstance(icon.getWidth(), icon.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(imgScale);
            icon.setIcon(scaledIcon);
        }catch(Exception e){
            threadSoundEffects.run("effects\\mixkit-click-error-1110.wav",threadSoundEffects.getPlay());
            JOptionPane.showMessageDialog(null,"Coś poszło nie tak :(","Error",JOptionPane.ERROR_MESSAGE);
            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
            System.exit(0);
        }

        one.setBackground(Color.LIGHT_GRAY);
        one.setBounds(0,0,100,25);
        two.setBackground(Color.LIGHT_GRAY);
        two.setBounds(0,100,100,25);
        three.setBackground(Color.LIGHT_GRAY);
        three.setBounds(0,200,100,25);
        if(whichOne == 0){
            one.setSelected(true);
            SelectIcon(0);
        }else if(whichOne == 1){
            two.setSelected(true);
            SelectIcon(1);
        }else{
            three.setSelected(true);
            SelectIcon(2);
        }

        group = new ButtonGroup();
        group.add(one);
        group.add(two);
        group.add(three);

        one.addActionListener(this);
        two.addActionListener(this);
        three.addActionListener(this);

        this.add(one);
        this.add(two);
        this.add(three);
        this.add(icon);
    }
    public void SelectIcon(int option){
        try {
            helper = new ImageIcon(paths[option]);
            Image img = helper.getImage();
            Image imgScale = img.getScaledInstance(icon.getWidth(), icon.getHeight(), Image.SCALE_SMOOTH);
            ImageIcon scaledIcon = new ImageIcon(imgScale);
            icon.setIcon(scaledIcon);
        } catch(Exception e){
            threadSoundEffects.run("effects\\mixkit-click-error-1110.wav",threadSoundEffects.getPlay());
            JOptionPane.showMessageDialog(null,"Coś poszło nie tak :(","Error",JOptionPane.ERROR_MESSAGE);
            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
            System.exit(0);
        }
    }
    public int getWhichRadioButton(){
        if(one.isSelected()){
            return 0;
        }else if (two.isSelected()){
            return 1;
        }else{
            return 2;
        }
    }
    public String getPath(){
        if(one.isSelected()){
            return paths[0];
        }else if (two.isSelected()){
            return paths[1];
        }else{
            return paths[2];
        }
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == one){
            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
            SelectIcon(0);
            repaint();
        } else if(e.getSource() == two){
            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
            SelectIcon(1);
            repaint();
        } else if(e.getSource() == three){
            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
            SelectIcon(2);
            repaint();
        }
    }
}
