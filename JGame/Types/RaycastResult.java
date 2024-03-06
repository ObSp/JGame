package JGame.Types;

import JGame.Instances.*;

public class RaycastResult {
    public Instance HitInstance;
    public Vector2 FinalPosition;

    public RaycastResult(Instance hit, Vector2 finalPos){
        HitInstance = hit;
        FinalPosition = finalPos;
    }
}
