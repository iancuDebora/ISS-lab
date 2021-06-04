

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServicesImplementation implements IServices {
    private FirmaRepository firmaRepository = new FirmaRepository();
    private AgentVanzariRepository agentVanzariRepo = new AgentVanzariRepository();
    private ProdusRepository produsRepository = new ProdusRepository();
    private AdministratorRepository administratorRepository = new AdministratorRepository();
    private ClientRepository clientRepository = new ClientRepository();
    private ComandaRepository comandaRepository = new ComandaRepository();
    private Map<String, IObserver> loggedClientsAg;
    private Map<String, IObserver> loggedClientsAdm;

    public ServicesImplementation(FirmaRepository firmaRepository,AgentVanzariRepository agentVanzariRepo,
                                  ProdusRepository produsRepository,
                                  AdministratorRepository administratorRepository,
                                  ClientRepository clientRepository,
                                  ComandaRepository comandaRepository) {
        this.firmaRepository = firmaRepository;
        this.agentVanzariRepo = agentVanzariRepo;
        this.produsRepository = produsRepository;
        this.administratorRepository = administratorRepository;
        this.clientRepository = clientRepository;
        this.comandaRepository = comandaRepository;
        this.loggedClientsAg = new ConcurrentHashMap<>();
        this.loggedClientsAdm = new ConcurrentHashMap<>();
    }

    public synchronized void login(AgentVanzari agent, IObserver client) throws MyException {
        AgentVanzari userR=agentVanzariRepo.findOne(agent.getId());

        if (userR!=null){
            if(loggedClientsAg.get(agent.getId())!=null)
                throw new MyException("Agentul este deja logat!");
            loggedClientsAg.put(agent.getId(), client);

        }else
            throw new MyException("Autentificarea a esuat!");
    }

    public synchronized void loginAdm(Administrator adm, IObserver client) throws MyException {
        Administrator userR=administratorRepository.findOne(adm.getId());

        if (userR!=null){
            if(loggedClientsAdm.get(adm.getId())!=null)
                throw new MyException("Administratorul este deja logat!");
            loggedClientsAdm.put(adm.getId(), client);

        }else
            throw new MyException("Autentificarea a esuat!");
    }

    public synchronized Produs[] getProduse()
    {
        List<Produs> rez = new ArrayList<>();
        for (Produs produs : produsRepository.findAll())
            rez.add(produs);
        return rez.toArray(new Produs[rez.size()]);
    }

    public synchronized Client[] getClienti()
    {
        List<Client> rez = new ArrayList<>();
        for (Client client : clientRepository.findAll())
            rez.add(client);
        return rez.toArray(new Client[rez.size()]);
    }

    private final int defaultThreadsNo=7;
    @Override
    public void deleteProdus(Produs produs) throws MyException {
        Iterable<AgentVanzari> agenti = agentVanzariRepo.findAll();
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for (AgentVanzari ag :agenti)
        {
            IObserver client=loggedClientsAg.get(ag.getId());
            if (client!=null)
                executor.execute(() -> {
                    produsRepository.delete(produs);
                    try {
                        client.produsDeleted(produs);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
        }

        Iterable<Administrator> administrators = administratorRepository.findAll();
        ExecutorService executor2= Executors.newFixedThreadPool(defaultThreadsNo);
        for (Administrator vol :administrators)
        {
            IObserver client=loggedClientsAdm.get(vol.getId());
            if (client!=null)
                executor2.execute(() -> {
                 //   produsRepository.delete(produs);
                    try {
                        client.produsDeleted(produs);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
        }

        executor.shutdown();
    }

    @Override
    public void addProdus(Produs produs) throws MyException {
        System.out.println("in service impl");
        System.out.println(produs.getDenumire());
        Iterable<AgentVanzari> agenti = agentVanzariRepo.findAll();
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for (AgentVanzari ag :agenti)
        {
            IObserver client=loggedClientsAg.get(ag.getId());
            if (client!=null)
                executor.execute(() -> {
                    produsRepository.save(produs);
                    try {
                        client.produsAdded(produs);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
        }

         executor.shutdown();

        Iterable<Administrator> administratori = administratorRepository.findAll();
        ExecutorService executor2= Executors.newFixedThreadPool(defaultThreadsNo);
        for (Administrator adm :administratori)
        {
            IObserver client=loggedClientsAdm.get(adm.getId());
            if (client!=null)
                executor2.execute(() -> {
                 //   produsRepository.save(produs);
                    try {
                        client.produsAdded(produs);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
        }
        executor2.shutdown();
    }

    @Override
    public void updateProdus(Produs produs) throws MyException {
        Iterable<AgentVanzari> agenti = agentVanzariRepo.findAll();
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for (AgentVanzari ag : agenti)
        {
            IObserver client=loggedClientsAg.get(ag.getId());
            if (client!=null)
                executor.execute(() -> {
                    produsRepository.update(produs);
                    try {
                        client.produsUpdated(produs);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
        }

       executor.shutdown();

        Iterable<Administrator> administratori = administratorRepository.findAll();
        ExecutorService executor2= Executors.newFixedThreadPool(defaultThreadsNo);
        for (Administrator vol :administratori)
        {
            IObserver client=loggedClientsAdm.get(vol.getId());
            if (client!=null)
                executor2.execute(() -> {
                 //   produsRepository.update(produs);
                    try {
                        client.produsUpdated(produs);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
        }
        executor2.shutdown();
    }

    @Override
    public void addComanda(Comanda comanda) throws MyException {
        Iterable<AgentVanzari> agenti = agentVanzariRepo.findAll();
        ExecutorService executor= Executors.newFixedThreadPool(defaultThreadsNo);
        for (AgentVanzari ag :agenti)
        {
            IObserver client=loggedClientsAg.get(ag.getId());
            if (client!=null)
                executor.execute(() -> {
                   comandaRepository.save(comanda);
                    try {
                        client.comandaAdded(comanda);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
        }

        executor.shutdown();

        Iterable<Administrator> administratori = administratorRepository.findAll();
        ExecutorService executor2= Executors.newFixedThreadPool(defaultThreadsNo);
        for (Administrator adm :administratori)
        {
            IObserver client=loggedClientsAdm.get(adm.getId());
            if (client!=null)
                executor2.execute(() -> {
                 //   comandaRepository.save(comanda);
                    try {
                        client.comandaAdded(comanda);
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                });
        }
        executor2.shutdown();
    }

}
