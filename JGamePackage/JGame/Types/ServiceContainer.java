package JGamePackage.JGame.Types;

import JGamePackage.JGame.JGame;
import JGamePackage.JGame.Services.*;

public class ServiceContainer {
    @SuppressWarnings("unused")
    private JGame parent;
    
    public ParserService ParserService;
    public PhysicsService PhysicsService;
    public TweenService TweenService;
    public SceneService SceneService;


    public ServiceContainer(JGame parent){
        ParserService = new ParserService(parent);
        PhysicsService = new PhysicsService(parent);
        TweenService = new TweenService(parent);
        SceneService = new SceneService(parent);
    }
}
