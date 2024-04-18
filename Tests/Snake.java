package Tests;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;

import JGame.*;
import JGame.Instances.*;
import JGame.Types.*;
import lib.*;

public class Snake {
    static JGame game = new JGame();
    static int dt_mult = 1000;
    static int speed = 1;
    static int boxSize = 30;
    static int fieldSize = 25;

    static double lastMove = 0.0;
    static double moveCooldown = .15;

    static Color playerColor = Color.blue;

    static Vector2 moveDirection = new Vector2(1, 0);

    static Box2D[][] Tiles = new Box2D[fieldSize][fieldSize];

    static Box2D[][] TailPositions = new Box2D[fieldSize][fieldSize];

    static ArrayList<RemovalScheduleItem> removalSchedule = new ArrayList<>();
    public static void main(String[] args) {
        Promise.await(game.start());
        


        //Field
        for (int x = 0; x < fieldSize; x++){
            for (int y = 0; y<fieldSize; y++){
                Box2D thisBox = new Box2D();
                if ((x%2==0 && y%2==0)||(x%2!=0 && y%2!=0)){
                    thisBox.FillColor = new Color( 176, 249, 122 );
                } else {
                    thisBox.FillColor = new Color(149, 222, 95);
                }
                thisBox.Position = new Vector2(x*boxSize, y*boxSize);
                thisBox.Size = new Vector2(boxSize, boxSize);
                Tiles[x][y] = thisBox;
                game.addInstance(thisBox);
            }
        }
        

        Box2D player = new Box2D();
        player.Position = Tiles[0][0].Position.clone();
        player.Size = new Vector2(boxSize, boxSize);
        player.FillColor = new Color( 25, 111, 61 );
        game.addInstance(player);

        //movement and basic trail stuff
        game.onTick(dt->{
            double curMillis = ((double) System.currentTimeMillis())/1000.0;

            
            //movement
            if (curMillis-lastMove < moveCooldown) return;

            Vector2 lastPos = player.Position.clone();

            try {
                player.Position = Tiles[(player.Position.X/boxSize)+moveDirection.X][player.Position.Y/boxSize + moveDirection.Y*-1].Position.clone();
            } catch (Exception e) {
                System.exit(0);
            }

            if (TailPositions[player.Position.X/boxSize][player.Position.Y/boxSize]!=null) System.exit(0);

            lastMove = curMillis;

            if (!lastPos.equals(player.Position)){
                Box2D trailBox = new Box2D();
                trailBox.Position = lastPos;
                trailBox.Size = new Vector2(boxSize,boxSize);
                trailBox.FillColor = new Color(82, 190, 128);
                trailBox.Name = ""+System.currentTimeMillis()+"";
                TailPositions[lastPos.X/boxSize][lastPos.Y/boxSize] = trailBox;
                game.addInstance(trailBox);


                removalSchedule.add(new RemovalScheduleItem(trailBox, curMillis, 2));
            }
            
        });
        
        //removal schedule
        game.onTick(dt->{
            double curTimeSeconds = ((double) System.currentTimeMillis())/1000;
            for (RemovalScheduleItem v : removalSchedule){
                double elapsedTimeSeconds = curTimeSeconds-v.addedTimeSeconds;

                if (elapsedTimeSeconds>=v.timeoutBeforeRemovalSeconds) game.removeInstance(v.selfInst);
            }

            for (int x = 0; x < TailPositions.length; x++){
                for (int y = 0; y < TailPositions[x].length; y++){
                    if (TailPositions[x][y]==null || TailPositions[x][y].Parent==null) TailPositions[x][y] = null;

                }
            }
        });
    
        //movement direction changing
        game.onKeyPress(e->{
            if (e.getKeyCode()==KeyEvent.VK_W && moveDirection.Y==0){
                moveDirection.X = 0;
                moveDirection.Y = 1;
            } else if(e.getKeyCode()==KeyEvent.VK_S && moveDirection.Y == 0){
                moveDirection.X = 0;
                moveDirection.Y = -1;
            } else if(e.getKeyCode()==KeyEvent.VK_A && moveDirection.X == 0){
                moveDirection.X = -1;
                moveDirection.Y = 0;
            } else if(e.getKeyCode()==KeyEvent.VK_D && moveDirection.X == 0){
                moveDirection.X = 1;
                moveDirection.Y = 0;
            }
        });


        new Thread(()->{
            while (task.wait(2)){
                
            }
        }).start();
    
    }

}


class RemovalScheduleItem{
    public Instance selfInst;
    public double timeoutBeforeRemovalSeconds;
    public double addedTimeSeconds;

    public RemovalScheduleItem(Instance inst, double addedTimeS, double timeoutBeforeRemovalSeconds){
        selfInst = inst;
        addedTimeSeconds = addedTimeS;
        this.timeoutBeforeRemovalSeconds = timeoutBeforeRemovalSeconds;
    }

    @Override
    public boolean equals(Object o){
        if (!(o instanceof RemovalScheduleItem)) return false;

        RemovalScheduleItem other = (RemovalScheduleItem) o;

        return other.selfInst.equals(selfInst) && timeoutBeforeRemovalSeconds==other.timeoutBeforeRemovalSeconds;
    }

}