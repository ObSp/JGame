package JGamePackage.lib;

import java.awt.*;

import javax.swing.JPanel;

import JGamePackage.JGame.Instances.Instance;

public class DrawGroup extends JPanel {
    public Instance[] instances;

    public DrawGroup(){
        instances = new Instance[0];
    }

    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);

        int size = instances.length;
        for (int i = 0; i < size-1; i++) {

            int mindex = i; 
            for (int j = i+1; j<size; j++){
                if (instances[j]==null) continue;
                if (instances[j].ZIndex<instances[mindex].ZIndex) mindex = j; 
            }
            
            Instance itemAtIndex = instances[i]; 
            instances[i] = instances[mindex]; 
            instances[mindex] = itemAtIndex;
        }

        ((Graphics2D) g).setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                    RenderingHints.VALUE_ANTIALIAS_ON);

        for (Instance x : instances){
            if (x.Parent == null || x.GetTransparency() == 0.0) continue;
            x.paint(g);
        }
    }
}