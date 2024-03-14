package Tests;

import java.awt.Color;

import JGame.*;
import JGame.Instances.*;
import JGame.Types.*;
import lib.*;


public class Dev {
    public static void main(String[] args) {

        JGame game = new JGame(.005);//cannot be lower, otherwise and out of bounds error will throw???

        Promise.await(game.start());

        Box2D thing = new Box2D();
        thing.Position = new Vector2(100, 193);
        thing.Size = new Vector2(100, 100);
        thing.FillColor = Color.BLACK;
        thing.setParent(game);

        Box2D otha = new Box2D();
        otha.Position = new Vector2(50, 193);
        otha.Size = new Vector2(100, 100);
        otha.FillColor = Color.blue;
        otha.setParent(game);
        otha.Name = "Ot";
        
        Group2D test = new Group2D();
        test.Name = "Group";
        test.AddChild(thing);
        test.AddChild(otha);
        test.Primary = thing;
        test.setParent(game);

        game.onTick(dt -> {
            test.MoveTo(test.getGroupPosition().add(new Vector2(game.getInputHorizontal()*3, game.getInputVertical()*3*-1)));
        });

    }
}