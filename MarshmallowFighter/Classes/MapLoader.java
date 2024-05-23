package MarshmallowFighter.Classes;

import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Iterator;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Box2D;
import JGamePackage.JGame.Instances.Image2D;
import JGamePackage.JGame.Instances.Instance;
import JGamePackage.JGame.Types.Vector2;
import MarshmallowFighter.JSONParser.JSONArray;
import MarshmallowFighter.JSONParser.JSONObject;
import MarshmallowFighter.JSONParser.parser.JSONParser;

public class MapLoader {


    private static Vector2 jsonObjectToVector2(JSONObject arr){
        return new Vector2((int)((long) arr.get("X")), (int)((long) arr.get("Y")));
    }

    private static Instance newModel(JGame game, JSONObject obj){
        Model m = new Model(game);
        m.model = new Image2D();
        m.model.Size = jsonObjectToVector2((JSONObject) obj.get("Size"));
        m.model.CFrame.Position = jsonObjectToVector2((JSONObject) obj.get("Position"));
        m.model.SetImagePath((String) obj.get("ImagePath"));
        m.model.AnchorPoint = new Vector2(0,100);
        m.model.Associate = m;
        game.addInstance(m.model);


        m.hitbox = new Box2D();
        m.hitbox.Size = new Vector2(
            (double) obj.get("HitboxScaleX")*(double) m.model.Size.X, 
            (double) obj.get("HitboxScaleY") * (double) m.model.Size.Y
        );
        m.hitbox.CFrame.Position = m.model.CFrame.Position.add(
            (int) (m.hitbox.Size.X/(long) obj.get("HitboxShiftRightDiv")), 
            -m.hitbox.Size.Y
        );
        return m.model;
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
                    instances.add(newModel(game, obj));
                }

                
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return instances;

    }
}
