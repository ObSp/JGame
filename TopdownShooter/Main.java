package TopdownShooter;

import java.awt.Color;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Services.*;
import JGamePackage.JGame.Types.*;
import TopdownShooter.Classes.Bullet;

public class Main {
    static JGame game = new JGame(new StartArgs(true));
    static InputService input = game.Services.InputService;

    static Box2D player = new Box2D();

    //gun stuff
    static Box2D gun = new Box2D();
    static double gunRotationRadius = 45;
    static double BulletVelocity = 10;

    public static void main(String[] args) {
        game.BackgroundColor = new Color(168, 30, 44);

        player.FillColor = Color.black;
        player.AnchorPoint = new Vector2(50);
        player.CFrame.Position = game.getTotalScreenSize().divide(2, 2);
        player.Size = new Vector2(50);
        game.addInstance(player);

        gun.FillColor = Color.white;
        gun.AnchorPoint = new Vector2(50);
        gun.Size = new Vector2(10, 5);
        game.addInstance(gun);

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
        });

        input.OnMouseClick.Connect(()->{
            shoot();
        });
    }


    static void shoot(){
        Bullet bullet = new Bullet(game, gun.Size.divide(2, 2), gun.CFrame.clone());
        bullet.Fire(input.GetMouseLocation(), BulletVelocity);
    }

    static double lerp(double a, double b, double t){
        return (1-t)*a + t*b;
    }
}
