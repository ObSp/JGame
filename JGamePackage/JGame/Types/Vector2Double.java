package JGamePackage.JGame.Types;

public class Vector2Double {
    public double X;
    public double Y;

    public Vector2Double(){
        X = 0;
        Y = 0;
    }

    public Vector2Double(double x, double y){
        X = x;
        Y = y;
    }

    public Vector2Double(double n){
        X = n;
        Y = n;
    }

    /**Make this Vector2Double have a Magnitude of 1.0;
     * 
     */
    public void Normalize(){
        double mag = Magnitude();
        if (mag > 1E-05){
            this.X /= mag;
            this.Y /= mag;
        } else {
            this.X = 0;
            this.Y = 0;
        }
    }

    public Vector2Double multiply(double x, double y){
        return new Vector2Double(X*x, Y*y);
    }

    public Vector2Double multiply(double n){
        return new Vector2Double(X*n, Y*n);
    }


    public Vector2 ToVector2(){
        return new Vector2(X, Y);
    }

    
    public double Magnitude(){
        return Math.sqrt(X*X + Y*Y);
    }

    @Override
    public String toString(){
        return "("+X+", "+Y+")";
    }
}
