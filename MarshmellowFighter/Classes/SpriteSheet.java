package MarshmellowFighter.Classes;

public class SpriteSheet {
    public final String[] Sprites;
    public final int[] TickBuffers;
    public int SpritePosition;
    public final boolean Repeating;

    public SpriteSheet(String[] sprites, int[] tickBuffers){
        Sprites = sprites;
        TickBuffers = tickBuffers;
        Repeating = false;
    }

    public SpriteSheet(String[] sprites, int[] tickBuffers, boolean repeating){
        Sprites = sprites;
        TickBuffers = tickBuffers;
        Repeating = repeating;
    }

    public boolean hasNext(){
        return Repeating || SpritePosition+1 < Sprites.length;
    }

    /**Advances the current sprite index to the next index, 
     * or to 0 if there are no Sprites with index < SpritePosition
     * 
     * @return the current sprite position
     */
    public int AdvanceSpritePosition(){
        SpritePosition ++;
        if (SpritePosition >= Sprites.length)
            SpritePosition = 0;
        return SpritePosition;
    }
}
