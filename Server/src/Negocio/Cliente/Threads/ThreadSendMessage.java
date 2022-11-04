package Negocio.Cliente.Threads;

import Negocio.Cliente.Packet;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ThreadSendMessage extends Thread{

    private final Socket client;
    private final Packet packet;

    public ThreadSendMessage(Socket client, Packet packet){
        this.client = client;
        this.packet = packet;
    }

    @Override
    public void run() {
        try {
            DataOutputStream dataOutput = new DataOutputStream(client.getOutputStream());
            dataOutput.writeUTF(packet.toString());
            System.out.println(packet);
            dataOutput.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
