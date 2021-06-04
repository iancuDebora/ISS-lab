import java.io.Serializable;

public class AdministratorDTO implements Serializable {
    private String id;
    private String parola;

    public AdministratorDTO(String id) {
        this(id,"");
    }

    public AdministratorDTO(String id, String passwd) {
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
        return "AdministratorDTO["+id+' '+parola+"]";
    }
}