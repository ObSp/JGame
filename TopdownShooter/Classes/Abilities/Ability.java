package TopdownShooter.Classes.Abilities;

import java.util.ArrayList;

import JGamePackage.JGame.JGame;
import JGamePackage.lib.Signal.Connection;
import TopdownShooter.Classes.Enemy;
import TopdownShooter.Classes.Player;

public abstract class Ability {

    protected final JGame game;
    protected Player player;
    protected ArrayList<Enemy> enemies;

    @SuppressWarnings("rawtypes")
    protected Connection keyConnection;

    public int ActivationKeyCode;


    public Ability(JGame game){
        this.game = game;

        player = (Player) game.Globals.get("Player");

        game.Services.InputService.OnKeyPress.Connect(e ->{
            if (e.getKeyCode() != ActivationKeyCode) return;

            this.onActivate();
        });
    }

    public abstract void onActivate();

    public abstract void Enable();

    public void Disable(){
        keyConnection.Disconnect();
    }
}
