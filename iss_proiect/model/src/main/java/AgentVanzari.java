
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

/**
 * 
 */
@Entity
@Table(name="agenti")
public class AgentVanzari {

    /**
     * Default constructor
     */
    public AgentVanzari() {
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

    @ManyToOne
    @JoinColumn(name="firma_id",nullable = false)
    private Firma job;

    /**
     * 
     */
    @OneToMany(mappedBy = "agentVanzari")
   public Set<Comanda> comenzi;

    public AgentVanzari(String id, String parola) {
        this.id = id;
        this.parola = parola;
      //  comenzi = new HashSet<Comanda>();
    }

    /**
     * @param comanda
     */
    public void comandaProdus(Comanda comanda ) {
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

  /*   public Set<Comanda> getComenzi() {
        return comenzi;
    }

    public void setComenzi(Set<Comanda> comenzi) {
        this.comenzi = comenzi;
    }

    public void addComenzi(Comanda comanda)
    //pre: autovehicul != null
    {
        this.basicAddToComenzi(comanda); //this.autoConduse.add(autovehicul);
       // comenzi.basicAddToComenzi(this);
    }

    public void removeFromComenzi(Comanda comanda)
    //Pre: autovehicul != null
    {
        this.basicRemoveFromComenzi(comanda); //this.autoConduse.remove(autovehicul);

    }
   void basicAddToComenzi(Comanda comanda)
    {
        this.comenzi.add(comanda);
    }

    void basicRemoveFromComenzi(Comanda comanda)
    {
        this.comenzi.remove(comanda);
    }*/
}