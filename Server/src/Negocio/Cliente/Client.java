package Negocio.Cliente;

import Negocio.Cliente.Listeners.MessageListener;
import Negocio.Cliente.Listeners.OrderTypeListener;
import Negocio.Cliente.Threads.ThreadProcessPacket;
import Negocio.Cliente.Threads.ThreadReceiveMessage;
import Negocio.Cliente.Threads.ThreadSendMessage;

import java.io.IOException;
import java.net.Socket;

//en CMD    javac Client.java ---> java Client

public class Client implements MessageListener, OrderTypeListener {

    private ViewClient pointerMain;
    private String id;
    private Socket socket;

    public Client(ViewClient client, String ip, int port) {
        try {
            this.pointerMain = client;
            socket = new Socket(ip,port);
            ThreadReceiveMessage threadMessage = new ThreadReceiveMessage(this, socket);
            threadMessage.start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
//----------------------------------------------------------------------------------------
    public void SignIn(Packet packet) {
        packet.setCommand("signIn");
        packet.setId(id);
        sendData(packet);
    }
    public void login(Packet packet) {
        packet.setCommand("login");
        packet.setId(id);
        sendData(packet);
    }
    public void closeAll(Packet packet) {
        packet.setCommand("close");
        packet.setId(id);
        sendData(packet);
    }
    public void sendGame(Packet packet) {
        packet.setCommand("game");
        packet.setId(id);
        sendData(packet);
    }
    private void sendData(Packet packet){
        ThreadSendMessage newSendMessage = new ThreadSendMessage(socket,packet);
        newSendMessage.start();
    }
//-------------------------------------------------------------------------------------------------
    @Override
    public void onReceiveId(String id) {
        this.id = id;
    }
    @Override
    public void onReceivePackage(String packet) {
        ThreadProcessPacket processPacket = new ThreadProcessPacket(packet);
        processPacket.addListener(pointerMain);
        processPacket.addListener(this);
        processPacket.start();
    }
    @Override
    public void onReceiveRegister(String[] register) { }
    @Override
    public void onReceiveLogin(String[] login) { }
    @Override
    public void onReceiveMessage(String[] message) { }
    @Override
    public void onReceiveGame(String[] data) { }
    @Override
    public void onReceiveGameTurn(String turn) { }
    @Override
    public void onReceiveUserAdd(String[] data) { }
    @Override
    public void onReceiveRemoveUser(String[] data) { }

}
