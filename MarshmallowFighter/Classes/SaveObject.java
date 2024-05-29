package MarshmallowFighter.Classes;

import JGamePackage.JGame.Types.Vector2;

public class SaveObject {
    public int MarshmallowsCollected = 0;
    public int MarshmallowsKilled = 0;
    public Vector2 SavedPosition;
    

    public SaveObject(int collected, int killed, Vector2 pos){
        MarshmallowsCollected = collected;
        MarshmallowsKilled = killed;
        SavedPosition = pos;
    }

    public String toString(){
        return "Collected = "+MarshmallowsCollected+", Killed = "+MarshmallowsKilled+", Saved Position = "+SavedPosition;
    }
}
