package graphicalTools;

import dbsTools.DBSTools;
import java.util.ArrayList;
import java.util.List;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javax.persistence.*;

/**
 * Auxiliary class to create and set different GUI components of application.
 * No DBS actions here.
 * @author shchesof
 */
public class ViewManager {
    
    // main application instances
    private final AnchorPane root;
    private final Stage stage;
    private final EntityManager em;
    private final ButtonsMaker bm;
    private final DBSTools dt;
    private final Font font;
    private final Label chooseEntity;
    public Scene scene;
    
    // text fields containing data of dog to create
    public TextField dogName, dogCharacteristic, dogBreed, dogAge, dogWeight;
    // list for these text fields
    protected List<TextField> textFieldsCreate;
    // main label of a start menu
    private Label chooseForSponsorship;

    public ViewManager(Stage stage, EntityManager em) {
        this.em = em;
        this.stage = stage;
        this.stage.setTitle("Pet Shelter Database");
        this.stage.setResizable(false);
        this.root = new AnchorPane();
        this.bm = new ButtonsMaker(root, this, em);
        this.dt = new DBSTools(em, this);
        this.scene = new Scene(root, 400, 180);
        this.font = new Font("Arial", 15);
        
        // set main label
        chooseEntity = new Label("Choose entity to work with:");
        chooseEntity.setTextFill(Color.BLACK);
        chooseEntity.setFont(font);
        chooseEntity.setTranslateX(120);
        chooseEntity.setTranslateY(30);
        root.getChildren().add(chooseEntity);
        
        // create two main buttons
        bm.createDogButton();
        bm.createSponsorButton();
        
        // init text fields to create dog
        textFieldsCreate = new ArrayList<>();
        textFieldsCreate.add(dogName = new TextField());
        textFieldsCreate.add(dogCharacteristic = new TextField());
        textFieldsCreate.add(dogBreed = new TextField());
        textFieldsCreate.add(dogAge = new TextField());
        textFieldsCreate.add(dogWeight = new TextField());
    }
    
    /**
     * Sets scene to choose which one of CRUD operations
     * to perfrom on Dog entity.
     */
    protected void setDogActionsScene() {
        root.getChildren().clear();
        // set label
        Label dogActions = new Label("Possible actions with the entity DOG:");
        dogActions.setTextFill(Color.BLACK);
        dogActions.setFont(font);
        dogActions.setTranslateX(80);
        dogActions.setTranslateY(30);
        root.getChildren().add(dogActions);
        // create all buttons
        bm.createCreateDogBtn();
        bm.createReadDogBtn();
        bm.createUpdateDogBtn();
        bm.createDeleteDogBtn();
        bm.createDogActionsExitBtn();
    }
    
    /**
     * Sets scene to choose which one of CRUD operations
     * to perfrom on Sponsorship entity.
     */
    protected void setSponsorshipActionsScene() {
        root.getChildren().clear();
        // set label
        Label dogActions = new Label("Possible actions with the relationship SPONSORSHIP:");
        dogActions.setTextFill(Color.BLACK);
        dogActions.setFont(font);
        dogActions.setTranslateX(20);
        dogActions.setTranslateY(30);
        root.getChildren().add(dogActions);
        // create all buttons
        bm.createCreateSponsorBtn();
        bm.createReadSponsorBtn();
        bm.createDeleteSponsorBtn();
        bm.createSponsorActionsExitBtn();
    }
    
    /**
     * Sets scene with text fields to CREATE Dog instance.
     */
    protected void setDogCreateScene() {
        root.getChildren().clear();
        // init text fields
        for (int i = 0; i < 5; i++) {
            createTextFieldsToCreateDog(i);
        }
        root.getChildren().addAll(textFieldsCreate);
        // create buttons
        bm.createCreateSubmitBtn();
        bm.createCreateClear();
        bm.createCreateExitBtn();
    }
    
    /**
     * Sets start menu scene.
     */
    protected void setInitScene() {
        root.getChildren().clear();
        root.getChildren().add(chooseEntity);
        bm.createDogButton();
        bm.createSponsorButton();
    }
    
    /**
     * Sets Dog READ scene.
     */
    protected void setDogReadScene() {
        root.getChildren().clear();
        bm.createReadExitBtn();
        // set scrollpane subscene
        DogReadSubscene readSubscene = new DogReadSubscene(scene, em, dt);
        readSubscene.setLayoutX(20);
        readSubscene.setLayoutY(10);
        root.getChildren().add(readSubscene);
    }
    
    /**
     * Sets Dog DELETE scene.
     * @param idToDelete
     */
    protected void setDogDeleteScene(TextField idToDelete) {
        root.getChildren().clear();
        // set label
        Label enterID = new Label("Enter ID of dog to delete:");
        enterID.setFont(font);
        enterID.setLayoutX(120);
        enterID.setLayoutY(20);
        idToDelete.setLayoutX(130);
        idToDelete.setLayoutY(enterID.getLayoutY()+30);
        // create all buttons
        bm.createDeleteSubmitBtn();
        bm.createDeleteExitBtn();
        root.getChildren().addAll(idToDelete, enterID);
    }
    
    /**
     * Sets Dog UPDATE scene.
     * @param idToUpdate
     */
    protected void setDogUpdateScene(TextField idToUpdate) {
        root.getChildren().clear();
        // set label
        Label enterID = new Label("Enter ID of dog to update:");
        enterID.setFont(font);
        enterID.setLayoutX(120);
        enterID.setLayoutY(20);
        // configure text field
        idToUpdate.setLayoutX(130);
        idToUpdate.setLayoutY(enterID.getLayoutY()+30);
        // create all buttons
        bm.createUpdateSubmitBtn();
        bm.createUpdateExitBtn();
        root.getChildren().addAll(idToUpdate, enterID);
    }
    
    /**
     * Sets Sponsorship CREATE scene.
     * @param dogID
     * @param sponsorID
     */
    protected void setSponsorCreateScene(TextField dogID, TextField sponsorID) {
        root.getChildren().clear();   
        // set label for dog's ID
        Label enterID1 = new Label("Enter ID of dog:");
        enterID1.setFont(font);
        enterID1.setLayoutX(150);
        enterID1.setLayoutY(20);
        dogID.setLayoutX(130);
        dogID.setLayoutY(enterID1.getLayoutY()+30);
        root.getChildren().addAll(dogID, enterID1);
        // set label for sponsor's ID
        Label enterID2 = new Label("Enter ID of sponsor:");
        enterID2.setFont(font);
        enterID2.setLayoutX(140);
        enterID2.setLayoutY(dogID.getLayoutY()+30);
        sponsorID.setLayoutX(130);
        sponsorID.setLayoutY(enterID2.getLayoutY()+30);
        root.getChildren().addAll(sponsorID, enterID2);
        // create all buttons
        bm.createCreateSponsorSubmitBtn();
        bm.createCreateSponsorExitBtn();
    }
    
    /**
     * Sets Sponsorship DELETE scene.
     * @param dogID
     * @param sponsorID
     */
    protected void setSponsorDeleteScene(TextField dogID, TextField sponsorID) {
        root.getChildren().clear();    
        // set label for dog's ID
        Label enterID1 = new Label("Enter ID of dog:");
        enterID1.setFont(font);
        enterID1.setLayoutX(150);
        enterID1.setLayoutY(20);
        dogID.setLayoutX(130);
        dogID.setLayoutY(enterID1.getLayoutY()+30);
        root.getChildren().addAll(dogID, enterID1);
        // set label for sponsor's ID
        Label enterID2 = new Label("Enter ID of sponsor:");
        enterID2.setFont(font);
        enterID2.setLayoutX(140);
        enterID2.setLayoutY(dogID.getLayoutY()+30);
        sponsorID.setLayoutX(130);
        sponsorID.setLayoutY(enterID2.getLayoutY()+30);
        root.getChildren().addAll(sponsorID, enterID2);
        // create all buttons
        bm.createCreateSponsorExitBtn();
        bm.createDeleteSponsorSubmit();
    }
    
    /**
     * Set Sponsorship READ scene.
     */
    protected void setSponsorReadScene() {
        root.getChildren().clear();
        // set label
        chooseForSponsorship = new Label("Choose entity to work with:");
        chooseForSponsorship.setTextFill(Color.BLACK);
        chooseForSponsorship.setFont(font);
        chooseForSponsorship.setTranslateX(120);
        chooseForSponsorship.setTranslateY(30);
        root.getChildren().add(chooseForSponsorship);
        // create all buttons
        bm.readDogSponsorshipButton();
        bm.readSponsorSponsorshipButton();
        bm.createReadSponsorsExitBtn();
    }
    
    /**
     * Set scene to enter ID of Sponsor intance for browsing of a list
     * of related Dog instances.
     * @param sponsorToRead
     */
    protected void setSponsorPrereadScene(TextField sponsorToRead) {
        root.getChildren().clear();    
        // set label
        Label enterID = new Label("Enter ID of sponsor:");
        enterID.setFont(font);
        enterID.setLayoutX(140);
        enterID.setLayoutY(20);
        // configure text fiels
        sponsorToRead.setLayoutX(130);
        sponsorToRead.setLayoutY(enterID.getLayoutY()+30);
        root.getChildren().addAll(sponsorToRead, enterID);
        // create all buttons
        bm.createPrereadSponsorshipExit();
        bm.createReadSponsorsDogsSubmit();
    }
    
    /**
     * Set scene to enter ID of Dog intance for browsing of a list
     * of related Sponsor instances.
     * @param dogToRead
     */
    protected void setDogPrereadScene(TextField dogToRead) {
        root.getChildren().clear();    
        // set label
        Label enterID = new Label("Enter ID of dog:");
        enterID.setFont(font);
        enterID.setLayoutX(150);
        enterID.setLayoutY(20);
        // configure text field
        dogToRead.setLayoutX(130);
        dogToRead.setLayoutY(enterID.getLayoutY()+30);
        root.getChildren().addAll(dogToRead, enterID);
        // create all buttons
        bm.createReadDogsSponsorsSubmit();
        bm.createPrereadSponsorshipExit();
    }
    
    /**
     * Initialize text fields to enter data to CREATE new Dog instance.
     * @param i index in for cycle
     */
    protected void createTextFieldsToCreateDog(int i) {
        if (i == 0) {
            textFieldsCreate.get(i).setLayoutX(90);
            textFieldsCreate.get(i).setLayoutY(10);
            textFieldsCreate.get(i).setPromptText("Enter dog's name");
        }
        else {
            textFieldsCreate.get(i).setLayoutX(textFieldsCreate.get(i-1).getLayoutX());
            textFieldsCreate.get(i).setLayoutY(textFieldsCreate.get(i-1).getLayoutY()+30);
            switch(i) {
                case 1:
                    textFieldsCreate.get(i).setPromptText("Enter dog's characteristic");
                    break;
                case 2:
                    textFieldsCreate.get(i).setPromptText("Enter dog's breed");
                    break;
                case 3:
                    textFieldsCreate.get(i).setPromptText("Enter dog's age");
                    break;
                case 4:
                    textFieldsCreate.get(i).setPromptText("Enter dog's weight");
                    break;
                default:
                    break;
            }
        }
    }
}