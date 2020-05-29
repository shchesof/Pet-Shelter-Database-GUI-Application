package entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * Sponsor entity in DBS.
 * @author shchesof
 */
@Entity
public class Sponsor {

    @Id
    @GeneratedValue
    @Column(name="sponsor_id")
    private Integer id;
    
    @Column(name = "telephone", nullable = false)
    private String telephone;
    
    @Column(name = "company")
    private String company;
    
    @Column(name = "name", nullable = false)
    private String name;
    
    @Column(name = "address", nullable = false)
    private String address;
    
    @ManyToMany(mappedBy = "sponsors")
    private final Set<Dog> dogs = new HashSet<>();

    /**
     * Return's set of all dogs sponsored by this sponsor.
     * @return set of all dogs sponsored by this sponsor
     */
    public Set<Dog> getDogs() {
        return dogs;
    }
    
    /**
     * Set all Sponsor instance values.
     * @param name
     * @param telephone
     * @param company
     * @param address
     */
    public void setValues(String name, String telephone,
            String company, String address) {
        this.name = name;
        this.telephone = telephone;
        this.company = company;
        this.address = address;
    }
    
    /**
     * Adds this Sponsor instance to DBS.
     * @param em
     */
    public void addToDatabase(EntityManager em) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(this);
        et.commit();
    }

    /**
     * Bind this Sponsor with given Dog.
     * @param dog to add relationship with
     */
    public void addDog(Dog dog) {
        dogs.add(dog);
        dog.getSponsors().add(this);
    }
    
    /**
     * Break up sponsorship between this Sponsor
     * and given Dog.
     * @param dog to delete relationship with
     */
    public void removeDog(Dog dog) {
        dogs.remove(dog);
        dog.getSponsors().remove(this);
    }

    /**
     * Returns sponsor's unique ID.
     * @return sponsor's unique ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * Returns sponsor's telephone number.
     * @return sponsor's telephone number
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * Returns company sponsor works in.
     * @return company sponsor works in
     */
    public String getCompany() {
        return company;
    }

    /**
     * Returns sponsor's name.
     * @return sponsor's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns sponsor's address.
     * @return sponsor's address
     */
    public String getAddress() {
        return address;
    }
}