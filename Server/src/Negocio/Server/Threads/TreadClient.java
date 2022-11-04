package Negocio.Server.Threads;

import Negocio.Server.DataPacket;
import Negocio.Server.Listeners.MessageListener;
import Negocio.Server.Server;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.*;

public class TreadClient extends Thread {

    private final List<MessageListener> listeners = new ArrayList<>();
    DataInputStream newData;
    private DataPacket dataPacket;
    private final Socket client;

    public TreadClient(Server server, Socket client) {
        this.client = client;
        listeners.add(server);
    }
//---------------------------------------------------------------------------------------------
    @Override
    public void run() {
        while (client.isConnected()) {
            try {
                dataPacket = new DataPacket(this,client);
                DataInputStream newData = new DataInputStream(client.getInputStream());
                dataPacket.setPacket(newData.readUTF());
                sendMessageNotification();
            }
            catch (IOException e) {
                sendDisconnectNotification();
                closeDataInputStream();
                break;
            }
        }
        /*try {
            dataPacket = new DataPacket(this,client);
            newData = new DataInputStream(client.getInputStream());
            while(newData.) {
                dataPacket.setPacket(newData.readUTF());
                sendMessageNotification();
            }
        } catch (IOException ex) {
            Logger.getLogger(TreadClient.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            sendDesconnectionNotification();
        }*/
    }
//---------------------------------------------------------------------------------------------
    public void init() {
        this.start();
    }
    public void addListeners(MessageListener ml){
        listeners.add(ml);
    }
    private void sendMessageNotification() {
        for (MessageListener ml : listeners)
            ml.onReceivePackage(dataPacket);
    }
    private void sendDisconnectNotification(){
        System.out.println(" Gracias por tu visita y vuelve pronto...!");
        for (MessageListener ml : listeners)
            ml.onDisconnect(dataPacket.getClient());
    }
    private void closeDataInputStream() {
        try {
            if (newData != null)
                newData.close();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
