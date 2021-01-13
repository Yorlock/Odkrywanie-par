package components;

import menu.Frame_1;
import menu.ThreadMusic;
import menu.ThreadSoundEffects;

import javax.swing.*;
import java.awt.*;

public class Button extends JButton {
    public Button(String name, int x, int y, int width, int height, Font font){
        this.setText(name);
        this.setBounds(x, y, width, height);
        this.setFont(font);
        this.setForeground(Color.gray);
        this.setBorderPainted(false);
    }
}
