
import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * 
 */
@Entity
@Table(name="produse")
public class Produs {

    /**
     * Default constructor
     */
    public Produs() {
    }

    /**
     * 
     */
    @Id
    @Column(name="denumire")
    public String denumire;

    /**
     * 
     */
    @Column(name="pret")
    public Float pret;

    /**
     * 
     */
    @Column(name="cantitateE")
    public Integer cantitateExistenta;


    @ManyToOne
    @JoinColumn(name="adm_id")
    public Administrator administrator;

    @OneToMany(mappedBy = "client")
    public Set<Comanda> comenzi;

    /**
     * 
     */

    public Produs(String denumire, Float pret, Integer cantitateExistenta,Administrator administrator) {
        this.denumire = denumire;
        this.pret = pret;
        this.cantitateExistenta = cantitateExistenta;
        this.administrator = administrator;

    }

    public Administrator getAdministrator() {
        return administrator;
    }

    public void setAdministrator(Administrator administrator) {
        this.administrator = administrator;
    }

    public Set<Comanda> getComenzi() {
        return comenzi;
    }

    public void setComenzi(Set<Comanda> comenzi) {
        this.comenzi = comenzi;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    public Float getPret() {
        return pret;
    }

    public void setPret(Float pret) {
        this.pret = pret;
    }

    public Integer getCantitateExistenta() {
        return cantitateExistenta;
    }

    public void setCantitateExistenta(Integer cantitateExistenta) {
        this.cantitateExistenta = cantitateExistenta;
    }

    @Override
    public String toString() {
        return "Produs{" +
                "denumire='" + denumire + '\'' +
                ", pret=" + pret +
                ", cantitateExistenta=" + cantitateExistenta +
                ", administrator=" + administrator +
                '}';
    }
}