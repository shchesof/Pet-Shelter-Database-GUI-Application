package dbsTools;

import entities.*;
import graphicalTools.ViewManager;
import java.util.List;
import java.util.Set;
import javax.persistence.*;

/**
 * Auxiliary class to perform main DBS operations on instances.
 * DBS ACTIONS HERE!
 * @author shchesof
 */
public class DBSTools {
    
    private final EntityManager em;
    private final ViewManager vm;

    public DBSTools(EntityManager em, ViewManager vm) {
        this.em = em;
        this.vm = vm;
    }

    /**
     * Extracts data of new Dog instance from text fields filled
     * in View Manager and create corresponding instance.
     */
    public void addDogToDBS() {
        // read values to set
        String name = vm.dogName.getText();
        String characteristic = vm.dogCharacteristic.getText();
        String breed = vm.dogBreed.getText();
        Integer approximateAge = Integer.parseInt(vm.dogAge.getText());
        Float weight = Float.parseFloat(vm.dogWeight.getText());
        // create and add dog to DBS
        Dog dog = new Dog();
        dog.setValues(name, characteristic, breed, approximateAge, weight);
        dog.addToDatabase(em);
    }
    
    /**
     * Breaks up sponsorship between given Dog instance and all related
     * Sponsor instances. Deletes given Dog instance from DBS.
     * @param dogToDelete
     */
    public void deleteDogFromDB(Dog dogToDelete) {
        em.getTransaction().begin();
        // break up all existing relationships
        for (Sponsor s : dogToDelete.getSponsors()) {
            s.removeDog(dogToDelete);
        }
        em.remove(dogToDelete);
        em.getTransaction().commit();
    }
    
    /**
     * Updates given Dog instance in DBS.
     * The breed remains the same, it's unupdatable.
     * @param dog - dog to update
     * @param name - new name
     * @param characteristics - new characteristics
     * @param age - new age
     * @param weight - new weight
     */
    public void updateDog(Dog dog, String name, String characteristics, Integer age, Float weight) {
        em.getTransaction().begin();
        dog.setValues(name, characteristics, dog.getBreed(), age, weight);
        em.getTransaction().commit();
    }
    
    /**
     * Browsing of a list of all existing Dog instances.
     * @return String containing information about all Dog instances in DBS
     */
    public String readAll() {
        String result = "";
        TypedQuery<Dog> q1 = em.createQuery(
            "SELECT d FROM Dog AS d",
            Dog.class
        );
        List<Dog> l = q1.getResultList();
        for (Dog d : l) {
            result += "Id: "+d.getId() + " | name: "+d.getName()
                    + " | characteristic: "+d.getCharacteristic()+" | breed: "
                    +d.getBreed() +" | approximate age: "+d.getApproximateAge()+
                    " | weight: "+ d.getWeight()+"\n\n";
        }
        return result;
    }
    
    /**
     * Browsing of a list of all existing Dog instances related
     * to given Sponsor instance.
     * @param sponsor - to read all dogs he or she sponsors
     * @return String containing information about all Dog instances in DBS
     * related to given Sponsor instance
     */
    public String readAllDogs(Sponsor sponsor) {
        Set<Dog> dogs = sponsor.getDogs();
        String result = "";
        result += "All dogs sponsored by " + sponsor.getId()+"\n";
        for (Dog d : dogs) {
            result += d.getId()+" ";
        }
        return result;
    }
    
    /**
     * Browsing of a list of all existing Sponsor instances related
     * to given Dog instance.
     * @param dog - to read all sponsors it is sponsored by
     * @return String containing information about all Sponsor instances in DBS
     * related to given Dog instance
     */
    public String readAllSponsors(Dog dog) {
        Set<Sponsor> sponsors = dog.getSponsors();
        String result = "";
        result += "All sponsors of dog " + dog.getId()+"\n";
        for (Sponsor s : sponsors) {
            result += s.getId()+" ";
        }
        return result;
    }
}