import java.io.IOException;
import java.util.Properties;

public class StartRpcServer {
    private static int defaultPort=55555;


    public static void main(String[] args) {


        Properties serverProps=new Properties();
        try {
            serverProps.load(StartRpcServer.class.getResourceAsStream("/server.properties"));
            System.out.println("Server properties set. ");
            serverProps.list(System.out);
        } catch (IOException e) {
            System.err.println("Cannot find server.properties "+e);
            return;
        }

        FirmaRepository firmaRepository = new FirmaRepository();
        AgentVanzariRepository agentVanzariRepo = new AgentVanzariRepository();
        AdministratorRepository administratorRepository = new AdministratorRepository();
        ProdusRepository produsRepository = new ProdusRepository();
        ClientRepository clientRepository = new ClientRepository();
        ComandaRepository comandaRepository = new ComandaRepository();



        IServices serverImpl=new ServicesImplementation(firmaRepository,agentVanzariRepo,produsRepository,
                administratorRepository,clientRepository,comandaRepository);
        int serverPort=defaultPort;
        try {
            serverPort = Integer.parseInt(serverProps.getProperty("proiect.server.port"));
        }catch (NumberFormatException nef){
            System.err.println("Wrong  Port Number"+nef.getMessage());
            System.err.println("Using default port "+defaultPort);
        }
        System.out.println("Starting server on port: "+serverPort);
        AbstractServer server = new RpcConcurrentServer(serverPort, serverImpl);
        try {
            server.start();
        } catch (ServerException e) {
            System.err.println("Error starting the server" + e.getMessage());
        }finally {
            try {
                server.stop();
            }catch(ServerException e){
                System.err.println("Error stopping server "+e.getMessage());
            }
        }
    }
}
