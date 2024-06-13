package TopdownShooter.Classes;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Image2D;

public class Enemy {

    private JGame game;
    private Player plr;
    
    public Image2D model = new Image2D();

    public Enemy(JGame g, Player p){
        game = g;
        plr = p;
    }
}
