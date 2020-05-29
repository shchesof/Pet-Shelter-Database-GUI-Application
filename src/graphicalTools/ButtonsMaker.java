package graphicalTools;

import dbsTools.DBSTools;
import entities.*;
import java.util.ArrayList;
import java.util.List;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javax.persistence.*;

/**
 * Class to make all Buttons in the applicaton. It contains most of the DBS
 * actions both on its own and with DBSTools. Methods containing DBS actions
 * are marked with "DBS ACTIONS HERE!".
 * @author shchseof
 */
public class ButtonsMaker {
    
    // to add components to the root
    private final AnchorPane root;
    // to set certain scenes
    private final ViewManager vm;
    // to work with entities
    private final EntityManager em;
    // to make necessary labels
    private final LabelsMaker lm;
    // to work with the DBS
    private final DBSTools dbsTools;
    
    // just different necessary text fields
    private TextField idToDelete, idToUpdate;
    private TextField sponsorID, dogID, dogToRead, sponsorToRead;
    private final TextField dogNameUpd, dogCharacteristicUpd,
            dogAgeUpd, dogWeightUpd;

    /**
     * Text fields containig data of dog to update.
     */
    protected List<TextField> textFieldsUpdate;
    
    // labels to inform user about success/failure of his or her actions
    private Label added, warning, notFound, deleted, updated;

    public ButtonsMaker(AnchorPane root, ViewManager vm, EntityManager em) {
        this.em = em;
        this.vm = vm;
        this.root = root;
        
        lm = new LabelsMaker(root, vm);
        dbsTools = new DBSTools(em, vm);
        
        // define text fields to update dog's data
        textFieldsUpdate = new ArrayList<>();
        textFieldsUpdate.add(dogNameUpd = new TextField());
        textFieldsUpdate.add(dogCharacteristicUpd = new TextField());
        textFieldsUpdate.add(new TextField());
        textFieldsUpdate.add(dogAgeUpd = new TextField());
        textFieldsUpdate.add(dogWeightUpd = new TextField());
    }
    
    /**
     * Creates button "DOG" to choose Dog entity to work with.
     */
    protected void createDogButton() {
        Button dogBtn = new Button("DOG");
        dogBtn.setLayoutX(190);
        dogBtn.setLayoutY(70);
        dogBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                vm.setDogActionsScene();
            }
        });
        root.getChildren().add(dogBtn);
    }
    
    /**
     * Creates button "SPONSORSHIP" to choose Sponsorship entity to work with.
     */
    protected void createSponsorButton() {
        Button sponsorshipBtn = new Button("SPONSORSHIP");
        sponsorshipBtn.setLayoutY(110);
        sponsorshipBtn.setLayoutX(160);
        sponsorshipBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                vm.setSponsorshipActionsScene();
            }
        });
        root.getChildren().add(sponsorshipBtn);
    }
    
    /**
     * Creates button "CREATE" to create new Dog instance.
     */
    protected void createCreateDogBtn () {
        Button createDogBtn = new Button("CREATE");
        createDogBtn.setLayoutX(40);
        createDogBtn.setLayoutY(70);
        createDogBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                vm.setDogCreateScene();
            }
        });
        root.getChildren().add(createDogBtn);
    }
    
    /**
     * Creates button "READ" to read list of all existing Dog instances.
     */
    protected void createReadDogBtn() {
        Button readDogBtn = new Button("READ");
        readDogBtn.setLayoutX(130);
        readDogBtn.setLayoutY(70);
        readDogBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                vm.setDogReadScene();
            }
        });
        root.getChildren().add(readDogBtn);
    }
    
    /**
     * Creates button "UPDATE" to update existing Dog instance.
     */
    protected void createUpdateDogBtn() {
        Button updateDogBtn = new Button("UPDATE");
        updateDogBtn.setLayoutX(210);
        updateDogBtn.setLayoutY(70);
        updateDogBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                idToUpdate = new TextField();
                vm.setDogUpdateScene(idToUpdate);
            }
        });
        root.getChildren().add(updateDogBtn);
    }
    
    /**
     * Creates button "DELETE" to delete existing Dog instance.
     */
    protected void createDeleteDogBtn() {
        Button deleteDogBtn = new Button("DELETE");
        deleteDogBtn.setLayoutX(300);
        deleteDogBtn.setLayoutY(70);
        deleteDogBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                idToDelete = new TextField();
                vm.setDogDeleteScene(idToDelete);
            }
        });
        root.getChildren().add(deleteDogBtn);
    }
    
    /**
     * Creates button "Submit" in the Dog CREATE menu.
     * Allows to add dog to DBS. 
     * DBS ACTIONS HERE!
     */
    protected void createCreateSubmitBtn() {
        Button createSubmit = new Button("Submit");
        createSubmit.setLayoutX(280);
        createSubmit.setLayoutY(40);
        createSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                // clear root from info labels
                root.getChildren().removeAll(warning, added);
                // check data validity
                if (!checkIntValidity(vm.dogAge) || !checkFloatValidity(vm.dogWeight)) {
                    warning = lm.createInvalidFormatWarning();
                    warning.setLayoutX(100);
                    warning.setLayoutY(160);
                    return;
                }
                // if gaps are empty - generate warning
                if (vm.dogName.getText().isEmpty() || vm.dogCharacteristic.getText().isEmpty()
                        || vm.dogBreed.getText().isEmpty() || vm.dogAge.getText().isEmpty()
                        || vm.dogWeight.getText().isEmpty()) {
                    warning = lm.createWarning();
                }
                else {
                    // add dog to database and set success label
                    dbsTools.addDogToDBS();
                    added = lm.createAddingInfo();
                }
            }
        });
        root.getChildren().add(createSubmit);
    }
    
    /**
     * Creates button "Clear" to clear all text fields
     * which were filled with new Dog instance data in Dog
     * CREATE menu.
     */
    protected void createCreateClear() {
        Button createClear = new Button("Clear");
        createClear.setLayoutX(285);
        createClear.setLayoutY(70);
        createClear.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                // clear all filled text fields
                for (TextField tf : vm.textFieldsCreate) {
                    tf.clear();
                }
            }
        });
        root.getChildren().add(createClear);
    }
    
    /**
     * Creates button "Exit" to exit from the Dog CREATE menu
     * back to dog actions menu.
     */
    protected void createCreateExitBtn() {
        Button createExit = new Button("Exit");
        createExit.setLayoutX(290);
        createExit.setLayoutY(100);
        createExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                vm.setDogActionsScene();
            }
        });
        root.getChildren().add(createExit);        
    }
    
    /**
     * Creates button "Exit" to exit from dog actions menu
     * back to the start menu.
     */
    protected void createDogActionsExitBtn() {
        Button dogActionsExitBtn = new Button("Exit");
        dogActionsExitBtn.setLayoutX(180);
        dogActionsExitBtn.setLayoutY(130);
        dogActionsExitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                vm.setInitScene();
            }
        });
        root.getChildren().add(dogActionsExitBtn);
    }
    
    /**
     * Creates button "Exit" to exit from the Dog READ menu
     * back to dog actions menu.
     */
    protected void createReadExitBtn() {
        Button readExit = new Button("Exit");
        readExit.setLayoutX(180);
        readExit.setLayoutY(152);
        readExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                vm.setDogActionsScene();
            }
        });
        root.getChildren().add(readExit);
    }
    
    /**
     * Creates button "Submit" to delete existing dog instance with
     * entered ID from DBS.
     * DBS ACTIONS HERE!
     */
    protected void createDeleteSubmitBtn() {
        Button deleteSubmit = new Button("Submit");
        deleteSubmit.setLayoutX(180);
        deleteSubmit.setLayoutY(80);
        deleteSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                // clear root from info labels
                root.getChildren().removeAll(warning, notFound, deleted);
                // if gap is empty - generate warning
                if (idToDelete.getText().isEmpty()) {
                    warning = lm.createWarning();
                    warning.setLayoutX(155);
                }
                else {
                    // check ID validity
                    if(!checkIntValidity(idToDelete)) {
                        warning = lm.createInvalidFormatWarning();
                        idToDelete.clear();
                        return;
                    }
                    // search for requested dog instance
                    Integer id = Integer.parseInt(idToDelete.getText());
                    Dog dogToDelete = em.find(Dog.class, id);
                    // dog was not found - generate warning
                    if (dogToDelete == null) {
                        idToDelete.clear();
                        notFound = lm.createDogNotFound(id);
                    }
                    // delete dog from DBS
                    else {
                        deleteDogFromDB(dogToDelete);
                    }
                }
            }
        });
        root.getChildren().add(deleteSubmit);
    }
    
    /**
     * Creates button "Exit" to exit from the Dog DELETE menu
     * back to dog actions menu.
     */
    protected void createDeleteExitBtn() {
        Button deleteExit = new Button("Exit");
        deleteExit.setLayoutX(190);
        deleteExit.setLayoutY(110);
        deleteExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                vm.setDogActionsScene();
            }
        });
        root.getChildren().add(deleteExit);
    }
    
    /**
     * Creates button "Submit" to display existing Dog instance
     * to update.
     * DBS ACTIONS HERE!
     */
    protected void createUpdateSubmitBtn() {
        Button updateSubmit = new Button("Submit");
        updateSubmit.setLayoutX(180);
        updateSubmit.setLayoutY(80);
        updateSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                // clear root from info labels
                root.getChildren().removeAll(warning, notFound, updated);
                // if gap is empty - generate warning
                if (idToUpdate.getText().isEmpty()) {
                    warning = lm.createWarning();
                    warning.setLayoutX(155);
                }
                else {
                    // check ID validity
                    if(!checkIntValidity(idToUpdate)) {
                        warning = lm.createInvalidFormatWarning();
                        idToUpdate.clear();
                        return;
                    }
                    // search for requested dog instance
                    Integer id = Integer.parseInt(idToUpdate.getText());
                    Dog dogToUpdate = em.find(Dog.class, id);
                    // dog was not found - generate warning
                    if (dogToUpdate == null) {
                        idToUpdate.clear();
                        notFound = lm.createDogNotFound(id);
                    }
                    else {
                        // display dog's actual data
                        for (int i = 0; i < 5; i++) {
                            createTextFieldsToCreateDog(i, dogToUpdate);
                        }
                        root.getChildren().clear();
                        root.getChildren().addAll(textFieldsUpdate);
                        // create buttons
                        createUpdatableSubmitBtn(dogToUpdate);
                        createUpdatableExitBtn();
                    }
                }
            }
        });
        root.getChildren().add(updateSubmit);
    }
    
    /**
     * Creates button "Exit" to exit from the Dog UPDATE menu
     * back to dog actions menu.
     */
    protected void createUpdateExitBtn() {
        Button updateExit = new Button("Exit");
        updateExit.setLayoutX(190);
        updateExit.setLayoutY(110);
        updateExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                vm.setDogActionsScene();
            }
        });
        root.getChildren().add(updateExit);
    }
    
    /**
     * Creates button "Submit" to submit Dog instance update.
     * DBS ACTIONS HERE!
     * @param dogToUpdate
     */
    protected void createUpdatableSubmitBtn(Dog dogToUpdate) {
        Button updatableSubmit = new Button("Submit");
        updatableSubmit.setLayoutX(280);
        updatableSubmit.setLayoutY(40);
        updatableSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                // clear root from info labels
                root.getChildren().removeAll(warning, added);
                // check data validity
                if (!checkIntValidity(dogAgeUpd) || !checkFloatValidity(dogWeightUpd)) {
                    warning = lm.createInvalidFormatWarning();
                    warning.setLayoutY(160);
                    return;
                }
                // just update dog in DBS and generate success label
                updateDog(dogToUpdate);
                lm.createUpdatingInfo(dogToUpdate.getName());
            }
        });
        root.getChildren().add(updatableSubmit);
    }
    
    /**
     * Creates button "Exit" to exit from the menu with
     * updating Dog instance data.
     */
    protected void createUpdatableExitBtn() {
        Button updatableExit = new Button("Exit");
        updatableExit.setLayoutX(285);
        updatableExit.setLayoutY(70);
        updatableExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                vm.setDogActionsScene();
            }
        });
        root.getChildren().add(updatableExit);        
    }
    
    /**
     * Create buttnon "CREATE" to create new Sponsorship
     * relationship.
     */
    protected void createCreateSponsorBtn() {
        Button createSponsorBtn = new Button("CREATE");
        createSponsorBtn.setLayoutX(80);
        createSponsorBtn.setLayoutY(70);
        createSponsorBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                dogID = new TextField();
                sponsorID = new TextField();
                vm.setSponsorCreateScene(dogID, sponsorID);
            }
        });
        root.getChildren().add(createSponsorBtn);
    }
    
    /**
     * Create buttnon "READ" to read existing Sponsorship
     * relationship.
     */
    protected void createReadSponsorBtn() {
        Button readSponsorBtn = new Button("READ");
        readSponsorBtn.setLayoutX(180);
        readSponsorBtn.setLayoutY(70);
        readSponsorBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                vm.setSponsorReadScene();
            }
        });
        root.getChildren().add(readSponsorBtn);
    }
    
    /**
     * Create buttnon "DELETE" to delete existing Sponsorship
     * relationship.
     */
    protected void createDeleteSponsorBtn() {
        Button deleteSponsorBtn = new Button("DELETE");
        deleteSponsorBtn.setLayoutX(270);
        deleteSponsorBtn.setLayoutY(70);
        deleteSponsorBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                dogID = new TextField();
                sponsorID = new TextField();
                vm.setSponsorDeleteScene(dogID, sponsorID);
            }
        });
        root.getChildren().add(deleteSponsorBtn);
    }
    
    /**
     * Create button "Exit" to exit from the sponsorship actions
     * menu back to the start menu.
     */
    protected void createSponsorActionsExitBtn() {
        Button sponsorActionsExitBtn = new Button("Exit");
        sponsorActionsExitBtn.setLayoutX(185);
        sponsorActionsExitBtn.setLayoutY(130);
        sponsorActionsExitBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                vm.setInitScene();
            }
        });
        root.getChildren().add(sponsorActionsExitBtn);
    }
    
    /**
     * Creates button "Submit" to create new relationship
     * between existing Dog and Sponsor instances.
     * DBS ACTIONS HERE!
     */
    protected void createCreateSponsorSubmitBtn() {
        Button createSponsorSubmit = new Button("Submit");
        createSponsorSubmit.setLayoutX(300);
        createSponsorSubmit.setLayoutY(dogID.getLayoutY()+15);
        createSponsorSubmit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                // clear root from info labels
                root.getChildren().removeAll(warning, added);
                // if gaps are empty - generate warning
                if (dogID.getText().isEmpty() || sponsorID.getText().isEmpty()) {
                    warning = lm.createWarning();
                    warning.setLayoutX(160);
                }
                else {
                    // check ID validity
                    if(!checkIntValidity(dogID) || !checkIntValidity(sponsorID)) {
                        warning = lm.createInvalidFormatWarning();
                        dogID.clear();
                        sponsorID.clear();
                        return;
                    }
                   // search for requested instances
                   Integer dogToSponsor = Integer.parseInt(dogID.getText());
                   Integer sponsorToDog = Integer.parseInt(sponsorID.getText());
                   Dog dog = em.find(Dog.class, dogToSponsor);
                   Sponsor sponsor = em.find(Sponsor.class, sponsorToDog);
                   // entities were not found - generate warning
                   if (dog == null || sponsor == null) {
                       warning = lm.createSponsorshipNotFound();
                   }
                   else {
                       // joining and persisting
                       dog.addSponsor(sponsor);
                       dog.addToDatabase(em);
                       // generate success label and clear IDs text fields
                       added = lm.createSponsorshipAddingInfo(sponsorToDog, dogToSponsor);
                       dogID.clear();
                       sponsorID.clear();
                   }
                }
            }
        });
        root.getChildren().add(createSponsorSubmit);
    }
    
    /**
     * Create button "Exit" to exit from the Sponsorship CREATE
     * menu back to the sponsorship actions menu.
     */
    protected void createCreateSponsorExitBtn() {
        Button createSponsorExit = new Button("Exit");
        createSponsorExit.setLayoutX(310);
        createSponsorExit.setLayoutY(100);
        createSponsorExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                vm.setSponsorshipActionsScene();
            }
        });
        root.getChildren().add(createSponsorExit);        
    }
    
    /**
     * Creates button "DOG" to choose Dog instance which will be
     * done action READ for.
     */
    protected void readDogSponsorshipButton() {
        Button dogSponsorBtn = new Button("DOG");
        dogSponsorBtn.setLayoutX(190);
        dogSponsorBtn.setLayoutY(70);
        dogSponsorBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                dogToRead = new TextField();
                vm.setDogPrereadScene(dogToRead);
            }
        });
        root.getChildren().add(dogSponsorBtn);
    }
    
    /**
     * Creates button "SPONSOR" to choose Sponsor instance which will be
     * done action READ for.
     */
    protected void readSponsorSponsorshipButton() {
        Button sponsorDogBtn = new Button("SPONSOR");
        sponsorDogBtn.setLayoutY(110);
        sponsorDogBtn.setLayoutX(175);
        sponsorDogBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                sponsorToRead = new TextField();
                vm.setSponsorPrereadScene(sponsorToRead);
            }
        });
        root.getChildren().add(sponsorDogBtn);
    }
    
    /**
     * Creates button "Exit" to exit from the Sponsorship READ
     * menu back to the sponsorship actions menu.
     */
    protected void createReadSponsorsExitBtn() {
        Button readSponsorshipExit = new Button("Exit");
        readSponsorshipExit.setLayoutX(195);
        readSponsorshipExit.setLayoutY(150);
        readSponsorshipExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                vm.setSponsorshipActionsScene();
            }
        });
        root.getChildren().add(readSponsorshipExit); 
    }
    
    /**
     * Creates button "Submit" for browsing of a list of all
     * related Sponsor instances for a requested Dog instance.
     * DBS ACTIONS HERE!
     */
    protected void createReadDogsSponsorsSubmit() {
        Button submit = new Button("Submit");
        submit.setLayoutX(185);
        submit.setLayoutY(80);
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                // clear root from info labels
                root.getChildren().remove(warning);
                // if gap is empty - generate warning
                if (dogToRead.getText().isEmpty()) {
                    warning = lm.createWarning();
                    warning.setLayoutX(160);
                    warning.setLayoutY(80);
                }
                else {
                    // check ID validity
                    if(!checkIntValidity(dogToRead)) {
                        warning = lm.createInvalidFormatWarning();
                        dogToRead.clear();
                        return;
                    }
                    // search for requested dog instance
                    Integer dogToReadId = Integer.parseInt(dogToRead.getText());
                    Dog dog = em.find(Dog.class, dogToReadId);
                    // dog was not found - generate warning
                    if (dog == null) {
                        warning = lm.createDogNotFound(dogToReadId);
                    }
                    else {
                        // set subscene displaying all dog's sponsors
                        SponsorshipReadSubscene subscene =
                                new SponsorshipReadSubscene(vm.scene, em, dbsTools, dog);
                        createReadSponsorshipExit();
                        subscene.setLayoutX(20);
                        subscene.setLayoutY(10);
                        root.getChildren().add(subscene);
                    }
                }
            }
        });
        root.getChildren().add(submit); 
    }
    
    /**
     * Creates button "Submit" for browsing of a list of all
     * related Dog instances for a requested Sponsor instance.
     * DBS ACTIONS HERE!
     */
    protected void createReadSponsorsDogsSubmit() {
        Button submit = new Button("Submit");
        submit.setLayoutX(185);
        submit.setLayoutY(80);
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                // clear root from info labels
                root.getChildren().remove(warning);
                // if gap is empty - generate warning
                if (sponsorToRead.getText().isEmpty()) {
                    warning = lm.createWarning();
                    warning.setLayoutX(160);
                    warning.setLayoutY(80);
                }
                else {
                    // check ID validity
                    if(!checkIntValidity(sponsorToRead)) {
                        warning = lm.createInvalidFormatWarning();
                        sponsorToRead.clear();
                        return;
                    }
                    // search for requested sponsor instance
                    Integer sponsorToReadId = Integer.parseInt(sponsorToRead.getText());
                    Sponsor sponsor = em.find(Sponsor.class, sponsorToReadId);
                    // sponsor was not found - generate warning
                    if (sponsor == null) {
                        warning = lm.createSponsorNotFound(sponsorToReadId);
                    }
                    else {
                        // set subscene displaying all sponsor's dogs
                        SponsorshipReadSubscene subscene =
                                new SponsorshipReadSubscene(vm.scene, em, dbsTools, sponsor);
                        createReadSponsorshipExit();
                        subscene.setLayoutX(20);
                        subscene.setLayoutY(10);
                        root.getChildren().add(subscene);
                    }
                }
            }
        });
        root.getChildren().add(submit); 
    }
    
    /**
     * Creates button "Submit" to delete existing relationship
     * between requested Sponsor and Dog instances.
     * DBS ACTIONS HERE!
     */
    protected void createDeleteSponsorSubmit() {
        Button submit = new Button("Submit");
        submit.setLayoutX(300);
        submit.setLayoutY(70);
        submit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                // clear root from info labels
                root.getChildren().removeAll(warning, deleted);
                // if gaps are empty - generate warning
                if (dogID.getText().isEmpty() || sponsorID.getText().isEmpty()) {
                    warning = lm.createWarning();
                    warning.setLayoutX(160);
                }
                else {
                    // check ID validity
                    if(!checkIntValidity(dogID) || !checkIntValidity(sponsorID)) {
                        warning = lm.createInvalidFormatWarning();
                        dogID.clear();
                        sponsorID.clear();
                        return;
                    }
                   // search for requested instances
                   Integer dogToSponsor = Integer.parseInt(dogID.getText());
                   Integer sponsorToDog = Integer.parseInt(sponsorID.getText());
                   Dog dog = em.find(Dog.class, dogToSponsor);
                   Sponsor sponsor = em.find(Sponsor.class, sponsorToDog);
                   // entities were not found - generate warning
                   if (dog == null || sponsor == null) {
                       warning = lm.createSponsorshipNotFound();
                   }
                   else {
                       // break off relations
                       dog.removeSponsor(sponsor);
                       dog.addToDatabase(em);
                       // set success label and clear IDs text fields
                       deleted = lm.createSponsorshipDeletingInfo(sponsorToDog, dogToSponsor);
                       dogID.clear();
                       sponsorID.clear();
                   }
                }
            }
        });
        root.getChildren().add(submit);
    }
    
    /**
     * Creates button "Exit" to exit from the "Enter ID of
     * Sponsor/Dog to READ" scene.
     */
    protected void createPrereadSponsorshipExit() {
        Button prereadExit = new Button("Exit");
        prereadExit.setLayoutX(195);
        prereadExit.setLayoutY(110);
        prereadExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                vm.setSponsorshipActionsScene();
            }
        });
        root.getChildren().add(prereadExit); 
    }
    
    /**
     * Creates button "Exit" to exit from the Sponsorship
     * READ scene.
     */
    protected void createReadSponsorshipExit() {
        Button readExit = new Button("Exit");
        readExit.setLayoutX(195);
        readExit.setLayoutY(150);
        readExit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent t) {
                vm.setSponsorshipActionsScene();
            }
        });
        root.getChildren().add(readExit); 
    }
    
    /**
     * Initialize text fields for new Dog instance data
     * in CREATE operation.
     * @param i - tick in for cycle
     * @param dogToUpdate
     */
    protected void createTextFieldsToCreateDog(int i, Dog dogToUpdate) {
        if (i == 0) {
            textFieldsUpdate.get(i).setLayoutX(90);
            textFieldsUpdate.get(i).setLayoutY(10);
            textFieldsUpdate.get(i).setText(dogToUpdate.getName());
        }
        else {
            textFieldsUpdate.get(i).setLayoutX(textFieldsUpdate.get(i-1).getLayoutX());
            textFieldsUpdate.get(i).setLayoutY(textFieldsUpdate.get(i-1).getLayoutY()+30);
            switch(i) {
                case 1:
                    textFieldsUpdate.get(i).setText(dogToUpdate.getCharacteristic());
                    break;
                case 2:
                    textFieldsUpdate.get(i).setText(dogToUpdate.getBreed());
                    break;
                case 3:
                    textFieldsUpdate.get(i).setText(dogToUpdate.getApproximateAge().toString());
                    break;
                case 4:
                    textFieldsUpdate.get(i).setText(dogToUpdate.getWeight().toString());
                    break;
                default:
                    break;
            }
        }
    }
    
    // DELETE AND UPDATE ACTIONS
    
    // delete dog from DBS with dbsTools and configure view
    // DBS ACTIONS HERE!
    private void deleteDogFromDB(Dog dogToDelete) {
        dbsTools.deleteDogFromDB(dogToDelete);
        // set success label
        deleted = new Label();
        deleted.setText("Dog with ID "+dogToDelete.getId()+" was deleted from database!");
        deleted.setLayoutX(100);
        deleted.setLayoutY(140);
        // clear ID text field
        idToDelete.clear();
        root.getChildren().add(deleted);
    }
    
    // update dog in DBS with dbsTools
    // DBS ACTIONS HERE!
    private void updateDog(Dog dog) {
        // extract data from text fields
        String newName = dogNameUpd.getText();
        String newCharacteristics = dogCharacteristicUpd.getText();
        Integer newAge = Integer.parseInt(dogAgeUpd.getText());
        Float newWeight = Float.parseFloat(dogWeightUpd.getText());
        // update dog in DBS
        dbsTools.updateDog(dog, newName, newCharacteristics, newAge, newWeight);
    }
    
    // JUST DATA VALIDITY CHECK POINTS
    
    // check if text field contains valid numeric id / age
    private boolean checkIntValidity(TextField id) {
        String isNumber = id.getText();
        try {
            Integer idInt = Integer.parseInt(isNumber);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
    
    // check if text field contains valid float weight
    private boolean checkFloatValidity(TextField weight) {
        String isNumber = weight.getText();
        try {
            Float weightFloat = Float.parseFloat(isNumber);
        } catch (NumberFormatException nfe) {
            return false;
        }
        return true;
    }
}