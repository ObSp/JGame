package Studio;


import JGamePackage.JGame.*;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Services.InputService;
import JGamePackage.JGame.Types.*;
import Classes.*;

import java.awt.Color;
import java.io.File;


public class Main {
    static Display controlDisplay = new Display("Control");

    static JGame game = new JGame();

    static InputService input = game.Services.InputService;

    static Exporter export = new Exporter(game);
    public static void main(String[] args) {
        for (Instance v : game.Services.ParserService.ParseJSONToInstances(new File("JGameStudio\\Saves\\latestSave.json")))
            game.addInstance(v);
        
        controlDisplay.init(300,500);

        controlDisplay.Instantiate.Connect((typeofObject,A)->{
            Instance test = new Box2D();
            

            if (typeofObject.equals("Oval2D")){
                test = new Oval2D();
            } else if (typeofObject.equals("Image2D")){
                test = new Image2D();
            }

            test.Size = new Vector2(300,300);
            test.CFrame.Position = new Vector2(0, 0);
            test.FillColor = Color.red;
            game.addInstance(test);

            controlDisplay.setCurrentSelected(test);
        });

        controlDisplay.updateProperties.Connect((instance, props)->{
            instance.CFrame.Position = (Vector2) props.get("Position");
            instance.Size = (Vector2) props.get("Size");
            instance.FillColor = (Color) props.get("Color");
        });

        controlDisplay.RequestDestroy.Connect((inst, __)->{
            if (inst == null) return;
            inst.Destroy(); 
            controlDisplay.setCurrentSelected(null);
        });

        input.OnMouseClick.Connect(()->{
            controlDisplay.setCurrentSelected(input.GetMouseTarget());
        });

        controlDisplay.ExportRequest.Connect((d,k)->{
            export.ExportInstancesAsJGI();
        });

        game.OnTick.Connect(dt-> {
            if (controlDisplay.currentSelected == null || !input.IsMouseDown()) return;

            controlDisplay.updateShownProperties();
            controlDisplay.currentSelected.SetCenterPosition(input.GetMouseLocation());
        });
    }
}