package Negocio.Server.Threads;

import Negocio.Server.EventOfClient;
import Negocio.Server.Listeners.ConnectListener;
import Negocio.Server.Server;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class ClientListener extends Thread {

    private final List<ConnectListener> listeners = new ArrayList<>();
    private final ServerSocket serverSocket;
    private EventOfClient event;
    private final Server server;
    private Socket client;

    public ClientListener(Server server) throws IOException {
        this.serverSocket = new ServerSocket(server.port);
        this.server = server;
        listeners.add(server);
    }
    @Override
    public void run() {
        clientListenerInit();
    }
    public void clientListenerInit(){
        while (true) {
            try {
                client = serverSocket.accept();
                setDataEvent();
                sendConnectionNotification();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public void setDataEvent(){
        String ip = client.getInetAddress().getHostAddress();
        String hour = String.valueOf(System.currentTimeMillis());
        event = new EventOfClient(this, ip, hour, server, client);
        event.setNickName("");
        event.setPassword("");
    }
    public void sendConnectionNotification(){
        String name = event.getIp();
        System.out.println("Server : Hola " + name + " Bienvenido al servidor de ROTXIV...!");
        for (ConnectListener cl : listeners)
            cl.OnConnect(event);
    }
    public void sendDesconnectionNotification(){
        String name = event.getIp();
        System.out.println(name + " Gracias por tu visita y vuelve pronto...!");
        for (ConnectListener cl : listeners)
            cl.OnDesconnect(event);
    }
}
