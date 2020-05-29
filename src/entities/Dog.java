package entities;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

/**
 * Dog entity in DBS.
 * @author shchesof
 */
@Entity
public class Dog {
    
    @Id
    @GeneratedValue
    @Column(name="dog_id")
    private Integer id;
    
    @Column(name = "name", nullable=false) 
    private String name;
    
    @Column(name = "characteristic", nullable=false) 
    private String characteristic;
    
    @Column(name = "breed", nullable=false) 
    private String breed;
    
    @Column(name = "approximateAge",nullable=false) 
    private Integer approximateAge;
    
    @Column(name="weight", nullable=false) 
    private Float weight;
   
    // set of all dog's sponsors
    @ManyToMany
    @JoinTable(name="dog_sponsor",
            joinColumns = {@JoinColumn(name="dog_id")},
            inverseJoinColumns = {@JoinColumn(name="sponsor_id")})
    private final Set<Sponsor> sponsors = new HashSet<>();
    
    /**
     * Bind this Dog with given Sponsor.
     * @param sponsor to add relationship with
     */
    public void addSponsor(Sponsor sponsor) {
        sponsors.add(sponsor);
        sponsor.getDogs().add(this);
    }
    
    /**
     * Break up sponsorship between this Dog
     * and given Sponsor.
     * @param sponsor to delete relationship with
     */
    public void removeSponsor(Sponsor sponsor) {
        sponsors.remove(sponsor);
        sponsor.getDogs().remove(this);
    }
    
    /**
     * Set all Dog instance values.
     * @param name
     * @param characteristic
     * @param breed
     * @param approximateAge
     * @param weight
     */
    public void setValues(String name, String characteristic, String breed,
            Integer approximateAge, Float weight) {
        this.name = name;
        this.characteristic = characteristic;
        this.breed = breed;
        this.approximateAge = approximateAge;
        this.weight = weight;
    }
    
    /**
     * Adds this Dog instance to DBS.
     * @param em
     */
    public void addToDatabase(EntityManager em) {
        EntityTransaction et = em.getTransaction();
        et.begin();
        em.persist(this);
        et.commit();
    }

    /**
     * Return's dog's unique ID.
     * @return dog's unique ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * Returns dog's name.
     * @return dog's name
     */
    public String getName() {
        return name;
    }

    /**
     * Returns dog's characteristic.
     * @return dog's characteristic
     */
    public String getCharacteristic() {
        return characteristic;
    }

    /**
     * Returns dog's breed.
     * @return dog's breed
     */
    public String getBreed() {
        return breed;
    }

    /**
     * Returns dog's approximate age.
     * @return dog's approximate age
     */
    public Integer getApproximateAge() {
        return approximateAge;
    }

    /**
     * Returns dog's weight.
     * @return dog's weight
     */
    public Float getWeight() {
        return weight;
    }

    /**
     * Returns set of all dog's sponsors.
     * @return set of all dog's sponsors
     */
    public Set<Sponsor> getSponsors() {
        return sponsors;
    } 
}