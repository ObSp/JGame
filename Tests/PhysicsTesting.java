package Tests;


import java.awt.Color;

import JGamePackage.JGame.*;
import JGamePackage.lib.*;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Types.*;

public class PhysicsTesting {
    static JGame game = new JGame();


    public static void main(String[] args) {

        Promise.await(game.start());

        Box2D thing = new Box2D();
        thing.FillColor = Color.red; 
        game.addInstance(thing);
    }
}
