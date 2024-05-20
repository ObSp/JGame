package MarshmallowFighter.Classes;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Image2D;

public abstract class Interactible {

    protected String path;
    protected final JGame game;

    public Image2D model;

    public Interactible(JGame game, String path){
        this.path = path;
        this.game = game;
    }
}
