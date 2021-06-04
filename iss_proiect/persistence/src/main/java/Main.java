public class Main {
    public static void main(String[] args) {
        FirmaRepository firmaRepository = new FirmaRepository();
        AgentVanzariRepository agentVanzariRepo = new AgentVanzariRepository();
     //   AdministratorRepository administratorRepository = new AdministratorRepository();
        System.out.println(firmaRepository.findAll());
        System.out.println(agentVanzariRepo.findAll());
       // System.out.println(administratorRepository.findAll());
    }
}
