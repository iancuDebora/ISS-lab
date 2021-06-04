import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Properties;

public class StartClientRpc extends Application {


    private static int defaultPort = 55555;
    private static String defaultServer = "localhost";


    public void start(Stage primaryStage) throws Exception {
        System.out.println("In start");
        Properties clientProps = new Properties();
        try {
            clientProps.load(StartClientRpc.class.getResourceAsStream("/client.properties"));
            System.out.println("Client properties set. ");
            clientProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find client.properties " + e);
            return;
        }
        String serverIP = clientProps.getProperty("proiect.server.host", defaultServer);
        int serverPort = defaultPort;

        try {
            serverPort = Integer.parseInt(clientProps.getProperty("proiect.server.port"));
        } catch (NumberFormatException ex) {
            System.err.println("Wrong port number " + ex.getMessage());
            System.out.println("Using default port: " + defaultPort);
        }
        System.out.println("Using server IP " + serverIP);
        System.out.println("Using server port " + serverPort);

        IServices server = new ServicesRpcProxy(serverIP, serverPort);



        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource("LoginWindowAgent.fxml"));
        Parent root=loader.load();



       AutentificareControllerAgent ctrl =
                loader.<AutentificareControllerAgent>getController();
        ctrl.setServer(server);


        FXMLLoader cloader = new FXMLLoader(
                getClass().getClassLoader().getResource("tView.fxml"));
        Parent croot=cloader.load();


        MainControllerAgent mainCtrl =
                cloader.<MainControllerAgent>getController();
       // mainCtrl.setServer(server);

      //  ctrl.setMainController(mainCtrl);



        FXMLLoader cloader2 = new FXMLLoader(
                getClass().getClassLoader().getResource("agentWindow2.fxml"));
        Parent croot2=cloader2.load();


       AgentCtrl2 agentCtrl2 =
                cloader2.<AgentCtrl2>getController();
        mainCtrl.setServer(server);

        ctrl.setControllers(agentCtrl2,mainCtrl);





        agentCtrl2.setParent(croot);
        ctrl.setParent(croot2);

        primaryStage.setTitle("Login Agent");
        primaryStage.setScene(new Scene(root, 300, 230));
        primaryStage.show();




    }


}
