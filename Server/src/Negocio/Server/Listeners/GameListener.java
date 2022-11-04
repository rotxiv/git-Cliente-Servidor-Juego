package Negocio.Server.Listeners;

public interface GameListener {

    void onReceiveNewPlayer(String idPlayer);
    void onReceiveDataGame(String[] data);
}
