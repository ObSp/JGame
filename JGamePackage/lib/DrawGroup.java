package JGamePackage.lib;

import java.util.ArrayList;
import java.awt.*;

import javax.swing.JComponent;

import JGamePackage.JGame.Instances.Instance;

public class DrawGroup extends JComponent {
    public ArrayList<Instance> instances;

    public DrawGroup(){
        instances = new ArrayList<>();
    }

    @Override
    public void paintComponent(Graphics g){
        System.out.println("drawin");
    }

    @Override
    public void paint(Graphics g){
        //try {
            for (int i = 0; i < instances.size(); i++){
                ((JComponent) instances.get(i)).paint(g);
            }
        //} catch (Exception e) {
            
        //}
    }
}