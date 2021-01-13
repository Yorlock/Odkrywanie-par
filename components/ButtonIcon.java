package components;

import javax.swing.*;
import java.awt.*;

public class ButtonIcon extends JButton {
    String path;
    ImageIcon helper;
    public ButtonIcon(String path, Color backgroundcolor, int x, int y, int width, int height){
        this.path = path;
        this.setBounds(x,y,width,height);
        this.setOpaque(false);
        this.setBackground(backgroundcolor);
        this.setBorderPainted(false);

        helper = new ImageIcon(path);
        Image img = helper.getImage();
        Image imgScale = img.getScaledInstance(this.getWidth(), this.getHeight(),Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        this.setIcon(scaledIcon);
    }
    public String getPath(){
        return this.path;
    }
    public void changeIcon(String new_path){
        path = new_path;
        helper = new ImageIcon(path);
        Image img = helper.getImage();
        Image imgScale = img.getScaledInstance(this.getWidth(), this.getHeight(),Image.SCALE_SMOOTH);
        ImageIcon scaledIcon = new ImageIcon(imgScale);
        this.setIcon(scaledIcon);
    }
}
