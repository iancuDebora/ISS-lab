
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.*;

/**
 * 
 */
@Entity
@Table(name="comenzi")
public class Comanda {

    /**
     * Default constructor
     */
    public Comanda() {
    }

    /**
     * 
     */
    @Id
    @GeneratedValue(generator="increment")
    @GenericGenerator(name="increment", strategy = "increment")
    public Integer id;

    /**
     * 
     */
    @ManyToOne
    @JoinColumn(name="client_id",nullable = false)
    public Client client;


    @ManyToOne
    @JoinColumn(name="produs_id",nullable = false)
    public Produs produs;
    /**
     *
     */
    @ManyToOne
    @JoinColumn(name="agent_id",nullable = false)
    public AgentVanzari agentVanzari;


    @Column(name="cantitate")
    private Integer cantitate;

    public Comanda(Integer id, Client client, Produs produs, AgentVanzari agentVanzari,Integer cantitate) {
        this.id = id;
        this.client = client;
        this.produs = produs;
        this.agentVanzari = agentVanzari;
        this.cantitate = cantitate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Produs getProdus() {
        return produs;
    }

    public void setProdus(Produs produs) {
        this.produs = produs;
    }

    public AgentVanzari getAgentVanzari() {
        return agentVanzari;
    }

    public void setAgentVanzari(AgentVanzari agentVanzari) {
        this.agentVanzari = agentVanzari;
    }

    public Integer getCantitate() {
        return cantitate;
    }

    public void setCantitate(Integer cantitate) {
        this.cantitate = cantitate;
    }
}