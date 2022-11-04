package Negocio.Server.Threads;

import Negocio.Server.*;

import java.net.Socket;
import java.util.*;

public class ThreadProcessDataPacket extends Thread {

    private final DataPacket dataPacket;
    private OutputPacket outputPacket;

    public ThreadProcessDataPacket(DataPacket dataPacket) {
        this.dataPacket = dataPacket;
    }
    @Override
    public void run() {
        organizeDataPacket((String) dataPacket.getPacket());
        switch (outputPacket.getCommandMessage()) {
            case "signIn" -> signIn(outputPacket);
            case "login" -> login(outputPacket);
            case "message" -> displayMessage(outputPacket);
            case "game" -> typeGameReceive(outputPacket);
            case "close" -> sendRemoveUser(outputPacket.getIdMessage(), outputPacket.getNickName());
        }
    }
//--------------------------------------------------------------------------------------------
    private void organizeDataPacket(String data) {
        String[] parts = data.split("-");
        outputPacket = new OutputPacket(this,parts);
    }
    private void sendMessage(Socket socket, OutputPacket packet) {
        Task task = new Task(socket, packet.toString());
        task.start();
    }
    private void signIn(OutputPacket packet) {
        boolean available = true;
        String nickName = packet.getNickName(); String password = packet.getPassword();
        String idNewUser = packet.getIdMessage();   String[] message;
        for (EventOfClient client : Server.clientTable.values()){
            if (Objects.equals(client.getNickName(), nickName)) {
                available = false;
                break;
            }
        }
        packet.setId(idNewUser);
        if (available) {
            Server.clientTable.get(idNewUser).setNickName(nickName);
            Server.clientTable.get(idNewUser).setPassword(password);
            Server.clientTable.get(idNewUser).setState("online");
            packet.setCommand("register");
            packet.setPacket(new String[]{idNewUser,nickName});
            sendMessage(dataPacket.getClient(),packet);
            sendListUsers(idNewUser);
            sendNewUserAdd(idNewUser,nickName);
            message = new String[]{"SERVER","Registro exitoso"};
            Server.Players.add(idNewUser);
            sendInitTurnPlayer(idNewUser);
        }
        else
            message = new String[]{"SEVER", "El NickName no esta disponible"};
        packet.setCommand("message");
        packet.setPacket(message);
        sendMessage(dataPacket.getClient(),packet);
    }
    private void login(OutputPacket packet) {
        String id = packet.getIdMessage();
        String nickName = packet.getNickName();
        String password = packet.getPassword();
        packet.setId(packet.getIdMessage());
        String antKey = null;
        for (String key : Server.clientTable.keySet()) {
            if (Objects.equals(Server.clientTable.get(key).getNickName(), nickName)) {
                if(Objects.equals(Server.clientTable.get(key).getPassword(), password)) {
                    Server.clientTable.get(id).setState("online");
                    Server.clientTable.get(id).setNickName(nickName);
                    Server.clientTable.get(id).setPassword(password);
                    packet.setCommand("login");
                    packet.setPacket(new String[]{id, nickName});
                    sendMessage(Server.clientTable.get(id).getClient(), packet);
                    packet.setCommand("message");
                    packet.setPacket(new String[]{"SERVER","Inicio de sesion correcta"});
                    sendMessage(Server.clientTable.get(id).getClient(), packet);
                    antKey = key;
                } else {
                    packet.setCommand("message");
                    packet.setPacket(new String[]{"SERVER","Nombre o contraseña incorrecta"});
                    sendMessage(Server.clientTable.get(id).getClient(),packet);
                }
                break;
            }
        }
        if (antKey != null) {
            Server.clientTable.remove(antKey);
            sendListUsers(id);
            sendNewUserAdd(id, nickName);
            Server.Players.add(id);
            sendInitTurnPlayer(id);
        }
    }
    private void displayMessage(OutputPacket packet) {
        final String id = packet.getIdMessage();
        for (String key : Server.clientTable.keySet()) {
            if (!Objects.equals(key, id)) {
                outputPacket.setCommand("message");
                outputPacket.setId(key);
                sendMessage(Server.clientTable.get(key).getClient(),packet);
            }
        }
    }
    private void typeGameReceive(OutputPacket packet){
        final String id = packet.getIdMessage();
        Server.clientTable.forEach((key,value) -> {
            if (!Objects.equals(key, id) && Objects.equals(value.getState(), "online")){
                outputPacket.setCommand("game");
                outputPacket.setId(key);
                Task task = new Task(Server.clientTable.get(key).getClient(),packet.getDataGame());
                task.start();
            }
        });
        ThreadProcessGame newGame = new ThreadProcessGame(packet.getPacket());
        newGame.init();
    }
    private void sendListUsers(String idNewUser) {
        List<String> auxUsers = new ArrayList<>();
        for (String key : Server.clientTable.keySet()) {
            if (!Objects.equals(key, idNewUser) && Objects.equals(Server.clientTable.get(key).getState(), "online")) {
                auxUsers.add(key);
                auxUsers.add(Server.clientTable.get(key).getNickName());
            }
        }
        String[] auxPack = auxUsers.toArray(new String[0]);
        OutputPacket packet = new OutputPacket(this,"userAdd",idNewUser,auxPack);
        sendMessage(Server.clientTable.get(idNewUser).getClient(),packet);
    }
    private void sendNewUserAdd(String id,String nickName) {
        final String[] message = { id, nickName };
        Server.clientTable.forEach((key,value) -> {
            if (!Objects.equals(key, message[0]) && value.getState().equals("online")) {
                OutputPacket packet = new OutputPacket(this,"userAdd",key,message);
                sendMessage(value.getClient(),packet);
            }
        });
    }
    private void sendRemoveUser(String id, String nickName) {
        final String[] message = { id, nickName, " Me retiro de la sala"};
        Server.clientTable.forEach((key,value) -> {
            if (!Objects.equals(key, message[0]) && value.getState().equals("online")) {
                OutputPacket packet = new OutputPacket(this,"removeUser",key,message);
                sendMessage(value.getClient(),packet);
            }
        });
    }
    private void sendInitTurnPlayer(String player) {
        if (Server.Players.size() == 1) {
            String[] message = new String[]{"available"};
            OutputPacket packet = new OutputPacket(this, "turn", player, message);
            sendMessage(Server.clientTable.get(player).getClient(),packet);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String[] msj = new String[]{"Server", "Eres el primero en jugar..."};
            OutputPacket packetTwo = new OutputPacket(this, "message", player, msj);
            sendMessage(Server.clientTable.get(player).getClient(),packetTwo);
        }
    }
}
