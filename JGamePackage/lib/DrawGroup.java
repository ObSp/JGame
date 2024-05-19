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
        /**int size = instances.length;
        for (int i = 0; i < size; i++) {

            int mindex = i; //the index of the smallest number in the list, starting at the current index in order to ignore any already sorted list items
            for (int j = i; j<size; j++){ //loop through the list, starting at index i to ingore any already sorted items
                if (instances[j].ZIndex<instances[i].ZIndex) mindex = j; //if the item at index j in array unorderedList is smaller than the item at the current mindex, set a new mindex
            }
            
            Instance itemAtIndex = instances[i]; //temporary variable to store the item at the index that will be changed
            instances[i] = instances[mindex]; //set the list index of i to the lowest number in the list after this index
            instances[mindex] = itemAtIndex; //set the old index of the lowest number to the old number at index i, effectively swapping them
        }*/

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