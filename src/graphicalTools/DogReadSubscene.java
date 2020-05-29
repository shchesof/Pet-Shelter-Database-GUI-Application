package graphicalTools;

import dbsTools.DBSTools;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javax.persistence.*;

/**
 * Auxiliary class to create scrollable scene to display a list
 * of all existing Dog instances.
 * @author shchesof
 */
public class DogReadSubscene extends SubScene {    
    
    // DBS ACTION HERE
    public DogReadSubscene(Scene scene, EntityManager em, DBSTools dt) {
        // set scrollpane
        super(new ScrollPane(), 350, 140);
        ScrollPane root = (ScrollPane) this.getRoot();
        Text allDogs = new Text();
        // read from DBS with dbsTools
        String result = dt.readAll();
        // set result text on the scene
        allDogs.setText(result);
        allDogs.wrappingWidthProperty().bind(this.widthProperty());
        root.setFitToWidth(true);
        root.setContent(allDogs);
    }
}