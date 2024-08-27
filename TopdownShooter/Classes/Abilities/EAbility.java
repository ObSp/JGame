package TopdownShooter.Classes.Abilities;

import java.awt.event.KeyEvent;

import JGamePackage.JGame.JGame;

public abstract class EAbility extends Ability {

    public EAbility(JGame game) {
        super(game);

        ActivationKeyCode = KeyEvent.VK_E;
    }
    
}
