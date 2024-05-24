package MarshmallowFighter.MapCreationTool;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Box2D;
import JGamePackage.JGame.Instances.Instance;
import JGamePackage.JGame.Types.Vector2;
import MarshmallowFighter.Classes.MapLoader;

public class Creator {

    static JGame game = new JGame();

    static Vector2 firstClick = null;
    static Vector2 secondClick = null;

    static ArrayList<Instance> insts;

    static Box2D ghost = new Box2D();

    static ArrayList<Instance> history = new ArrayList<>();

    public static void main(String[] args) {
        ghost.FillColor = Color.red;

        MapLoader.LoadMap(game, "MarshmallowFighter\\Media\\DATA.json");
        ghost.SetTransparency(.5);

        init();
    }

    static void init(){
        insts = game.Services.ParserService.ParseJSONToInstances(new File("MarshmallowFighter\\Media\\HITBOXES.json"));

        for (Instance i : insts){
            game.addInstance(i);
        }

        game.Services.InputService.OnMouseClick.Connect(()->{
            if (firstClick != null && secondClick != null){
                firstClick = null;
                secondClick = null;
            }

            if (firstClick == null){
                firstClick = game.Services.InputService.GetMouseLocation();
                return;
            }

            secondClick = game.Services.InputService.GetMouseLocation();
            Box2D b = new Box2D();
            setCorrectPositionAndSize(b, firstClick, secondClick);
            b.FillColor = Color.red;
            game.addInstance(b);
            history.add(b);
            insts.add(b);
        });
        game.Services.InputService.OnKeyPress.Connect(e->{
            if (e.getKeyCode() == KeyEvent.VK_U){
                if (history.size() == 0) return;
                Instance last = history.getLast();
                game.removeInstance(last);
                insts.remove(last);
                history.removeLast();
            }

            if (e.getKeyCode() == KeyEvent.VK_T){
                Instance cur = game.Services.InputService.GetMouseTarget();
                if (cur == null) return;
                cur.SetTransparency(cur.GetTransparency() != 0.0 ? 0.0 : 1);
            }

            if (e.getKeyCode() == KeyEvent.VK_BACK_SPACE){
                Instance cur = game.Services.InputService.GetMouseTarget();
                if (cur == null) return;
                game.removeInstance(cur);
                insts.remove(cur);
                history.remove(cur);
            }

            if (e.getKeyCode() == KeyEvent.VK_N){
                Instance cur = game.Services.InputService.GetMouseTarget();
                if (cur == null) return;

                Scanner scan = new Scanner(System.in);
                String name = scan.nextLine();
                cur.Name = name;
                scan.close();
            }
        });

        game.OnTick.Connect(dt->{
            if (firstClick != null && secondClick == null){
                if (!game.instances.contains(ghost))
                    game.addInstance(ghost);

                setCorrectPositionAndSize(ghost, firstClick, game.Services.InputService.GetMouseLocation());
            } else if (game.instances.contains(ghost)){
                game.removeInstance(ghost);
            }

            game.Camera.Position.X += game.Services.InputService.GetInputHorizontal()*5;
            game.Camera.Position.Y -= game.Services.InputService.GetInputVertical()*5;
        });

        game.getWindow().addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {}

            @Override
            public void windowClosing(WindowEvent e) {
                game.Services.ParserService.ExportInstancesToJSON(listToArr(insts), new File("MarshmallowFighter\\Media\\HITBOXES.json"));
            }

            @Override
            public void windowClosed(WindowEvent e) {}

            @Override
            public void windowIconified(WindowEvent e) {}

            @Override
            public void windowDeiconified(WindowEvent e) {}

            @Override
            public void windowActivated(WindowEvent e) {}

            @Override
            public void windowDeactivated(WindowEvent e) {}
            
        });
    }

    static void setCorrectPositionAndSize(Box2D b, Vector2 first, Vector2 second){
        if (second.X < first.X){
            b.CFrame.Position.X = second.X;
        } else {
            b.CFrame.Position.X = first.X;
        }

        if (second.Y < first.Y){
            b.CFrame.Position.Y = second.Y;
        } else {
            b.CFrame.Position.Y = first.Y;
        }
        b.Size.X = Math.abs(second.X-first.X);
        b.Size.Y = Math.abs(second.Y-first.Y);
    }

    static Instance[] listToArr(ArrayList<Instance> list){
        Instance[] arr = new Instance[list.size()];
        for (int i = 0; i < arr.length; i ++){
            arr[i] = list.get(i);
        }
        return arr;
    }
}