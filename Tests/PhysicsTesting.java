package Tests;


import java.awt.Color;

import JGamePackage.JGame.*;
import JGamePackage.lib.*;
import JGamePackage.JGame.Instances.*;

public class PhysicsTesting {
    static JGame game = new JGame();


    public static void main(String[] args) {

        Promise.await(game.start());

        Box2D thing = new Box2D();
        thing.FillColor = Color.red; 
        thing.Anchored = false;
        thing.Solid = true;
        game.addInstance(thing);

        Box2D other = new Box2D();
        other.FillColor = Color.black;
        other.Size.X = 1000;
        other.Position.Y = 1000;
        other.Solid = true;
        game.addInstance(other);
    }
}
