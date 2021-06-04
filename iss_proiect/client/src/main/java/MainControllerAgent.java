import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.rmi.RemoteException;
import java.util.Random;

public class MainControllerAgent implements IObserver {

    private IServices server;
    private AgentVanzari agentVanzari;


    private ObservableList<Produs> modelProduse = FXCollections.observableArrayList();
    private ObservableList<Client> modelClienti = FXCollections.observableArrayList();


    @FXML
    private TableView<Produs> produseTableView;

    @FXML
    private TableView<Client> clientiTableView;

    @FXML
    private TableColumn<Produs, String> denumireColumn;

    @FXML
    private TableColumn<Produs, Float> pretColumn;

    @FXML
    private TableColumn<Produs, Integer> cantitateEColumn;

    @FXML
    private TableColumn<Produs, Administrator> admColumn;

    @FXML
    private TableColumn<Client, String> columnNumeClient;

    @FXML
    private TextField textFieldCantitate;


    public void setServer(IServices s) {
        server = s;
    }

    public void setAgentVanzari(AgentVanzari agentVanzari)
    {
        this.agentVanzari = agentVanzari;
        initModel();
        initialize();
    }

    private void initialize()
    {
        denumireColumn.setCellValueFactory(new PropertyValueFactory<Produs, String>("Denumire"));
        pretColumn.setCellValueFactory(new PropertyValueFactory<Produs, Float>("Pret"));
        cantitateEColumn.setCellValueFactory(new PropertyValueFactory<Produs, Integer>("CantitateExistenta"));
        admColumn.setCellValueFactory(new PropertyValueFactory<Produs, Administrator>("Administrator"));
        produseTableView.setItems(modelProduse);

        columnNumeClient.setCellValueFactory(new PropertyValueFactory<Client, String>("Nume"));
        clientiTableView.setItems(modelClienti);
    }


    private void initModel() {

        Produs[] produse = null;
        try {
            produse = server.getProduse();
            System.out.println("TRUTH");
            for(Produs produs:produse)
            {
                System.out.println(produs.getAdministrator());
            }
        } catch (MyException e) {
            e.printStackTrace();
        }

        modelProduse.setAll(produse);

        Client[] clienti = null;
        try {
            clienti = server.getClienti();
        } catch (MyException e) {
            e.printStackTrace();
        }

        modelClienti.setAll(clienti);

    }

    @Override
    public void produsDeleted(Produs produs) throws RemoteException {
        for (int idx = 0; idx <produseTableView.getItems().size(); idx++) {
            Produs produs0 = produseTableView.getItems().get(idx);
            if (produs0.getDenumire().equals(produs.getDenumire())) {
                produseTableView.getItems().remove(idx);
                return;
            }
        }
        System.out.println("produs deleted " + produs.toString());
    }

    @Override
    public void produsAdded(Produs produs) throws RemoteException {
        produseTableView.getItems().add(produs);
        System.out.println("Produs adaugat " + produs.getDenumire());
    }

    @Override
    public void produsUpdated(Produs produs) throws RemoteException {

        for (int idx = 0; idx < produseTableView.getItems().size(); idx++) {
            Produs produs0 = produseTableView.getItems().get(idx);
            if (produs0.getDenumire().equals(produs.getDenumire())) {
                produseTableView.getItems().set(idx, produs);

                return;
            }
        }
        System.out.println("produs modificat " + produs.toString());
    }

    public void handleComanda()
    {
        Produs produs = produseTableView.getSelectionModel().getSelectedItem();
        System.out.println("PRODUS DE COMANDAT");
        System.out.println(produs.getAdministrator());
        Client client = clientiTableView.getSelectionModel().getSelectedItem();
        int cant = Integer.parseInt(textFieldCantitate.getText());
        int cantVeche = produs.getCantitateExistenta();
        Comanda comanda = new Comanda(new Random().nextInt(1000000000),client,produs,agentVanzari,cant);
        comanda.setAgentVanzari(agentVanzari);
        comanda.setClient(client);
        comanda.setProdus(produs);
      //  server.addComanda(comanda);
        int cant1 = cantVeche - cant;
        Produs produs1 = new Produs(produs.getDenumire(),produs.getPret(),cant1, produs.getAdministrator());
        server.updateProdus(produs1);
     //   server.addComanda(comanda);
    }

    @Override
    public void comandaAdded(Comanda comanda) throws RemoteException {
        for (int idx = 0; idx < produseTableView.getItems().size(); idx++) {
            Produs produs0 = produseTableView.getItems().get(idx);
            if (produs0.getDenumire().equals(comanda.getProdus().getDenumire())) {
                produseTableView.getItems().set(idx, comanda.getProdus());
                return;
            }
        }
        System.out.println("produs modificat " + comanda.getProdus().toString());
    }
}
