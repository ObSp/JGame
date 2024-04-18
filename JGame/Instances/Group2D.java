package JGame.Instances;

import java.awt.Graphics;

import JGame.Types.Vector2;
import lib.*;

public class Group2D extends Instance{

    private ArrayTable<Instance> Children;
    public Instance Primary;

    public Group2D(){
        Children = new ArrayTable<>();
        Size = new Vector2(0, 0);
    }

    public Group2D(Instance ...children){
        Children = new ArrayTable<>(children);
    }

    public void MoveTo(Vector2 newpos){
        if (Primary==null){
            System.out.println("ERR: Group2D.MoveTo() can only be called on a Group2D with a valid Primary instance.");
            return;
        }

        

        Vector2 primarypos = Primary.Position;
        Primary.Position = newpos;

        for (Instance inst : Children){
            if (inst.equals(Primary)) continue;
            Vector2 diff = new Vector2(inst.Position.X-primarypos.X, inst.Position.Y-primarypos.Y);
            Vector2 actual = new Vector2(Primary.Position.X+diff.X, Primary.Position.Y+diff.Y);
            inst.Position = actual;
        }
    }

    public Vector2 getGroupPosition(){
        if (Primary==null){
            System.out.println("ERR: Group2D.getGroupPosition() can only be called on a Group2D with a valid Primary instance.");
            return null;
        }
        return Primary.Position.clone();
    }

    public void AddChild(Instance newchild){
        Children.add(newchild);
    }

    public void RemoveChild(Instance child){
        Children.remove(child);
    }

    public Instance[] GetChildren(){
        final Instance[] arr = Children.toArray();
        return arr;
    }

    @Override
    public void paint(Graphics g) {
        return;
    }

    @Override
    public void setPosition(Vector2 velpos){
        if (Primary==null){
            System.out.println("ERR: physics handler tried to move a Group2D without it having a valid Primary instance.");
            return;
        }
        System.out.println(velpos);
        MoveTo(velpos);
    }
    
}
