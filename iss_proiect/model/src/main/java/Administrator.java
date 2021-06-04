
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

/**
 * 
 */
@Entity
@Table(name="administratori")
public class Administrator {

    /**
     * Default constructor
     */
    public Administrator() {
    }

    /**
     * 
     */
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    private String id;

    /**
     * 
     */
    @Column(name="parola")
    private String parola;

    /**
     * 
     */
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "firma_id")
    public Firma job;

//
//    @OneToMany(mappedBy = "administrator")
//    public Set<Produs> produse;

    public Administrator(String id, String parola) {
        this.id = id;
        this.parola = parola;
    }

    /**
     * @param produs
     */
    public void adaugaProdus(Produs produs) {
        // TODO implement here
    }

    /**
     * @param produs
     */
    public void stergeProdus(Produs produs) {
        // TODO implement here
    }

    /**
     * @param produs
     */
    public void modificaProdus(Produs produs) {
        // TODO implement here
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParola() {
        return parola;
    }

    public void setParola(String parola) {
        this.parola = parola;
    }

    public Firma getJob() {
        return job;
    }

    public void setJob(Firma job) {
        this.job = job;
    }
}