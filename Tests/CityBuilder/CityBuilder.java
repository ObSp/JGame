package Tests.CityBuilder;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.*;
import JGamePackage.JGame.Services.InputService;
import JGamePackage.JGame.Types.Vector2;

import java.awt.Color;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CityBuilder {
    static JGame game = new JGame();
    static int boxSize = 37;
    static int boardWidth = 31;
    static int boardHeight = 26;
    static int padding = 1;

    static Color[] colors = {
        ColorContainer.gray, ColorContainer.pink, ColorContainer.red, 
        ColorContainer.purple, ColorContainer.yellow, ColorContainer.orange,
        ColorContainer.brown, ColorContainer.green, ColorContainer.blue,
        ColorContainer.black
    };

    static InputService input = game.Services.InputService;

    static Color selectedColor = colors[1];

    static String curSavePath = "Tests\\CityBuilder\\Saves\\save1.txt";

    static StoredBox[][] board = new StoredBox[boardWidth][boardHeight];

    static void generateBox(int x, int y){
        StoredBox box = new StoredBox();
        box.Size = new Vector2(boxSize);
        box.CFrame.Position =  new Vector2(x*(boxSize+1), y*(boxSize+1));
        box.FillColor = colors[0];
        detectClick(box);

        board[x][y] = box;
        game.addInstance(box);
    }

    public static void main(String[] args) {
        game.setWindowTitle("City Builder");

        game.getWindow().addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent e) {
            }

            @Override
            public void windowClosing(WindowEvent e) {
                try {
                    writeToFile();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }

            @Override
            public void windowClosed(WindowEvent e) {
            }

            @Override
            public void windowIconified(WindowEvent e) {
            }

            @Override
            public void windowDeiconified(WindowEvent e) {
            }

            @Override
            public void windowActivated(WindowEvent e) {
            }

            @Override
            public void windowDeactivated(WindowEvent e) {
            }
            
        });

        game.OnTick.Connect(dt->{
            if (!input.IsMouseDown()) return;
            Instance target = input.GetMouseTarget();
            if (target == null || target.FillColor.equals(selectedColor) || !(target instanceof StoredBox)) return;

            target.FillColor = selectedColor;
        });

        //board generation

        for (int x = 0; x < boardWidth; x++){

            for (int y = 0; y < boardHeight; y++){
                generateBox(x, y);
            }

        }

        getFromFile();

        int boxX = 1300;
        int boxSize = 70;
        int boxPadding = 10;
        int finalY = 0;

        //color selection generation
        for (int y = 0; y <colors.length; y++){
            Box2D b = new Box2D();
            b.Size = new Vector2(boxSize);
            b.FillColor = colors[y];
            b.CFrame.Position = new Vector2(boxX, y*(boxSize+boxPadding));

            finalY++;

            detectColorChangeClick(b);

            game.addInstance(b);
        }
        finalY++;

        //clear button
        Image2D clear = new Image2D();
        clear.ImagePath = "C:\\Users\\Paul W\\Documents\\GitHub\\JGame\\Tests\\CityBuilder\\Assets\\Clear.png";
        clear.Size = new Vector2(boxSize);
        clear.CFrame.Position = new Vector2(boxX, finalY*(boxSize+boxPadding));
        game.addInstance(clear);

        clear.MouseButton1Down.Connect((x,y)->{
            for (Box2D[] row : board){
                for (Box2D b : row){
                    b.FillColor = colors[0];
                }
            }
        });
    }

    static void detectClick(StoredBox b){
        b.MouseButton1Down.Connect((x,y)->{
            b.FillColor = selectedColor;
        });
    }

    static void detectColorChangeClick(Box2D b){
        b.MouseButton1Down.Connect((x,y)->{
            selectedColor = b.FillColor;
        });
    }

    static void writeToFile() throws IOException{
        FileWriter writer = new FileWriter(curSavePath);
        String toWrite = "";
        for (int i = 0; i < board.length; i ++){
            for (int j = 0; j<board[i].length; j++){
                var col = board[i][j].FillColor;
                if (col.equals(ColorContainer.gray)) continue;
                //looks like row-col-(r,g,b)
                toWrite += i+"-"+j+"-("+col.getRed()+","+col.getGreen()+","+col.getBlue()+")"+"\n";
            }
        }
        writer.write("");
        writer.write(toWrite);
        writer.close();
    }

    static void getFromFile(){
        File file = new File(curSavePath);
        try {
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()){
                String line = reader.nextLine();
                parseReaderLine(line);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        
    }

    static void parseReaderLine(String line){
        var splitByHyphen = line.split("-");
        var x = Integer.parseInt(splitByHyphen[0]);
        var y = Integer.parseInt(splitByHyphen[1]);
        var color = splitByHyphen[2];
        color = color.replace("(", "").replace(")","");
        var rbg = color.split(",");
        var r = Integer.parseInt(rbg[0]);
        var g = Integer.parseInt(rbg[1]);
        var b = Integer.parseInt(rbg[2]);

        StoredBox boxAt = board[x][y];
        boxAt.FillColor = new Color(r,g,b);
    }
}


class StoredBox extends Box2D{
    public int ColorIndex = 0;
    
    public StoredBox(){
        super();
    }

    public void IncrementColorIndex(){
        ColorIndex = ColorIndex < CityBuilder.colors.length-1 ? ColorIndex+1 : 0;
        this.UpdateFillColor();
    }

    public void UpdateFillColor(){
        this.FillColor = CityBuilder.colors[ColorIndex];
    }
}

class ColorContainer{
    static Color gray = Color.gray;
    static Color pink = Color.pink;
    static Color red = Color.red;
    static Color purple = new Color(138,43,226);
    static Color yellow = Color.yellow;
    static Color orange = Color.orange;
    static Color brown = new Color(139,69,19);
    static Color green = Color.green;
    static Color blue = Color.blue;
    static Color black = Color.black;
}