package JGame.Types;

import JGame.JGame;
import JGame.Services.*;

public class ServiceContainer {
    @SuppressWarnings("unused")
    private JGame parent;
    
    public ParserService ParserService;


    public ServiceContainer(JGame parent){
        ParserService = new ParserService(parent);
    }
}
