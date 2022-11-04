package Negocio.Cliente.Threads;

import Negocio.Cliente.Client;
import Negocio.Cliente.Listeners.MessageListener;

import java.io.DataInputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class ThreadReceiveMessage extends Thread{

    private final List<MessageListener> listeners = new ArrayList<>();
    private final Socket socket;

    public ThreadReceiveMessage(Client client, Socket socket) {
        this.socket = socket;
        listeners.add(client);
    }
    @Override
    public void run() {
        try {
            String packet;
            DataInputStream newData = new DataInputStream(socket.getInputStream());
            while(true) {
                packet = newData.readUTF();
                sendMessageNotification(packet);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void addListeners(MessageListener e){
        listeners.add(e);
    }
    private void sendMessageNotification(String packet) {
        for (MessageListener ml : listeners)
            ml.onReceivePackage(packet);
    }
}
