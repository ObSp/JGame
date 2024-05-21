package MarshmallowFighter.Classes;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Box2D;
import JGamePackage.JGame.Instances.Image2D;

public class Model {

    public Box2D hitbox;

    public boolean hitBoxSidesSolid = false;
    
    protected final JGame game;
    public Image2D model;

    public Model(JGame game){
        this.game = game;
    }
}
