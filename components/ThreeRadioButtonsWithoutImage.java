package components;

import menu.ThreadSoundEffects;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ThreeRadioButtonsWithoutImage extends JPanel implements ActionListener{
    ButtonGroup group;
    JRadioButton one, two, three;
    ThreadSoundEffects threadSoundEffects;
    public ThreeRadioButtonsWithoutImage(int x, int y, int width, int height,Font font, String texts[], ThreadSoundEffects threadSoundEffects, int whichOne){
        this.threadSoundEffects = threadSoundEffects;

        one = new JRadioButton(texts[0]);
        two = new JRadioButton(texts[1]);
        three = new JRadioButton(texts[2]);

        one.setFont(font);
        two.setFont(font);
        three.setFont(font);

        this.setLayout(null);
        this.setBounds(x,y,width,height);
        this.setBackground(Color.LIGHT_GRAY);

        one.setBackground(Color.LIGHT_GRAY);
        one.setBounds(0,0,100,25);
        two.setBackground(Color.LIGHT_GRAY);
        two.setBounds(300,0,100,25);
        three.setBackground(Color.LIGHT_GRAY);
        three.setBounds(600,0,100,25);
        if(whichOne == 0){
            one.setSelected(true);
        }else if(whichOne == 1){
            two.setSelected(true);
        }else{
            three.setSelected(true);
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

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == one){
            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
            repaint();
        } else if(e.getSource() == two){
            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
            repaint();
        } else if(e.getSource() == three){
            threadSoundEffects.run("effects\\mixkit-select-click-1109.wav", threadSoundEffects.getPlay());
            repaint();
        }
    }

}
