
import javax.persistence.*;
import java.util.*;

/**
 * 
 */
@Entity
@Table(name="firme")
public class Firma {

    /**
     * Default constructor
     */
    public Firma() {
    }

    /**
     * 
     */

    @Id
    @Column(name="denumire")
    private String denumire;


    @OneToMany(mappedBy = "job")
    public Set<AgentVanzari> agenti;

    /**
     *
     */
    @OneToOne(mappedBy = "job", cascade = CascadeType.ALL,
            fetch = FetchType.LAZY, optional = false)
    public Administrator administrator;

    public Firma(String denumire) {
        this.denumire = denumire;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public Set<AgentVanzari> getAgenti() {
        return agenti;
    }

    public void setAgenti(Set<AgentVanzari> agenti) {
        this.agenti = agenti;
    }

   /* public Administrator getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }*/
}