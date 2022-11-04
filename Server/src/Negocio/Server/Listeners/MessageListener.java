package Negocio.Server.Listeners;

import Negocio.Server.DataPacket;

import java.net.Socket;
import java.util.EventListener;

public interface MessageListener extends EventListener {
    
    void onReceivePackage(DataPacket packet);
    void onDisconnect(Socket socket);
    
}
