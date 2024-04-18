package JGamePackage.JGame.Types;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Services.*;

public class ServiceContainer {
    @SuppressWarnings("unused")
    private JGame parent;
    
    public ParserService ParserService;


    public ServiceContainer(JGame parent){
        ParserService = new ParserService(parent);
    }
}
