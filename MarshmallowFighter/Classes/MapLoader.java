package MarshmallowFighter.Classes;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Image2D;
import JGamePackage.JGame.Instances.Instance;
import JGamePackage.JGame.Types.Vector2;
import MarshmallowFighter.JSONParser.JSONArray;
import MarshmallowFighter.JSONParser.JSONObject;
import MarshmallowFighter.JSONParser.parser.JSONParser;

public class MapLoader {


    private static Vector2 jsonObjectToVector2(JSONObject arr){
        return new Vector2(Math.toIntExact((long) arr.get("X")), Math.toIntExact((long) arr.get("Y")));
    }

    private static Instance newModel(JGame game, JSONObject obj){
        Model m = new Model(game);
        m.model.Size = jsonObjectToVector2((JSONObject) obj.get("Size"));
        m.model.CFrame.Position = jsonObjectToVector2((JSONObject) obj.get("Position"));
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<Instance> LoadMap(JGame game, String jsonPath){
        ArrayList<Instance> instances = new ArrayList<>();

        try {
            JSONParser parser = new JSONParser();
            Reader reader = new FileReader(jsonPath);
        
            JSONObject jsonObj = (JSONObject) parser.parse(reader);

            JSONArray map = (JSONArray) jsonObj.get("Map");
            Iterator<JSONObject> iter = map.iterator();

            while (iter.hasNext()){
                var obj = iter.next();
                String type = (String) obj.get("Type");
                if (type.equals("Model")){
                    instances.add(newModel(game, jsonObj));
                }

                
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }
}
