package JGamePackage.JGame.Types;

/**A class that combines Rotation and Position into a single object,
 * also including many utility functions to make the relation between them easier.
 * 
 */
public class CFrame {
    /**The rotation, in Radians, of the CFrame
     * 
     */
    public double Rotation;

    /**The position, as a Vector2, of the CFrame
     * 
     */
    public Vector2 Position;

    public CFrame(Vector2 Position, double Rotation){
        this.Rotation = Rotation;
        this.Position = Position;
    }

    /**Modifies this CFrame's rotation to look at the other position
     * 
     * @param position : The position to look at
     */
    public void LookAt(Vector2 position){
        double xDiff = position.X-Position.X;
        if (xDiff == 0){ //not sure if this can cause any potential errors, but avoids the arithmetic "tried to divide by zere/NaN" error
            return;
        }
        double yDiff = position.Y-Position.Y;
        Rotation = Math.atan(yDiff/xDiff);
    }

    /**Returns a new CFrame with a Position of {@code origin} and a rotation
     * of {@code LookAt(lookAt)}.
     * 
     * @param origin : The origin position of the CFrame
     * @param lookAt : The position to look at
     * @return
     */
    public static CFrame LookAt(Vector2 origin, Vector2 lookAt){
        CFrame c = new CFrame(origin, 0);
        c.LookAt(lookAt);
        return c;
    }
}
