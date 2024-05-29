package TwoPlayerFighter.Classes;

public class Controls {
    public int left;
    public int right;
    public int jump;
    public int ability1;

    public Controls(int l, int r, int j, int a1){
        left = l;
        right = r;
        jump = j;
        ability1 = a1;
    }

    public boolean containsKeyCode(int keycode){
        return left==keycode || right==keycode || jump==keycode;
    }

    public boolean isKeyCodeHorizontal(int keycode){
        return left==keycode || right == keycode;
    }

    public boolean isKeyCodeJump(int kc){
        return jump==kc;
    }
}
