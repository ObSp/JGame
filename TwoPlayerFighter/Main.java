package TwoPlayerFighter;

import JGamePackage.JGame.JGame;
import TwoPlayerFighter.Classes.Game;
import TwoPlayerFighter.Classes.Menu;

public class Main {
    static JGame game = new JGame();

    static Menu menu = new Menu(game);

    public static void main(String[] args) {
        menu.Show();
        new Game(game).start();
    }
}
