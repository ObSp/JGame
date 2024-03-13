package JGame.Instances;

import java.awt.Graphics2D;
import java.nio.file.*;

public class Image2D extends Instance{
    String ImagePath;

    public Image2D(){
        Path currentRelativePath = Paths.get("");
        @SuppressWarnings("unused")
        Path s = currentRelativePath.toAbsolutePath();
    }


    @Override
    public void render(Graphics2D g) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'render'");
    }
    
}
