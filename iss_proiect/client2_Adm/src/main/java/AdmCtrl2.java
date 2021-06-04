import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class AdmCtrl2 {


    private Administrator currentAdm;

    private Parent mainParent;
    public void setParent(Parent p){
        mainParent=p;
    }

    public void setCurrentAdm(Administrator currentAdm)
    {
        this.currentAdm = currentAdm;
    }


    public void handleButton(ActionEvent event)
    {
        Stage stage=new Stage();
        stage.setTitle("Window for administrator" +currentAdm.getId());
        stage.setScene(new Scene(mainParent));
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}
