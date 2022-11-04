package Negocio.Server;

import java.util.EventObject;

public class OutputPacket extends EventObject {
    private String command;
    private String id;
    private String[] packet;

    public OutputPacket(Object source, String command, String id, String[] packet) {
        super(source);
        this.command = command;
        this.id = id;
        this.packet = packet;
    }
    public OutputPacket(Object source, String[] packet) {
        super(source);
        this.packet = packet;
    }
    public void setCommand(String command) {
        this.command = command;
    }
    public void setId(String id) {
        this.id = id;
    }
    public void setPacket(String[] packet) {
        this.packet = packet;
    }
    public String getCommand() {
        return command;
    }
    public String getId() {
        return id;
    }
    public String[] getPacket() {
        return packet;
    }
//-----------------------------------------------------------------------------------
    public String  getCommandMessage() {
        return packet[0];
    }
    public String getIdMessage() {
        return packet[1];
    }
    public String getNickName() {
        return packet[2];
    }
    public String getPassword() {
        return packet[3];
    }
    public String getString() {
        return command + "-" + id;
    }
    public String getDataGame() {
        StringBuilder text = new StringBuilder(command + "-" + id + "-");
        for (int i = 1; i < packet.length; i++) {
            text.append(packet[i]).append("-");
        }
        return text.toString();
    }
//-----------------------------------------------------------------------------------
    @Override
    public String toString() {
        StringBuilder text = new StringBuilder(command + "-" + id + "-");
        for (String s : packet) text.append(s).append("-");
        return text.toString();
    }
}
