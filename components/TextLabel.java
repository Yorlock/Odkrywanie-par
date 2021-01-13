package components;

import javax.swing.*;
import java.awt.*;

public class TextLabel extends JLabel {
    public TextLabel(String text, Font font, Color color, int x, int y, int width, int height, boolean border){
        this.setText(text);
        this.setFont(font);
        this.setForeground(color);
        this.setBounds(x,y,width,height);
        if(border) this.setBorder(BorderFactory.createBevelBorder(1));
    }
}
