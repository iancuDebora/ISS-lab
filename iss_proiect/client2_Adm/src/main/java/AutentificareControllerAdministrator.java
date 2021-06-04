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

public class AutentificareControllerAdministrator {

    private IServices server;

    private Administrator curentAdministrator;

    private AdmCtrl2 admCtrl2;

    private MainControllerAdministrator mainController;

    @FXML
    private TextField idTextField;

    @FXML
    private TextField parolaTextField;

    Parent mainParent;

    public AutentificareControllerAdministrator() {
    }

    public AutentificareControllerAdministrator(IServices server) {
        this.server = server;
    }

    public void setParent(Parent p){
        mainParent=p;
    }

    public void setServer(IServices server)
    {
        this.server=server;
    }

    public void setControllers(AdmCtrl2 admCtrl2,MainControllerAdministrator mainController)
    {
        this.admCtrl2 = admCtrl2;
        this.mainController = mainController;
    }
    @FXML
    void handleLogIn(ActionEvent event) {
        String id = idTextField.getText();

        String parola = parolaTextField.getText();


        curentAdministrator = new Administrator(id, parola);
        System.out.println("ADM ID");
        System.out.println(curentAdministrator.getId());


        try{
            server.loginAdm(curentAdministrator, mainController);

            Stage stage=new Stage();
            stage.setTitle("Window for administrator " +curentAdministrator.getId());
            stage.setScene(new Scene(mainParent));

            stage.setOnCloseRequest(new EventHandler<WindowEvent>() {
                @Override
                public void handle(WindowEvent event) {

                    System.exit(0);
                }
            });

            stage.show();
            admCtrl2.setCurrentAdm(curentAdministrator);
            mainController.setAdm(curentAdministrator);
            ((Node)(event.getSource())).getScene().getWindow().hide();

        }   catch (MyException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("AGENTIE");
            alert.setHeaderText("Autentificarea a esuat");
            alert.setContentText("Parola sau id-ul sunt gresite");
            alert.showAndWait();
        }
    }

    public void handleWindow()
    {/*
        Service service =
                new ClassPathXmlApplicationContext("proiect_mpp_1App.xml")
                        .getBean(Service.class);

        Stage newStage = new Stage();
        newStage.setTitle("Teledon");
        FXMLLoader loader = new FXMLLoader(getClass().getResource("../views/tView.fxml"));
        AnchorPane mainPane = null;
        try {
            mainPane = (AnchorPane) loader.load();
            TController controller = loader.getController();
            controller.setService(service);
            Scene newScene = new Scene(mainPane);
            newStage.setScene(newScene);
            numeTextField.getScene().getWindow().hide();
            newStage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
*/
    }

}
