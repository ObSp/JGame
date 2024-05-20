package JGamePackage.lib;

import java.awt.*;

import javax.swing.JComponent;

import JGamePackage.JGame.Instances.Instance;

public class DrawGroup extends JComponent {
    public Instance[] instances;

    public DrawGroup(){
        instances = new Instance[0];
    }

    @Override
    public void paint(Graphics g){
        int size = instances.length;
        for (int i = 0; i < size-1; i++) {

            int mindex = i; 
            for (int j = i+1; j<size; j++)
                if (instances[j].ZIndex<instances[mindex].ZIndex) mindex = j; 
            
            Instance itemAtIndex = instances[i]; 
            instances[i] = instances[mindex]; 
            instances[mindex] = itemAtIndex;
        }

        for (Instance x : instances){
            if (x.Parent == null) continue;
            x.paint(g);
        }

        /* for (int i = 0; i < instances.size(); i++){
            JComponent j = instances.get(i);
            if (j == null)
                continue;
            j.paint(g);
        }
         * 
         */
    }
}