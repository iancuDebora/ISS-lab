import java.io.Serializable;

public class ProdusDTO implements Serializable {
    private String denumire;
    private Float pret;
    private Integer cantitateExistenta;
    private AdministratorDTO administrator;

    public ProdusDTO(String denumire, Float pret, Integer cantitateExistenta, AdministratorDTO administrator) {
        this.denumire = denumire;
        this.pret = pret;
        this.cantitateExistenta = cantitateExistenta;
        this.administrator = administrator;
    }

    public AdministratorDTO getAdministrator() {
        return administrator;
    }

    public void setAdministrator(AdministratorDTO administrator) {
        this.administrator = administrator;
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
        return "ProdusDTO{" +
                "denumire='" + denumire + '\'' +
                ", pret=" + pret +
                ", cantitateExistenta=" + cantitateExistenta +
                '}';
    }
}