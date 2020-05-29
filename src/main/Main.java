package main;

import graphicalTools.ViewManager;
import java.util.HashMap;
import java.util.Map;
import javafx.application.Application;
import javafx.stage.Stage;

import javax.persistence.*;

/**
 *
 * @author shchesof
 */
public class Main extends Application {
    
    Map<String, Integer> sponsors = new HashMap<>();
    
    @Override
    public void start(Stage stage) {
        EntityManagerFactory emf =
            Persistence.createEntityManagerFactory("PetShelterPU");
        EntityManager em = emf.createEntityManager();
        setUpSponsors(em);
        ViewManager vm = new ViewManager(stage, em);
        stage.setScene(vm.scene);
        stage.show();
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    // put some sponsors in DBS
    private void setUpSponsors(EntityManager em) {
        // map of all sponsors who were already added to database
        sponsors.put("Sofia", 3201);
        sponsors.put("Max", 3202);
        sponsors.put("Henry", 3501);
        sponsors.put("Anna", 3502);
        // print just for visualization
        System.out.println("All sponsors:");
        for (Map.Entry<String, Integer> entry : sponsors.entrySet()) {
            System.out.println(entry.getKey()+" "+entry.getValue());
        }
    }
}