package components;

import menu.ThreadSoundEffects;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

public class Slider extends JPanel implements ChangeListener {
    JLabel label;
    JSlider slider;
    String addToShowText;
    public Slider(int min, int max, int value,int x, int y, int width, int height, Font fontSlider, Font fontText, Color color, int minorTick, int majorTick, String addToShowText){
        slider = new JSlider(min, max, value);
        label = new JLabel();
        this.addToShowText = addToShowText;
        slider.setPreferredSize(new Dimension(width - 70, height));
        slider.setPaintTicks(true);
        slider.setMinorTickSpacing(minorTick);
        slider.setPaintTrack(true);
        slider.setMajorTickSpacing(majorTick);
        slider.setPaintLabels(true);
        slider.setForeground(Color.BLACK);
        slider.setFont(fontSlider);
        slider.setBackground(Color.LIGHT_GRAY);
        slider.addChangeListener(this);

        label.setForeground(color);
        label.setSize(30,25);
        label.setFont(fontText);
        label.setText(slider.getValue()+addToShowText);
        //label.setOpaque(true);

        this.setBounds(x, y, width, height);
        this.setBackground(Color.LIGHT_GRAY);
        this.setLayout(new BorderLayout());
        this.add(slider, BorderLayout.WEST);
        this.add(label, BorderLayout.EAST);
    }

    public int getValue(){
        return slider.getValue();
    }

    @Override
    public void stateChanged(ChangeEvent e)
    {
        label.setText(slider.getValue()+addToShowText);
    }
}
