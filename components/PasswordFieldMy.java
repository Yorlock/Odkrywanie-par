package components;

import javax.swing.*;
import java.awt.*;

public class PasswordFieldMy extends JPasswordField {
    public PasswordFieldMy(int x, int y, int width, int height, Font font, String text){
        this.setBounds(x,y,width,height);
        this.setFont(font);
        this.setForeground(Color.cyan);
        this.setBackground(Color.black);
        this.setCaretColor(Color.white);
        this.setText(text);
    }
}
