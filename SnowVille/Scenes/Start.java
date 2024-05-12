package Scenes;

import java.awt.Color;

import JGamePackage.JGame.*;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Types.*;

public class Start extends Scene {

    Box2D ground = new Box2D();

    public void init(JGame game){
        this.parent = game;

        ground.FillColor = new Color(0,0,0,0);
        ground.Size.X = game.getScreenWidth();
        ground.Size.Y = 50;
        ground.Position.Y = game.getScreenHeight()-ground.Size.Y;
        ground.Solid = true;
        instances.add(ground);
    }
}
