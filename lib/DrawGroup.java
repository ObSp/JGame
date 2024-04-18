package lib;

import java.util.ArrayList;
import java.awt.*;

import javax.swing.JComponent;

import JGame.Instances.Instance;

public class DrawGroup extends JComponent {
    public ArrayList<Instance> instances;

    public DrawGroup(){
        instances = new ArrayList<>();
    }

    @Override
    public void paint(Graphics g){
        try {
            for (Instance inst : instances){
                inst.paint(g);
            }
        } catch (Exception e) {

        }
    }
}
