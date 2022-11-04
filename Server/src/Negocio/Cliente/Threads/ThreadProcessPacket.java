package Negocio.Cliente.Threads;

import Negocio.Cliente.Listeners.OrderTypeListener;
import Negocio.Cliente.Packet;

import java.util.ArrayList;
import java.util.List;

public class ThreadProcessPacket extends Thread{

    private final List<OrderTypeListener> listeners = new ArrayList<>();
    private final String data;
    Packet dataPacket;

    public ThreadProcessPacket(String packet){
        this.data = packet;
    }
    public void addListener(OrderTypeListener l) {
        listeners.add(l);
    }
//----------------------------------------------------------------------------------------------
    @Override
    public void run() {
        organizePacket(data);
        String command = dataPacket.getCommandMessage();
        switch (command) {
            case "ID" -> typeNumIdReceived();
            case "register" -> typeRegisterReceived();
            case "login" -> typeLoginReceived();
            case "message" -> typeMessageReceived();
            case "game" -> typeGameReceived();
            case "turn" -> typeGameTurnReceived();
            case "userAdd" -> typeUserAddReceived();
            case "removeUser" -> typeRemoveUserReceived();
        }
    }
    //----------------------------------------------------------------------------------------------
    private void organizePacket(String data) {
        String[] parts = data.split("-");
        this.dataPacket = new Packet(this,parts);
    }
    private void typeNumIdReceived() {
        for (OrderTypeListener otl : listeners)
            otl.onReceiveId(dataPacket.getIdMessage());
    }
    private void typeMessageReceived(){
        for (OrderTypeListener otl : listeners)
            otl.onReceiveMessage(dataPacket.getMessage());
    }
    private void typeLoginReceived(){
        for (OrderTypeListener otl : listeners)
            otl.onReceiveLogin(dataPacket.getMessage());
    }
    private void typeRegisterReceived(){
        for (OrderTypeListener otl : listeners)
            otl.onReceiveRegister(dataPacket.getMessage());
    }
    private void typeGameReceived() {
        for (OrderTypeListener otl : listeners)
            otl.onReceiveGame(dataPacket.getGame());
    }
    private void typeGameTurnReceived() {
        for (OrderTypeListener otl : listeners)
            otl.onReceiveGameTurn(dataPacket.getMyTurn());
    }
    private void typeUserAddReceived() {
        for (OrderTypeListener otl : listeners)
            otl.onReceiveUserAdd(dataPacket.getPlayers());
    }
    private void typeRemoveUserReceived() {
        for (OrderTypeListener otl : listeners)
            otl.onReceiveRemoveUser(dataPacket.getPlayers());
    }
}
