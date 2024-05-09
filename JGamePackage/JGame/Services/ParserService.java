package JGamePackage.JGame.Services;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Box2D;
import JGamePackage.JGame.Instances.Instance;
import JGamePackage.JGame.Instances.Oval2D;
import JGamePackage.JGame.Types.Vector2;

public class ParserService extends Service {
    
    

    public ParserService(JGame parent){
        super(parent);
    }



    private Object stringToValue(String propName, String val){
        if (propName.equals("Position") || propName.equals("Size") || propName.equals("Velocity"))
            return Vector2.fromString(val);

        if (propName.equals("Name"))
            return val;

        if (propName.equals("Solid") || propName.equals("Anchored"))
            return Boolean.valueOf(val).booleanValue();
        
        return null;
    }

    @SuppressWarnings("resource")
    public ArrayList<Instance> ParseJCToInstances(File file) throws IOException{
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            throw new Error(e.toString());
        }

        ArrayList<Instance> instances = new ArrayList<>();
        Instance curInstance = null;

        String curLine;

        while ((curLine = reader.readLine()) != null){
            //new instance
            if (curLine.contains("$#")){
                if (curInstance != null){
                    instances.add(curInstance);
                }

                String type = curLine.split("$#")[1];
                if (type.equals("Box2D")){
                    curInstance = new Box2D();
                } else if(type.equals("Image2D")){
                    curInstance = new Box2D();
                } else if(type.equals("Oval2D")){
                    curInstance = new Oval2D();
                }
                
            } else {
                var split = curLine.split("=");
                var left = split[0];
                var right = split[1];
                curInstance.setInstanceVariableByName(left, stringToValue(left, right));
            }
        }


        return instances;
    }

    public void ExportInstancesToJC(Instance[] instances, File destination){
        
    }

    
}