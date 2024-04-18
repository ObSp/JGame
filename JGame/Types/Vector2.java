package JGame.Types;


/**An object representing a point in 2D space
 * 
 */
public class Vector2 {
    public int X;
    public int Y;


    //static stuff

    /**Shorthand for writing Vector2(0,0). */
    public static Vector2 zero = new Vector2(0, 0);
    /**Shorthand for writing Vector2(-1,0). */
    public static Vector2 left = new Vector2(-1, 0);
    /**Shorthand for writing Vector2(0,1). */
    public static Vector2 up = new Vector2(0, 1);
    /**Shorthand for writing Vector2(1,0). */
    public static Vector2 right = new Vector2(1, 0);
    /**Shorthand for writing Vector2(0,-1). */
    public static Vector2 down = new Vector2(0, -1);


    /**Creates a new Vector2 with the specified X and Y coordinate points as ints.
     * 
     * @param x
     * @param y
     */
    public Vector2(int x, int y){
        X=x;
        Y=y;
    }

    public Vector2(double x, double y){
        X=(int) x;
        Y= (int) y;
    }


    public Vector2 add(Vector2 other){
        X += other.X;
        Y += other.Y;
        return this;
    }

    public Vector2 subtract(Vector2 other){
        X -= other.X;
        Y -= other.Y;
        return this;
    }

    public boolean isZero(){
        return X==0 && Y==0;
    }

    public int Magnitude(){
        return (int) Math.sqrt(Math.pow(X, 2)+Math.pow(Y, 2));
    }

    @Override
    public String toString(){
        return "("+X+", "+Y+")";
    }

    @Override
    public Vector2 clone(){
        return new Vector2(X, Y);
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;

        if (!(obj instanceof Vector2)) return false;

        Vector2 other = (Vector2) obj;

        return X==other.X && Y == other.Y;
    }

}
