package TopdownShooter.Classes;

import java.util.ArrayList;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Instance;

public class InstancePool {

    private ArrayList<Instance> pool = new ArrayList<>();
    private ArrayList<Instance> inUse = new ArrayList<>();

    private boolean canExpand = true;
    private int intialSize = 50;
    private int expansionSize = 5;

    private JGame game;
    
    private Instance template;

    //--PRIVATE METHODS--//
    private void populate(int numNew){
        for (int i = 0; i < numNew; i++){
            Instance clone = this.template.clone();
            this.pool.add(clone);
        }
    }

    //--PUBLIC METHODS--//
    public InstancePool(Instance template, int initialSize, boolean canExpand, int expansionSize, JGame game){
        this.game = game;

        this.template = template;

        this.intialSize = initialSize;
        this.canExpand = canExpand;
        this.expansionSize = expansionSize;

        this.populate(this.intialSize);
    }

    public Instance GetInstanceFromPool(){
        int poolSize = 0;

        if (poolSize == 0){
            this.populate(this.expansionSize);
            poolSize += this.expansionSize;
        }
        
        Instance picked = this.pool.get(this.pool.size()-1);
        this.pool.remove(this.pool.size()-1);
        this.inUse.add(picked);
        this.game.addInstance(picked);

        return picked;
    }

    public void ReturnInstanceToPool(Instance instance){
        int indexOfInstance = this.inUse.indexOf(instance);

        if (indexOfInstance == -1){
            throw new Error("Instance Pool Error: Unable to return instance" + instance.toString() + " because it currently isn't in use. Please double check that you tried to return the correct object.");
        }
        
        this.game.removeInstance(instance);
        this.inUse.remove(indexOfInstance);
        this.pool.add(instance);
    }
}
