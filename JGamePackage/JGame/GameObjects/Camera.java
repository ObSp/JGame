package JGamePackage.JGame.GameObjects;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Instance;
import JGamePackage.JGame.Types.*;

/**A class representing the end users' viewport of the screen.
 * Instances refer to this class while rendering to make sure they should render. <p>
 * This class is not meant to be instantiated by anything other than a {@code JGame},
 * but may be viable to in same special cases.
 * 
 */
public class Camera extends GameObject {
    private final JGame game;


    /**The position of the Camera, which dictates the render position of all objects. By default, this is the center of the screen
     * 
     */
    public Vector2 Position;
    public Vector2 AnchorPoint = new Vector2(0);

    public Camera(JGame game){
        this.game = game;

        Position = new Vector2(0,0);
    }

    public Vector2 GetInstancePositionRelativeToCameraPosition(Instance obj){
        return obj.CFrame.Position.subtract(getActualPos(game.getTotalScreenSize()));
    }

    private Vector2 getActualPos(Vector2 size){
        return Position.subtract(getAnchorPointOffset(size));
    }

    protected Vector2 getAnchorPointOffset(Vector2 screenSize){
        return new Vector2(getAnchorPointOffsetX(screenSize.X), getAnchorPointOffsetY(screenSize.Y));
    }

    protected int getAnchorPointOffsetX(int xSize){
        return (int) ((double)xSize*(((double)AnchorPoint.X)/100.0));
    }

    protected int getAnchorPointOffsetY(int ySize){
        return (int) ((double)ySize*(((double)AnchorPoint.Y)/100.0));
    }

    public boolean isInstanceInViewport(Instance obj){
        return overlaps(obj, obj.GetRenderPosition());
    }

    public boolean isInstanceInViewport(Instance obj, Vector2 renderpos){
        return overlaps(obj, renderpos);
    }



    private boolean overlaps(Instance other, Vector2 renderpos){
        if (other==null) return false;

        Vector2 screenSize = game.getTotalScreenSize();

        int left = 0;
        int top = 0;
        int right = left + screenSize.X;
        int bottom = top + screenSize.Y;

        Vector2 topLeft = renderpos;
        int otherLeft = topLeft.X;
        int otherRight = topLeft.X+other.Size.X;
        int otherTop = topLeft.Y;
        int otherBottom = otherTop+other.Size.Y;

        boolean visibleLeft = otherRight > left;
        boolean visibleRight = otherLeft < right;
        boolean visibleTop = otherBottom > top;
        boolean visibleBottom = otherTop < bottom;

        return visibleLeft && visibleRight && visibleTop && visibleBottom;
    }


}