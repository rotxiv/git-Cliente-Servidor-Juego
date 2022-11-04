package Negocio.Cliente;

import java.util.Arrays;
import java.util.EventObject;

public class Packet extends EventObject {

    private String command;
    private String id;
    private String[] packet;

    public Packet(Object source) {
        super(source);
    }
    public Packet(Object source, String command, String id, String[] packet) {
        super(source);
        this.command = command;
        this.id = id;
        this.packet = packet;
    }
    public Packet(Object source, String[] packet) {
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
//--------------------------------------------------------------------------
    public String getCommandMessage() {
        return packet[0];
    }
    public String getIdMessage() {
        return packet[1];
    }
    public String getMyTurn() {
        return packet[2];
    }
    public String[] getGame() {
        return Arrays.copyOfRange(packet,2,packet.length);
    }
    public String[] getPlayers() {
        return Arrays.copyOfRange(packet,2,packet.length);
    }
    public String[] getMessage() {
        return Arrays.copyOfRange(packet,2,packet.length);
    }

    @Override
    public String toString() {
        StringBuilder text = new StringBuilder(getCommand() + "-" + getId() + "-");
        for (String s : packet) text.append(s).append("-");
        return text.toString();
    }
}
