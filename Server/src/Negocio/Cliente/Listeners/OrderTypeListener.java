package Negocio.Cliente.Listeners;

public interface OrderTypeListener {

    void onReceiveId(String id);
    void onReceiveRegister(String[] register);
    void onReceiveLogin(String[] login);
    void onReceiveMessage(String[] message);
    void onReceiveGame(String[] data);
    void onReceiveGameTurn(String turn);
    void onReceiveUserAdd(String[] data);
    void onReceiveRemoveUser(String[] data);

}
