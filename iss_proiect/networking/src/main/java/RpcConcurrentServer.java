import java.net.Socket;

public class RpcConcurrentServer extends AbsConcurrentServer {
    private IServices server;
    public RpcConcurrentServer(int port, IServices server) {
        super(port);
        this.server = server;
        System.out.println("RpcConcurrentServer");
    }

    @Override
    protected Thread createWorker(Socket client) {

        ClientRpcReflectionWorker worker=new ClientRpcReflectionWorker(server, client);

        Thread tw=new Thread(worker);
        return tw;
    }

    @Override
    public void stop(){
        System.out.println("Stopping services ...");
    }
}