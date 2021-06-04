public interface IServices {
    void login(AgentVanzari agentVanzari, IObserver client) throws MyException;
    void loginAdm(Administrator administrator, IObserver client) throws MyException;
    Produs[] getProduse() throws MyException;
    Client[] getClienti() throws MyException;
    void deleteProdus(Produs produs) throws MyException;
    void addProdus(Produs produs) throws MyException;
    void updateProdus(Produs produs) throws MyException;
    void addComanda(Comanda comanda) throws MyException;
}
