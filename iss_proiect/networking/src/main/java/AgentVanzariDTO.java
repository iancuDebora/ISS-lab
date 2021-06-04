import java.io.Serializable;

public class AgentVanzariDTO implements Serializable {
    private String id;
    private String parola;

    public AgentVanzariDTO(String id) {
        this(id,"");
    }

    public AgentVanzariDTO(String id, String passwd) {
        this.id = id;
        this.parola = passwd;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPasswd() {
        return parola;
    }

    @Override
    public String toString(){
        return "AgentVanzariDTO["+id+' '+parola+"]";
    }
}