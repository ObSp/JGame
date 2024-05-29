package MarshmallowFighter.Classes;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

import JGamePackage.JGame.*;
import JGamePackage.JGame.Types.Vector2;
import JGamePackage.lib.JSONSimple.*;
import JGamePackage.lib.JSONSimple.parser.JSONParser;

public class DataHandler {
    JGame game;

    public DataHandler(JGame game){
        this.game = game;
    }

    @SuppressWarnings("unchecked")
    public void ExportData(SaveObject data, String path){
        if (data == null) return;
        JSONObject obj = new JSONObject();
        obj.put("MarshmallowsCollected", data.MarshmallowsCollected);
        obj.put("MarshmallowsKilled", data.MarshmallowsKilled);
        obj.put("SavedPosition", data.SavedPosition.toString());

        try {
            FileWriter writer = new FileWriter(path);
            writer.write(obj.toJSONString());
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }

    public SaveObject ImportData(String path){
        File f = new File(path);
        if (!f.exists()) return null;
        try {
            FileReader reader = new FileReader(f);
            JSONParser parser = new JSONParser();

            JSONObject data = (JSONObject) parser.parse(reader);

            int col = (int) (long) data.get("MarshmallowsCollected");
            int killed = (int) (long) data.get("MarshmallowsKilled");
            Vector2 lastPos = Vector2.fromString((String) data.get("SavedPosition"));
            return new SaveObject(col, killed, lastPos);
        } catch (Exception e) {
            e.printStackTrace();
        }


        return null;
    }
}
