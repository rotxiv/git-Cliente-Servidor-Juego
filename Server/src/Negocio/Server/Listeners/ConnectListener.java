package Negocio.Server.Listeners;

import Negocio.Server.EventOfClient;

import java.util.EventListener;

public interface ConnectListener extends EventListener {

    void OnConnect(EventOfClient event);
    void OnDesconnect(EventOfClient event);

}
