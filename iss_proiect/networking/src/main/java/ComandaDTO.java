import java.io.Serializable;

public class ComandaDTO implements Serializable {
    private int id;
    private ClientDTO client;
    private ProdusDTO produs;
    private AgentVanzariDTO agentVanzari;
    private int cant;


    public ComandaDTO(int id, ClientDTO client, ProdusDTO produs, AgentVanzariDTO agentVanzari, int cant) {
        this.id = id;
        this.client = client;
        this.produs = produs;
        this.agentVanzari = agentVanzari;
        this.cant = cant;
    }

    public ClientDTO getClient() {
        return client;
    }

    public void setClient(ClientDTO client) {
        this.client = client;
    }

    public ProdusDTO getProdus() {
        return produs;
    }

    public void setProdus(ProdusDTO produs) {
        this.produs = produs;
    }

    public AgentVanzariDTO getAgentVanzari() {
        return agentVanzari;
    }

    public void setAgentVanzari(AgentVanzariDTO agentVanzari) {
        this.agentVanzari = agentVanzari;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }

    @Override
    public String toString() {
        return "ComandaDTO{" +
                "id=" + id +
                ", cant=" + cant +
                '}';
    }
}