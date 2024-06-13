package TopdownShooter;

import java.awt.Color;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Services.*;
import JGamePackage.JGame.Types.*;
import JGamePackage.lib.task;
import TopdownShooter.Classes.Bullet;

public class Main {
    static JGame game = new JGame(new StartArgs(true));
    static InputService input = game.Services.InputService;

    static Image2D player = new Image2D();

    //layers
    static int BackgroundLayer = -1;
    static int PlayerLayer = 0;
    static int UILayer = 1;

    //gun stuff
    static Box2D gun = new Box2D();
    static double gunRotationRadius = 45;
    static double BulletVelocity = 10;


    //healthbar
    static Box2D healthbarBackground = new Box2D();
    static Box2D healthbar;

    static double MaxHealth = 200;
    static double Health = MaxHealth;


    //enemy stuff
    static double enemySpawnSeconds = 4;

    public static void main(String[] args) {
        game.BackgroundColor = new Color(50, 58, 79);

        player.FillColor = Color.black;
        player.AnchorPoint = new Vector2(50);
        player.CFrame.Position = game.getTotalScreenSize().divide(2, 2);
        player.Size = new Vector2(50);
        player.ImagePath = "TopdownShooter\\Media\\Player.png";
        game.addInstance(player);

        gun.FillColor = Color.white;
        gun.AnchorPoint = new Vector2(50);
        gun.Size = new Vector2(10, 5);
        game.addInstance(gun);


        healthbarBackground.CFrame.Position = new Vector2(20);
        healthbarBackground.Size = new Vector2(200, 30);
        healthbarBackground.MoveWithCamera = false;
        healthbarBackground.FillColor = new Color(22, 22, 23);
        healthbarBackground.ZIndex = UILayer;
        game.addInstance(healthbarBackground);

        healthbar = healthbarBackground.clone();
        healthbar.FillColor = new Color(237, 28, 70);
        healthbar.ZIndex = healthbarBackground.ZIndex + 1;
        game.addInstance(healthbar);
        

        gun.CFrame.Position = player.CFrame.Position.add(0, -50);

        game.OnTick.Connect(dt->{
            Vector2 mouseLoc = input.GetMouseLocation();
            Vector2 plrPos = player.CFrame.Position;
            gun.CFrame.LookAt(mouseLoc); //rotate gun towards mouse

            plrPos.X += input.GetInputHorizontal()*(dt*600);
            plrPos.Y -= input.GetInputVertical()*(dt*600);


            double lookAtAngle_rad = CFrame.LookAt(plrPos, mouseLoc).Rotation;
            
            int gunX = (int) (plrPos.X + gunRotationRadius*Math.cos(lookAtAngle_rad));
            int gunY = (int) (plrPos.Y + gunRotationRadius*Math.sin(lookAtAngle_rad));

            if (mouseLoc.X <= plrPos.X){
                gunX = plrPos.X - (gunX - plrPos.X);
                gunY = plrPos.Y - (gunY - plrPos.Y);
            }

            
            gun.CFrame.Position.X = gunX;
            gun.CFrame.Position.Y = gunY;

            healthbar.Size.X = (int) lerp(healthbar.Size.X, (Health/MaxHealth)*healthbarBackground.Size.X, .1);
        });

        input.OnMouseClick.Connect(()->{
            shoot();
        });

        while (true) {
            task.wait(enemySpawnSeconds);
        }
    }


    static void shoot(){
        Bullet bullet = new Bullet(game, gun.Size.divide(2, 2), gun.CFrame.clone());
        bullet.Fire(input.GetMouseLocation(), BulletVelocity);
    }

    static double lerp(double a, double b, double t){
        return (1-t)*a + t*b;
    }
}
