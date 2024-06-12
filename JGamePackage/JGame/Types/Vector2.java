package JGamePackage.JGame.Types;


/**An object representing a point in 2D space
 * 
 */
public class Vector2 {
    public int X;
    public int Y;


    //static stuff

    /**Shorthand for writing Vector2(0,0). */
    public static final Vector2 zero = new Vector2(0, 0);
    /**Shorthand for writing Vector2(-1,0). */
    public static final Vector2 left = new Vector2(-1, 0);
    /**Shorthand for writing Vector2(0,1). */
    public static final Vector2 up = new Vector2(0, 1);
    /**Shorthand for writing Vector2(1,0). */
    public static final Vector2 right = new Vector2(1, 0);
    /**Shorthand for writing Vector2(0,-1). */
    public static final Vector2 down = new Vector2(0, -1);

    /**Converts a {@code String} to a {@code Vector2}. The input string must follow this format:
     * (X, Y). Note that whitespace doesn't matter and parantheses don't matter, only the comma is required.
     * 
     * @param str : The input string, following the format specified above
     * @return A new Vector2
     */
    public static Vector2 fromString(String str){
        String stripped = str.replace("(", "").replace(")", "").replace(" ", "");

        String[] split = stripped.split(",");

        int xCoord = Integer.parseInt(split[0]);
        int yCoord = Integer.parseInt(split[1]);


        return new Vector2(xCoord, yCoord);
    }


    /**Creates a new Vector2 with the specified X and Y coordinate points as ints.
     * 
     * @param x
     * @param y
     */
    public Vector2(int x, int y){
        X=x;
        Y=y;
    }

    /**Creates a new Vector2 with the specified X and Y coordinate points as inst by casting from double to int.
     * 
     * @param x
     * @param y
     */
    public Vector2(double x, double y){
        X=(int) x;
        Y= (int) y;
    }

    /**Creates a new Vector2 with the X and Y coordinates set to 0
     * 
     */
    public Vector2(){
        X = 0;
        Y = 0;
    }

    /**Creates a new Vector2 and sets the Vector2's X and Y coordinates equal to the {@code n} parameter.
     * 
     * @param n : The number to set the X and Y coordinates to
     */
    public Vector2(int n){
        X = n;
        Y = n;
    }


    public Vector2 add(Vector2 other){
        return new Vector2(X+other.X, Y+other.Y);
    }

    public Vector2 add(int n){
        return new Vector2(X+n, Y+n);
    }

    public Vector2 add(int x, int y){
        return new Vector2(X+x, Y+y);
    }

    public Vector2 subtract(Vector2 other){
        return new Vector2(X-other.X, Y-other.Y);
    }

    public Vector2 subtract(int n){
        return new Vector2(X-n, Y-n);
    }

    public Vector2 multiply(Vector2 other){
        return new Vector2(X*other.X, Y*other.Y);
    }

    public Vector2 divide(int x, int y){
        return new Vector2(X/x, Y/y);
    }

    public Vector2 divide(int n){
        return new Vector2(X/n, Y/n);
    }

    public Vector2 divide(double x, double y){
        return new Vector2((double) X/x, (double) Y/y);
    }

    public Vector2 divide(double n){
        return new Vector2((double) X/n, (double) Y/n);
    }

    public Vector2 multiply(int x, int y){
        return new Vector2(X*x, Y*y);
    }

    public Vector2 multiply(int n){
        return new Vector2(X/n, Y/n);
    }

    public boolean isZero(){
        return X==0 && Y==0;
    }

    public double Magnitude(){
        return Math.sqrt(X*X + Y*Y);
    }

    /** Returns a {@code Vector2Double} with the same direction as this Vector2 but with a length of 1.
     * 
     * @return A {@code Vector2Double} with the same direction as this Vector2 but with a length of 1
     */
    public Vector2Double Normalized(){
        Vector2Double res = new Vector2Double(X, Y);
        res.Normalize();
        return res;
    }

    private double lerp1(double a, double b, double t){
        return (1-t) * a + t*b;
    }

    public void lerp(Vector2 b, double t){
        X = (int) lerp1(X, b.X, t);
        Y = (int) lerp1(Y, b.Y, t);
    }

    public static Vector2 lerp(Vector2 a, Vector2 b, double t){
        Vector2 n = a.clone();
        n.lerp(b, t);
        return n;
    }

    @Override
    public String toString(){
        return "("+X+", "+Y+")";
    }

    @Override
    public Vector2 clone(){
        return new Vector2(X, Y);
    }

    public Vector2Double ToVector2Double(){
        return new Vector2Double(X, Y);
    }

    @Override
    public boolean equals(Object obj){
        if (this == obj) return true;

        if (!(obj instanceof Vector2)) return false;

        Vector2 other = (Vector2) obj;

        return X==other.X && Y == other.Y;
    }
}