package JGame.Types;

public class Vector2 {
    public int X;
    public int Y;

    public Vector2(int x, int y){
        X=x;
        Y=y;
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
}
