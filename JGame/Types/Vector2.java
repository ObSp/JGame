package JGame.Types;

public class Vector2 {
    public int X;
    public int Y;

    public Vector2(int x, int y){
        X=x;
        Y=y;
    }


    public void addTo(Vector2 other){
        X += other.X;
        Y += other.Y;
    }

    @Override
    public String toString(){
        return "("+X+", "+Y+")";
    }
}
