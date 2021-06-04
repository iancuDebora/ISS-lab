import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;

import java.rmi.RemoteException;

public class MainControllerAdministrator implements IObserver {

    private IServices server;
    private Administrator administrator;


    private ObservableList<Produs> modelProduse = FXCollections.observableArrayList();



    @FXML
    private TableView<Produs> produseTableView;

    @FXML
    private TableColumn<Produs, String> denumireColumn;

    @FXML
    private TableColumn<Produs, Float> pretColumn;

    @FXML
    private TableColumn<Produs, Integer> cantitateEColumn;


    @FXML
    private TextField textFieldDenumire;

    @FXML
    private TextField textFieldPret;

    @FXML
    private TextField textFieldCantitate;

    public void setServer(IServices s) {
        server = s;
    }

    public void setAdm(Administrator adm)
    {
        this.administrator = adm;
        initModel();
        initialize();
    }

    private void initialize()
    {
        denumireColumn.setCellValueFactory(new PropertyValueFactory<Produs, String>("Denumire"));
        pretColumn.setCellValueFactory(new PropertyValueFactory<Produs, Float>("Pret"));
        cantitateEColumn.setCellValueFactory(new PropertyValueFactory<Produs, Integer>("CantitateExistenta"));
        produseTableView.setItems(modelProduse);
    }


    private void initModel() {

        Produs[] produse = null;
        try {
            produse = server.getProduse();
        } catch (MyException e) {
            e.printStackTrace();
        }

        modelProduse.setAll(produse);
    }

    @FXML
    void handleSelection(MouseEvent event) {
        Produs produs = produseTableView.getSelectionModel().getSelectedItem();

        if (produs != null) {
            textFieldDenumire.setText(produs.getDenumire());
            textFieldPret.setText(produs.getPret().toString());
            textFieldCantitate.setText(produs.getCantitateExistenta().toString());
        }

    }


    @FXML
    private void handleDelete()
    {
        Produs produs = produseTableView.getSelectionModel().getSelectedItem();
        server.deleteProdus(produs);
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

    @Override
    public void comandaAdded(Comanda comanda) throws RemoteException {
        for (int idx = 0; idx < produseTableView.getItems().size(); idx++) {
            Produs caz0 = produseTableView.getItems().get(idx);
            if (caz0.getDenumire().equals(comanda.getProdus().getDenumire())) {
                produseTableView.getItems().set(idx, comanda.getProdus());
                return;
            }
        }
        System.out.println("produs modificat " + comanda.getProdus().toString());
    }

    public void handleAdd()
    {
        String denumire = textFieldDenumire.getText().toString();
        Float pret= Float.parseFloat(textFieldPret.getText());
        Integer cant = Integer.parseInt(textFieldCantitate.getText());
        Produs produs = new Produs(denumire,pret,cant,administrator);
        System.out.println("first name try");
        System.out.println(produs.getDenumire());
        System.out.println(produs.getAdministrator());
        server.addProdus(produs);
    }

    public void handleUpdate()
    {
        String denumire = textFieldDenumire.getText().toString();
        Float pret= Float.parseFloat(textFieldPret.getText());
        Integer cant = Integer.parseInt(textFieldCantitate.getText());
        Produs produs = new Produs(denumire,pret,cant,administrator);
        server.updateProdus(produs);
    }


}
