package Negocio.Server.Threads;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Task extends Thread{

    private final Socket client;
    private final String packet;
    DataOutputStream data;

    public Task(Socket client, String packet){
        this.client = client;
        this.packet = packet;
    }
    @Override
    public void run() {
        try {
            data = new DataOutputStream(client.getOutputStream());
            data.writeUTF(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void closeAll() {
        try {
            client.close();
            data.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
