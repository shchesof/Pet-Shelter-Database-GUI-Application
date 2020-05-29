package graphicalTools;

import dbsTools.DBSTools;
import entities.*;
import javafx.scene.Scene;
import javafx.scene.SubScene;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javax.persistence.EntityManager;

/**
 * Auxiliary class to create scrollable scene to display a list
 * of all existing Sponsorship instances.
 * DBS ACTIONS HERE!
 * @author shchesof
 */
public class SponsorshipReadSubscene extends SubScene {

    /**
     * Display all Sponsor instances related to given Dog instance.
     * @param scene
     * @param em
     * @param dt
     * @param dog
     */
    public SponsorshipReadSubscene(Scene scene, EntityManager em, DBSTools dt, Dog dog) {
        // set scrollpane
        super(new ScrollPane(), 350, 140);
        ScrollPane root = (ScrollPane) this.getRoot();
        Text allSponsors = new Text();
        // read all sponsors of given dog from DBS
        String result = dt.readAllSponsors(dog);
        // set result text on scene
        allSponsors.setText(result);
        allSponsors.wrappingWidthProperty().bind(this.widthProperty());
        root.setFitToWidth(true);
        root.setContent(allSponsors);
    }
    
    /**
     * Display all Dog instances related to given Sponsor instance.
     * @param scene
     * @param em
     * @param dt
     * @param sponsor
     */
    public SponsorshipReadSubscene(Scene scene, EntityManager em, DBSTools dt, Sponsor sponsor) {
        // set scrollpane
        super(new ScrollPane(), 350, 140);
        ScrollPane root = (ScrollPane) this.getRoot();
        Text allDogs = new Text();
        // read all dogs of given sponsor from DBS
        String result = dt.readAllDogs(sponsor);
        // set result text on scene
        allDogs.setText(result);
        allDogs.wrappingWidthProperty().bind(this.widthProperty());
        root.setFitToWidth(true);
        root.setContent(allDogs);
    }
}