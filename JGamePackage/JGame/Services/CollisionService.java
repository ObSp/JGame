package JGamePackage.JGame.Services;

import java.util.ArrayList;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Box2D;
import JGamePackage.JGame.Instances.Instance;
import JGamePackage.JGame.Types.CollisionOptions;
import JGamePackage.JGame.Types.Vector2;

public class CollisionService extends Service {
    public CollisionService(JGame parent){
        super(parent);
    }

    private boolean blacklistContains(Instance[] bl, Instance toFind){
        for (Instance x : bl){
            if (x==toFind)
                return true;
        }
        return false;
    }

    private Instance[] listToArray(ArrayList<Instance> list){
        Instance[] insts = new Instance[list.size()];
        for (int i = 0; i < insts.length; i++)
            insts[i] = list.get(i);
        return insts;
    }

    public Instance[] GetInstancesInBox(Vector2 position, Vector2 boxSize, CollisionOptions options){
        ArrayList<Instance> colliding = new ArrayList<>();

        Box2D box = new Box2D();
        box.Size = boxSize.clone();
        box.CFrame.Position = position.clone();

        for (Instance inst : Parent.instances){
            if (inst.overlaps(box) && (!options.SolidsOnly || inst.Solid) && !blacklistContains(options.Blacklist, inst)){
                colliding.add(inst);
            }
        }



        return listToArray(colliding);
    }

    /**Checks the collision inside of bounds boxSize and position, returning true if there are other Instances
     * in those bounds and false if there aren't
     * 
     * @param position
     * @param boxSize
     * @param options
     * @return
     */
    public Instance CheckCollisionInBox(Vector2 position, Vector2 boxSize, CollisionOptions options){
        Box2D box = new Box2D();
        box.Size = boxSize.clone();
        box.CFrame.Position = position.clone();
        //box.FillColor = Color.black;
        //Parent.addInstance(box);

        for (Instance inst : Parent.instances){
            if (inst.overlaps(box) && (!options.SolidsOnly || inst.Solid) && !blacklistContains(options.Blacklist, inst)){
                return inst;
            }
        }



        return null;
    }
}
