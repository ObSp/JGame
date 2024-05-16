package JGamePackage.JGame.GameObjects;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Instances.Instance;
import JGamePackage.JGame.Types.*;
import JGamePackage.JGame.Types.Enum;

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
    public Vector2 AnchorPoint = new Vector2(50);

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
        return overlaps(obj);
    }

    private int getLeft(Vector2 pos){
        return pos.X;
    }

    private int getTop(Vector2 pos){
        return pos.Y;
    }

    private int getRight(Vector2 pos, Vector2 tsz){
        return pos.X+tsz.X;
    }

    private int getBottom(Vector2 pos, Vector2 tsz){
        return pos.Y + tsz.Y;
    }



    private boolean overlaps(Instance other){
        if (other==null) return false;

        Vector2 screenSize = game.getTotalScreenSize();
        Vector2 actualPos = getActualPos(screenSize);

        int leftCorner = getLeft(actualPos);
        int top = getTop(actualPos);
        int rightCorner = getRight(actualPos, screenSize);
        int bottom = getBottom(actualPos, screenSize);

        int cX = Position.X;
        int cY = Position.Y;

        int otherLeft = other.GetCornerPosition(Enum.InstanceCornerType.TopLeft).X+cX;
        int otherTop = other.GetCornerPosition(Enum.InstanceCornerType.TopLeft).Y-cY;
        int otherRight = other.GetCornerPosition(Enum.InstanceCornerType.TopRight).X+cX;
        int otherBottom = other.GetCornerPosition(Enum.InstanceCornerType.BottomLeft).Y-cY;


        return 

        leftCorner < otherRight &&

        rightCorner > otherLeft &&

        top < otherBottom &&

        bottom > otherTop;
    }


}