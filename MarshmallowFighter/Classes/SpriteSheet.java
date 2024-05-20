package MarshmallowFighter.Classes;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class SpriteSheet {
    public final String[] Sprites;
    public BufferedImage[] ImageBuffer;
    public final int[] TickBuffers;
    public int SpritePosition = 0;
    public final boolean Repeating;

    private void imgBufferFromPaths(){
        for (int i = 0; i < Sprites.length; i++){
            try {
                ImageBuffer[i] = ImageIO.read(new File(Sprites[i]));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public SpriteSheet(String[] sprites, int[] tickBuffers){
        Sprites = sprites;
        TickBuffers = tickBuffers;
        Repeating = false;
        ImageBuffer = new BufferedImage[Sprites.length];
        imgBufferFromPaths();
    }

    public SpriteSheet(String[] sprites, int[] tickBuffers, boolean repeating){
        Sprites = sprites;
        TickBuffers = tickBuffers;
        Repeating = repeating;
        ImageBuffer = new BufferedImage[Sprites.length];
        imgBufferFromPaths();
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

    public SpriteSheet copy(){
        return new SpriteSheet(Sprites.clone(), TickBuffers != null ? TickBuffers.clone() : null, Repeating);
    }
}
