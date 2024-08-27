package TopdownShooter.Classes.Abilities;

import java.awt.event.KeyEvent;

import JGamePackage.JGame.JGame;

public abstract class QAbility extends Ability {

    public QAbility(JGame game) {
        super(game);
        
        ActivationKeyCode = KeyEvent.VK_Q;
    }

    
    
}
