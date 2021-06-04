import java.rmi.RemoteException;

public interface IObserver {
    void produsDeleted(Produs produs) throws RemoteException;
    void produsAdded(Produs produs) throws RemoteException;
    void produsUpdated(Produs produs) throws RemoteException;
    void comandaAdded(Comanda comanda) throws RemoteException;
}
