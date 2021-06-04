import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;




public class AgentCtrl2 {


    private AgentVanzari currentAgent;

    private Parent mainParent;
    public void setParent(Parent p){
        mainParent=p;
    }

    public void setCurrentAgent(AgentVanzari currentAgent)
    {
        this.currentAgent = currentAgent;
    }


    public void handleButton(ActionEvent event)
    {
        Stage stage=new Stage();
        stage.setTitle("Window for agent" +currentAgent.getId());
        stage.setScene(new Scene(mainParent));
        stage.show();
        ((Node)(event.getSource())).getScene().getWindow().hide();
    }
}
