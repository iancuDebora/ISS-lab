
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * 
 */
@Entity
@Table(name="clienti")
public class Client {

    /**
     * Default constructor
     */
    public Client() {
    }

    /**
     * 
     */
    @Id
    @Column(name="nume")
    public String nume;


    /**
     *
     */
    @OneToMany(mappedBy = "client")
    public Set<Comanda> comenzi;

    public Client(String nume) {
        this.nume = nume;
    }

    public Set<Comanda> getComenzi() {
        return comenzi;
    }

    public void setComenzi(Set<Comanda> comenzi) {
        this.comenzi = comenzi;
    }

    public String getNume() {
        return nume;
    }

    public void setNume(String nume) {
        this.nume = nume;
    }
}