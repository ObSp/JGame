package JGame.Instances;

import java.awt.Graphics2D;

import JGame.Types.Vector2;
import lib.*;

public class Group2D extends Instance{

    private ArrayTable<Instance> Children;
    Instance Primary;

    public Group2D(){
        Children = new ArrayTable<>();
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
            Vector2 diff = new Vector2(inst.Position.X-primarypos.X, inst.Position.Y-primarypos.Y);
            inst.Position.add(diff);
        }
    }

    public void AddChild(Instance newchild){
        Children.add(newchild);
    }

    public void RemoveChild(Instance child){
        Children.remove(child);
    }

    public Instance[] GetChildren(){
        return Children.toArray();
    }

    @Override
    public void render(Graphics2D g) {
        for (Instance inst : Children){
            inst.render(g);
        }
    }
    
}
