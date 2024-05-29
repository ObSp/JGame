package MarshmallowFighter.Classes;

import java.awt.event.KeyEvent;
import java.util.ArrayList;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Image2D;

public abstract class Interactible {

    protected String path;
    protected final JGame game;

    public Runnable onInteract;

    public int InteractionKey = KeyEvent.VK_E;

    public boolean InteractionPromptVisible = false;
    
    public int InteractionDistanceX = 50;
    public int InteractionDistanceY = 50;


    public Image2D model;
    public Image2D prompt;

    @SuppressWarnings("unchecked")
    public Interactible(JGame game, String path){
        this.path = path;
        this.game = game;

        if (game.Globals.get("interactibles") == null)
            game.Globals.put("interactibles", new ArrayList<Interactible>());

        ((ArrayList<Interactible>) game.Globals.get("interactibles")).add(this);
    }

    @SuppressWarnings("unchecked")
    public void removeFromGlobals(){
        ((ArrayList<Interactible>) game.Globals.get("interactibles")).remove(this);
    }

    public void onInteract(){
        if (onInteract != null) onInteract.run();
    };

    public abstract void PlayerEnteredBounds();

    public abstract void PlayerExitedBounds();
}
