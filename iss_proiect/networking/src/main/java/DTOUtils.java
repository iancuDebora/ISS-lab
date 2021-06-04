
public class DTOUtils {
    public static AgentVanzari getFromDTO(AgentVanzariDTO dto){
        String id=dto.getId();
        String pass=dto.getPasswd();
        return new AgentVanzari(id, pass);

    }
    public static AgentVanzariDTO getDTO(AgentVanzari agent){
        String id = agent.getId();
        String parola = agent.getParola();
        return new AgentVanzariDTO(id, parola);
    }

    public static ComandaDTO getDTO(Comanda comanda){
        int id = comanda.getId();
        int cant = comanda.getCantitate();
        AgentVanzari agentVanzari = comanda.getAgentVanzari();
        AgentVanzariDTO agdto = getDTO(agentVanzari);
        Client client = comanda.getClient();
        ClientDTO cdto = getDTO(client);
        Produs produs = comanda.getProdus();
        ProdusDTO prdto = getDTO(produs);
        return new ComandaDTO(id,cdto,prdto,agdto,cant);
    }

    public static Comanda getFromDTO(ComandaDTO dto){
        int id=dto.getId();
        int cant = dto.getCant();
        AgentVanzariDTO agentVanzari =dto.getAgentVanzari();
        AgentVanzari ag = getFromDTO(agentVanzari);
        ClientDTO client = dto.getClient();
        Client client1 = getFromDTO(client);
        ProdusDTO produs = dto.getProdus();
        Produs produs1 = getFromDTO(produs);
        return new Comanda(id,client1,produs1,ag,cant);

    }

    public static AdministratorDTO getDTO(Administrator adm){
        String id = adm.getId();
        String parola = adm.getParola();
        return new AdministratorDTO(id, parola);
    }
    public static Administrator getFromDTO(AdministratorDTO dto){
        String id=dto.getId();
        String pass=dto.getPasswd();
        return new Administrator(id, pass);

    }

    public static Client getFromDTO(ClientDTO dto){
        String denumire=dto.getDenumire();
        return new Client(denumire);

    }
    public static ClientDTO getDTO(Client client){
        String nume = client.getNume();
        return new ClientDTO(nume);
    }

    public static Client[] getFromDTO(ClientDTO[] dtos){
        Client[] cazuri = new Client[dtos.length];
        for(int i=0;i<dtos.length;i++){
            cazuri[i] = getFromDTO(dtos[i]);
        }
        return cazuri;
    }

    public static ClientDTO[] getDTO(Client[] clienti){
        ClientDTO[] frDTO=new ClientDTO[clienti.length];
        for(int i=0;i<clienti.length;i++)
            frDTO[i]=getDTO(clienti[i]);
        return frDTO;
    }
    public static ProdusDTO getDTO(Produs produs){
        String denumire=produs.getDenumire();
        Float pret=produs.getPret();
        int cant = produs.getCantitateExistenta();
        System.out.println("TRY AGAIN getDTO");
        System.out.println(produs.getDenumire());
        AdministratorDTO administrator = getDTO(produs.getAdministrator());

        return new ProdusDTO(denumire,pret, cant, administrator);
    }

    public static Produs getFromDTO(ProdusDTO dto){
        String denumire=dto.getDenumire();
        Float pret=dto.getPret();
        int cant = dto.getCantitateExistenta();
        System.out.println("TRY AGAIN getDTO");
        System.out.println(dto.getDenumire());
        Administrator administrator = getFromDTO(dto.getAdministrator());
        return new Produs(denumire,pret, cant, administrator);

    }

    public static Produs[] getFromDTO(ProdusDTO[] dtos){
        Produs[] produse = new Produs[dtos.length];
        for(int i=0;i<dtos.length;i++){
            produse[i] = getFromDTO(dtos[i]);
        }
        return produse;
    }



    public static ProdusDTO[] getDTO(Produs[] produse){
        ProdusDTO[] frDTO=new ProdusDTO[produse.length];
        for(int i=0;i<produse.length;i++)
            frDTO[i]=getDTO(produse[i]);
        return frDTO;
    }


}
