import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AutentificareControllerAgent {

    private IServices server;

    private AgentVanzari curentAgent;

    private AgentCtrl2 agentCtrl2;

    private MainControllerAgent mainControllerAgent;

    @FXML
    private TextField idTextField;


    @FXML
    private TextField parolaTextField;

    Parent mainParent;

    public void setParent(Parent p){
        mainParent=p;
    }

    public void setServer(IServices server)
    {
        this.server=server;
    }

    public void setControllers(AgentCtrl2 agentCtrl2, MainControllerAgent mainControllerAgent)
    {
        this.agentCtrl2 = agentCtrl2;
        this.mainControllerAgent = mainControllerAgent;
    }
    @FXML
    void handleLogIn(ActionEvent event) {
         String id = idTextField.getText();

        String parola = parolaTextField.getText();


        curentAgent = new AgentVanzari(id, parola);


        try{
            server.login(curentAgent, mainControllerAgent);

            Stage stage=new Stage();
            stage.setTitle("Window for " +curentAgent.getId());
            stage.setScene(new Scene(mainParent));

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {

                    System.exit(0);
                }
            });

            stage.show();
            agentCtrl2.setCurrentAgent(curentAgent);
            mainControllerAgent.setAgentVanzari(curentAgent);
            ((Node)(event.getSource())).getScene().getWindow().hide();

        }   catch (MyException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("AGENT VANZARI");
            alert.setHeaderText("Autentificarea a esuat");
            alert.setContentText("Parola sau id-ul sunt gresite");
            alert.showAndWait();
        }
    }


}
