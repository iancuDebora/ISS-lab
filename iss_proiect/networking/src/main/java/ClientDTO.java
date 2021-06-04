import java.io.Serializable;

public class ClientDTO implements Serializable {
    private String denumire;


    public ClientDTO(String denumire) {
        this.denumire = denumire;
    }

    public String getDenumire() {
        return denumire;
    }

    public void setDenumire(String denumire) {
        this.denumire = denumire;
    }

    @Override
    public String toString() {
        return "ClientDTO{" +
                "denumire='" + denumire + '\'' +
                '}';
    }
}