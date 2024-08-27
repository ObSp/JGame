package TopdownShooter.Classes;

import java.awt.Color;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Box2D;
import JGamePackage.JGame.Instances.Image2D;
import JGamePackage.JGame.Services.InputService;
import JGamePackage.JGame.Types.Vector2;
import TopdownShooter.Classes.Abilities.EAbility;
import TopdownShooter.Classes.Abilities.QAbility;
import TopdownShooter.Classes.Abilities.StarterEAbility;
import TopdownShooter.Classes.Abilities.StarterQAbility;
import TopdownShooter.Classes.Guns.*;

public class Player {
    @SuppressWarnings("unused")
    private JGame game;

    //services
    private InputService input;

    //model
    public Image2D model = new Image2D();
    private static Vector2 modelSize = new Vector2(50);

    //hurt
    private Box2D hurtCover = new Box2D();

    //gun
    public Gun gun;

    //Humanoid
    public Humanoid Humanoid = new Humanoid();

    //Abilities
    public EAbility Basic;
    public QAbility Ultimate;

    public Player(JGame game){
        input = game.Services.InputService;

        this.game = game;
        game.Globals.put("Player", this);

        gun = new BasicGun(game, this);

        model.AnchorPoint = new Vector2(50);
        model.CFrame.Position = game.getTotalScreenSize().divide(2, 2);
        model.Size = modelSize.clone();
        model.ImagePath = "TopdownShooter\\Media\\Player.png";
        model.Associate = this;
        game.addInstance(model);

        hurtCover.Size = game.getTotalScreenSize();
        hurtCover.FillColor = Color.red;
        hurtCover.SetOpacity(.3);
        hurtCover.MoveWithCamera = false;

        Basic = new StarterEAbility(game);
        Ultimate = new StarterQAbility(game);
    }

    public void Shoot(){
        this.gun.Shoot();
    }

    public void OffsetByInput(double dt){
        double inputHorizontal = input.GetInputHorizontal()*(dt*600);
        model.CFrame.Position.X += inputHorizontal;
        model.CFrame.Position.Y -= input.GetInputVertical()*(dt*600);

        if (inputHorizontal > 0 && model.FlipHorizontally){
            model.FlipHorizontally = false;
        } else if (inputHorizontal < 0 && !model.FlipHorizontally){
            model.FlipHorizontally = true;
        }
    }

    public void UpdateGunLocation(){
        this.gun.UpdateAndLookAtMouse();
    }
}
