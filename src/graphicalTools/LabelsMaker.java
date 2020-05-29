package graphicalTools;

import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * Auxiliary class to create and set different necessary labels.
 * No DBS actions here.
 * @author shchesof
 */
public class LabelsMaker {
    private final AnchorPane root;
    private final ViewManager vm;

    public LabelsMaker(AnchorPane root, ViewManager vm) {
        this.root = root;
        this.vm = vm;
    }
    
    /**
     * Creates and sets warning "Please fill all gaps!".
     * @return
     */
    protected Label createWarning() {
        Label warning = new Label();
        warning.setText("Please fill all gaps!");
        warning.setLayoutX(120);
        warning.setLayoutY(155);
        root.getChildren().add(warning);
        return warning;
    }
    
    /**
     * Creates and sets label informing about successful adding of Dog
     * instance to DBS.
     * @return
     */
    protected Label createAddingInfo() {
        Label added = new Label();
        added.setText("Dog named "+vm.dogName.getText()+" was added to database!");
        for (TextField tf : vm.textFieldsCreate) {
            tf.clear();
        }
        added.setLayoutX(vm.dogName.getLayoutX());
        added.setLayoutY(vm.dogWeight.getLayoutY()+30);
        root.getChildren().add(added);
        return added;
    }
    
    /**
     * Creates and sets label informing about successful updating of Dog
     * instance in DBS.
     * @param name of dog which was successfully updated
     * @return
     */
    protected Label createUpdatingInfo(String name) {
        Label updated = new Label();
        updated.setText("Dog named "+name+" was updated!");
        updated.setLayoutX(110);
        updated.setLayoutY(160);
        root.getChildren().add(updated);
        return updated;
    }
    
    /**
     * Creates and sets warning "Dog given_id wasn't found!".
     * @param id of Dog instance which was not found
     * @return
     */
    public Label createDogNotFound(Integer id) {
        Label notFound = new Label();
        notFound.setText("Dog with ID "+id+" wasn't found!");
        notFound.setLayoutX(130);
        notFound.setLayoutY(140);
        root.getChildren().add(notFound);
        return notFound;
    }
    
    /**
     * Creates and sets warning "Sponsor given_id wasn't found!".
     * @param id of Sponsor instance which was not found
     * @return
     */
    protected Label createSponsorNotFound(Integer id) {
        Label notFound = new Label();
        notFound.setText("Sponsor with ID "+id+" wasn't found!");
        notFound.setLayoutX(130);
        notFound.setLayoutY(80);
        root.getChildren().add(notFound);
        return notFound;
    }
    
    /**
     * Creates and sets warning "Wrong IDs!".
     * @return
     */
    protected Label createSponsorshipNotFound() {
        Label sponsorshipNotFound = new Label();
        sponsorshipNotFound.setText("Wrong IDs!");
        sponsorshipNotFound.setLayoutX(170);
        sponsorshipNotFound.setLayoutY(140);
        root.getChildren().add(sponsorshipNotFound);
        return sponsorshipNotFound;
    }
    
    /**
     * Creates and sets label informing about successful creation
     * of new sponsorship between existing Dog and Sponsor instances.
     * @param sId sponsor's ID
     * @param dId dog's ID
     * @return
     */
    protected Label createSponsorshipAddingInfo(Integer sId, Integer dId) {
        Label sponsorshipAdded = new Label();
        sponsorshipAdded.setText("Dog "+dId+" + sponsor "+sId+" is created!");
        sponsorshipAdded.setLayoutX(110);
        sponsorshipAdded.setLayoutY(140);
        root.getChildren().add(sponsorshipAdded);
        return sponsorshipAdded;
    }
    
    /**
     * Creates and sets label informing about successful deletion
     * of existing sponsorship between Dog and Sponsor instances.
     * @param sId sponsor's ID
     * @param dId dog's ID
     * @return
     */
    protected Label createSponsorshipDeletingInfo(Integer sId, Integer dId) {
        Label deleted = new Label();
        deleted.setText("Dog "+dId+" + sponsor "+sId+" was deleted!");
        deleted.setLayoutX(110);
        deleted.setLayoutY(140);
        root.getChildren().add(deleted);
        return deleted;
    }
    
    /**
     * Creates and sets label informing about successful deletion of Dog
     * instance from DBS.
     * @param id of dog which was successfully deleted from DBS
     * @return
     */
    protected Label createDogDeletingInfo(Integer id) {
        Label deleted = new Label();
        deleted.setText("Dog with ID "+id+" was deleted from database!");
        deleted.setLayoutX(100);
        deleted.setLayoutY(140);
        return deleted;
    }
    
    /**
     * Creates and sets warning "ID/age/weight is not valid!".
     * @return
     */
    protected Label createInvalidFormatWarning() {
        Label invalidID = new Label();
        invalidID.setText("ID/age/weight is not valid!");
        invalidID.setLayoutX(130);
        invalidID.setLayoutY(140);
        root.getChildren().add(invalidID);
        return invalidID;
    }
}