package JGame.Instances;

import java.awt.Graphics;

import java.nio.file.*;

public class Image2D extends Instance{
    String ImagePath;

    public Image2D(){
        Path currentRelativePath = Paths.get("");
        Path s = currentRelativePath.toAbsolutePath();
    }


    @Override
    public void render(Graphics g) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'render'");
    }
    
}
