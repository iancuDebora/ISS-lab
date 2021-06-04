import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ServicesRpcProxy implements IServices {
    private String host;
    private int port;

    private IObserver client;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private Socket connection;

    private BlockingQueue<Response> qresponses;
    private volatile boolean finished;
    public ServicesRpcProxy(String host, int port) {
        this.host = host;
        this.port = port;
        qresponses=new LinkedBlockingQueue<Response>();
    }

    public void login(AgentVanzari agentVanzari, IObserver client) throws MyException {
        initializeConnection();
        AgentVanzariDTO dto= DTOUtils.getDTO(agentVanzari);
        Request req=new Request.Builder().type(RequestType.LOGIN).data(dto).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.OK){
            this.client=client;
            return;
        }
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            closeConnection();
            throw new MyException(err);
        }
    }

    public void loginAdm(Administrator adm, IObserver client) throws MyException {
        initializeConnection();
        AdministratorDTO dto= DTOUtils.getDTO(adm);
        Request req=new Request.Builder().type(RequestType.LOGIN_ADM).data(dto).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.OK){
            this.client=client;
            return;
        }
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            closeConnection();
            throw new MyException(err);
        }
    }

  public Produs[] getProduse() throws MyException
    {
        Request req=new Request.Builder().type(RequestType.PRODUSE).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new MyException(err);
        }
        ProdusDTO[] prdto = (ProdusDTO[])response.data();
        Produs[] produse= DTOUtils.getFromDTO(prdto);
        return produse;
    }

    public Client[] getClienti() throws MyException
    {
        Request req=new Request.Builder().type(RequestType.CLIENTI).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new MyException(err);
        }
        ClientDTO[] cldto = (ClientDTO[])response.data();
        Client[] clienti= DTOUtils.getFromDTO(cldto);
        return clienti;
    }

    @Override
    public void deleteProdus(Produs produs) throws MyException {
        ProdusDTO dto = DTOUtils.getDTO(produs);
        Request req=new Request.Builder().type(RequestType.DELETE_PRODUS).data(dto).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new MyException(err);
        }

    }

    @Override
    public void addProdus(Produs produs) throws MyException {
        ProdusDTO dto = DTOUtils.getDTO(produs);
        Request req=new Request.Builder().type(RequestType.ADD_PRODUS).data(dto).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new MyException(err);
        }
    }

    @Override
    public void updateProdus(Produs produs) throws MyException {
        ProdusDTO dto = DTOUtils.getDTO(produs);
        Request req=new Request.Builder().type(RequestType.UPDATE_PRODUS).data(dto).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new MyException(err);
        }
    }

    @Override
    public void addComanda(Comanda comanda) throws MyException {
        ComandaDTO dto = DTOUtils.getDTO(comanda);
        Request req=new Request.Builder().type(RequestType.ADD_COMANDA).data(dto).build();
        sendRequest(req);
        Response response=readResponse();
        if (response.type()== ResponseType.ERROR){
            String err=response.data().toString();
            throw new MyException(err);
        }
    }

    private void closeConnection() {
        finished=true;
        try {
            input.close();
            output.close();
            connection.close();
            client=null;
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void sendRequest(Request request)throws MyException {
        try {
            output.writeObject(request);
            output.flush();
        } catch (IOException e) {
            throw new MyException("Error sending object "+e);
        }

    }

    private Response readResponse(){
        Response response=null;
        try{

            response=qresponses.take();

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return response;
    }
    private void initializeConnection(){
        try {
            connection=new Socket(host,port);
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            finished=false;
            startReader();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void startReader(){
        Thread tw=new Thread(new ReaderThread());
        tw.start();
    }


    private void handleUpdate(Response response){
       if (response.type()== ResponseType.DELETED_PRODUS){

            Produs produs = DTOUtils.getFromDTO((ProdusDTO) response.data());
            System.out.println("produs deleted "+ produs);
            try {
                client.produsDeleted(produs);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
        if (response.type()== ResponseType.NEW_PRODUS){
            Produs produs = DTOUtils.getFromDTO((ProdusDTO) response.data());
            System.out.println("produs added "+ produs);
            try {
                client.produsAdded(produs);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        if (response.type()== ResponseType.UPDATED_PRODUS){
            Produs produs = DTOUtils.getFromDTO((ProdusDTO) response.data());
            System.out.println("produs updated "+ produs);
            try {
                client.produsUpdated(produs);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        if (response.type()== ResponseType.NEW_COMANDA){
            Comanda comanda = DTOUtils.getFromDTO((ComandaDTO) response.data());
            System.out.println("comanda added "+ comanda);
            try {
                client.comandaAdded(comanda);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private boolean isUpdate(Response response) {
        return response.type() == ResponseType.DELETED_PRODUS || response.type() == ResponseType.NEW_PRODUS
    || response.type() == ResponseType.UPDATED_PRODUS || response.type() == ResponseType.NEW_COMANDA;
    }
    private class ReaderThread implements Runnable{
        public void run() {
            while(!finished){
                try {
                    Object response=input.readObject();
                    System.out.println("response received "+response);
                    if (isUpdate((Response)response)){
                        handleUpdate((Response)response);
                    }else{

                        try {
                            qresponses.put((Response)response);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                } catch (IOException e) {
                    System.out.println("Reading error "+e);
                } catch (ClassNotFoundException e) {
                    System.out.println("Reading error "+e);
                }
            }
        }
    }
}
