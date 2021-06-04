import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.Socket;
import java.rmi.RemoteException;

public class ClientRpcReflectionWorker implements Runnable, IObserver {
    private IServices server;
    private Socket connection;

    private ObjectInputStream input;
    private ObjectOutputStream output;
    private volatile boolean connected;
    public ClientRpcReflectionWorker(IServices server, Socket connection) {
        this.server = server;
        this.connection = connection;
        try{
            output=new ObjectOutputStream(connection.getOutputStream());
            output.flush();
            input=new ObjectInputStream(connection.getInputStream());
            connected=true;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        while(connected){
            try {
                Object request=input.readObject();
                Response response=handleRequest((Request)request);
                if (response!=null){
                    sendResponse(response);
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        try {
            input.close();
            output.close();
            connection.close();
        } catch (IOException e) {
            System.out.println("Error "+e);
        }
    }


    private static Response okResponse = new Response.Builder().type(ResponseType.OK).build();

    private Response handleRequest(Request request){
        Response response=null;
        String handlerName="handle"+(request).type();
        System.out.println("HandlerName "+handlerName);
        try {
            Method method=this.getClass().getDeclaredMethod(handlerName, Request.class);
            response=(Response)method.invoke(this,request);
            System.out.println("Method "+handlerName+ " invoked");
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return response;
    }

    private Response handleLOGIN(Request request){
        System.out.println("Login request ..."+request.type());
        AgentVanzariDTO dto=(AgentVanzariDTO)request.data();
        AgentVanzari agent = DTOUtils.getFromDTO(dto);
        try {
            server.login(agent, this);
            return okResponse;
        } catch (MyException e) {
            connected=false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleLOGIN_ADM(Request request){
        System.out.println("Login request ..."+request.type());
        AdministratorDTO dto=(AdministratorDTO)request.data();
        Administrator voluntar = DTOUtils.getFromDTO(dto);
        try {
            server.loginAdm(voluntar, this);
            return okResponse;
        } catch (MyException e) {
            connected=false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handlePRODUSE(Request request){
        System.out.println("GetCazuri Request ..."+request.type());

        try {
            Produs[] cazuri = server.getProduse();
            ProdusDTO[] frDTO = DTOUtils.getDTO(cazuri);
            return new Response.Builder().type(ResponseType.GET_PRODUSE).data(frDTO).build();
        } catch (MyException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleCLIENTI(Request request){
        System.out.println("GetCazuri Request ..."+request.type());

        try {
            Client[] cazuri = server.getClienti();
            ClientDTO[] frDTO = DTOUtils.getDTO(cazuri);
            return new Response.Builder().type(ResponseType.GET_CLIENTI).data(frDTO).build();
        } catch (MyException e) {
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleDELETE_PRODUS(Request request)
    {
        System.out.println("delete produs request ..."+request.type());
        ProdusDTO dto=(ProdusDTO)request.data();
        Produs produs = DTOUtils.getFromDTO(dto);
        try {
            server.deleteProdus(produs);
            return okResponse;
        } catch (MyException e) {
            connected=false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private Response handleADD_PRODUS(Request request){
        System.out.println("add produs request ..."+request.type());
        ProdusDTO dto = (ProdusDTO)request.data();
        Produs produs = DTOUtils.getFromDTO(dto);

        try {
            server.addProdus(produs);
            return okResponse;
        } catch (MyException e) {
            connected=false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }


    private Response handleADD_COMANDA(Request request){
        System.out.println("add comanda request ..."+request.type());
        ComandaDTO dto = (ComandaDTO)request.data();
        Comanda comanda = DTOUtils.getFromDTO(dto);
        try {
            server.addComanda(comanda);
            return okResponse;
        } catch (MyException e) {
            connected=false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }


    private Response handleUPDATE_PRODUS(Request request){
        System.out.println("update produs request ..."+request.type());
        ProdusDTO dto = (ProdusDTO)request.data();
        Produs produs = DTOUtils.getFromDTO(dto);
        try {
            server.updateProdus(produs);
            return okResponse;
        } catch (MyException e) {
            connected=false;
            return new Response.Builder().type(ResponseType.ERROR).data(e.getMessage()).build();
        }
    }

    private void sendResponse(Response response) throws IOException{
        System.out.println("sending response "+response);
        output.writeObject(response);
        output.flush();
    }

    @Override
    public void produsDeleted(Produs produs) throws RemoteException {
        ProdusDTO dto = DTOUtils.getDTO(produs);
        Response resp = new Response.Builder().type(ResponseType.DELETED_PRODUS).data(dto).build();
        System.out.println("Produs deleted");
        try {
            sendResponse(resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void produsAdded(Produs produs) throws RemoteException {
        ProdusDTO dto = DTOUtils.getDTO(produs);
        Response resp = new Response.Builder().type(ResponseType.NEW_PRODUS).data(dto).build();
        System.out.println("Produs added");
        try {
            sendResponse(resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void produsUpdated(Produs produs) throws RemoteException {
        ProdusDTO dto = DTOUtils.getDTO(produs);
        Response resp = new Response.Builder().type(ResponseType.UPDATED_PRODUS).data(dto).build();
        System.out.println("Produs updated");
        try {
            sendResponse(resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void comandaAdded(Comanda comanda) throws RemoteException {
        ComandaDTO dto = DTOUtils.getDTO(comanda);
        Response resp = new Response.Builder().type(ResponseType.NEW_COMANDA).data(dto).build();
        System.out.println("comanda added");
        try {
            sendResponse(resp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
