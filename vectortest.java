import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Types.Vector2;

public class vectortest {
    public static void main(String[] args) {
        JGame game = new JGame();

        

        Vector2 v = new Vector2(-2, -2);
        System.out.println(v.Normalized());
    }
}
