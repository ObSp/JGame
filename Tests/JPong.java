package Tests;

import java.awt.Color;
import java.awt.event.KeyEvent;

import JGame.*;
import JGame.Instances.*;
import JGame.Types.*;
import lib.Task;
import lib.TimeUtil;

enum Player{
    Player1,
    Player2
}

public class JPong {
    static int PaddleSpeed = 1;
    static int DeltaMulti = 1000;
    
    static int numHits = 0;

    static Player lastHit;
    static int Player1Score = 0;
    static int Player2Score = 0;

    static Vector2 BallSpeed = randomVector2();
    public static void main(String[] args) {
        JGame game = new JGame(.0001);

        game.WindowTitle = "JPong | Score: 0-0";
        game.start();
        
        TimeUtil.sleep(1);

        Box2D bg = new Box2D();
        bg.Position = new Vector2(0, 0);
        bg.Size = new Vector2(2000, 1000);
        bg.FillColor = Color.white;
        bg.setParent(game);

        Box2D LeftPaddle = new Box2D();
        LeftPaddle.Size = new Vector2(20, 300);
        LeftPaddle.Position = new Vector2(10, (game.getScreenHeight()/2)-LeftPaddle.Size.Y/2);
        LeftPaddle.FillColor = Color.blue;
        LeftPaddle.setParent(game);

        Box2D RightPaddle = new Box2D();
        RightPaddle.Size = new Vector2(20, 300);
        RightPaddle.Position = new Vector2(game.getScreenWidth()-30, (game.getScreenHeight()/2)-RightPaddle.Size.Y/2);
        RightPaddle.FillColor = Color.red;
        RightPaddle.setParent(game);

        Box2D Ball = new Box2D();
        Ball.Size = new Vector2(60, 60);
        Ball.Position = new Vector2(game.getScreenWidth()/2, (game.getScreenHeight()/2)-Ball.Size.Y/2);
        Ball.FillColor = Color.gray;
        Ball.setParent(game);
        Task.Delay(2, ()->{
            Ball.FillColor = Color.black;
        });

        //basic movement stuff
        game.onTick(dt->{



            //--LEFT PADDLE MOVEMENT--//
            if (game.isKeyDown(KeyEvent.VK_W) && !LeftPaddle.touchingBorderTop() ){
                LeftPaddle.Position.Y -= PaddleSpeed*(dt*DeltaMulti);
            }else if (game.isKeyDown(KeyEvent.VK_S) && !LeftPaddle.touchingBorderBottom() ){
                LeftPaddle.Position.Y += PaddleSpeed*(dt*DeltaMulti);
            }

            //--RIGHT PADDLE MOVEMENT--//
            if (game.isKeyDown(KeyEvent.VK_UP) && !RightPaddle.touchingBorderTop() ){
                RightPaddle.Position.Y -= PaddleSpeed*(dt*DeltaMulti);
            }else if (game.isKeyDown(KeyEvent.VK_DOWN) && !RightPaddle.touchingBorderBottom() ){
                RightPaddle.Position.Y += PaddleSpeed*(dt*DeltaMulti);
            }

            double tc = (double) game.TickCount;

            if (Ball.FillColor.equals(Color.gray) || tc%5!=0)return;


            Ball.Position.X += BallSpeed.X*(dt*DeltaMulti);
            Ball.Position.Y += BallSpeed.Y*(dt*DeltaMulti);


            //--CHECKING IF THE BALL HIT ANYONE
            if (Ball.touchingBorderLeft() || Ball.touchingBorderRight()){
                //Score stuff
                if (lastHit!= null && lastHit.equals(Player.Player1)){
                    Player1Score++;
                }

                if (lastHit!= null && lastHit.equals(Player.Player2)){
                    Player2Score++;
                }



                game.setWindowTitle( "JPong | Score: "+Player1Score+" - "+Player2Score);
                BallSpeed = randomVector2();
                Ball.Position = new Vector2(game.getScreenWidth()/2, (game.getScreenHeight()/2)-Ball.Size.Y/2);

                RightPaddle.Position = new Vector2(game.getScreenWidth()-30, (game.getScreenHeight()/2)-RightPaddle.Size.Y/2);
                LeftPaddle.Position = new Vector2(10, (game.getScreenHeight()/2)-LeftPaddle.Size.Y/2);
                Ball.FillColor = Color.gray;

                Task.Delay(2, ()->{
                    Ball.FillColor = Color.black;
                });
                return;
            }


            if (Ball.touchingBorderBottom() && BallSpeed.Y>0) BallSpeed.Y = -BallSpeed.Y;

            if (Ball.touchingBorderTop() && BallSpeed.Y<0) BallSpeed.Y = -BallSpeed.Y;

            if (Ball.overlaps(LeftPaddle) && BallSpeed.X <0){
                Ball.FillColor = LeftPaddle.FillColor;
                numHits++;
                lastHit = Player.Player1;

                if (Math.random()>.5){
                    BallSpeed.Y = -BallSpeed.Y;
                }

                BallSpeed.X = -BallSpeed.X; 
                if (Math.random()>.8 || ((numHits<3 || Math.abs(BallSpeed.X)<3) && Math.random()>.4))
                    BallSpeed.X += 1;
            
            }

            if (Ball.overlaps(RightPaddle) && BallSpeed.X >0) {
                Ball.FillColor = RightPaddle.FillColor;
                numHits++;
                lastHit = Player.Player2;

                BallSpeed.X = -BallSpeed.X; 
                
                if (Math.random()>.5){
                    BallSpeed.Y = -BallSpeed.Y;
                }

                if (Math.random()>.8 || (numHits<3 && Math.random()>.5))
                    BallSpeed.X -= 1;


            }
        });

        game.onTick(dt->{
            if (game.TickCount%3000 != 0 || Math.random()<.9) return;
           // Box2D powerup = new Box2D();
        });
    }

    static Vector2 randomVector2(){
        int x = 1;
        int y = 1;
        
        if (Math.random()>.5) x = -x;

        if (Math.random()>.5) y = -y;

        return new Vector2(x, y);
    }
}
