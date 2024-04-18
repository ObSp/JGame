package lib;

import javax.swing.JFrame;

import JGame.Instances.Instance;

import java.awt.*;
import java.util.ArrayList;

public class OverridableJFrame extends JFrame {
    public ArrayList<Instance> contents = new ArrayList<>();

    public OverridableJFrame(String Title){
        super(Title);
    }
    
    @Override
    public void paint(Graphics g){
        paintComponents(g);
    }

    @Override
    public void paintComponents(Graphics g) {
        
        for (Instance c : contents){
            c.paint(g);
        }
        
        
    }
}
