package Classes;

import java.io.File;

import JGamePackage.JGame.JGame;

public class Exporter {

    JGame parent;
    

    public Exporter(JGame parent){
        this.parent = parent;
    }

    public void ExportInstancesAsJGI(){
        this.parent.Services.ParserService.ExportInstancesToJSON(this.parent.getInstances(), new File("JGameStudio\\Saves\\latestSave.json"));
    }
}
