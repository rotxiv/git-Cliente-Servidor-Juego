package Negocio.Server;

import java.net.Socket;
import java.util.EventObject;

public class DataPacket extends EventObject {
    
    private Socket client;
    private Object packet;

    public DataPacket(Object source,Socket client){
        super(source);
        this.client = client;
    }
    public DataPacket(Object source, Socket client, Object packet) {
        super(source);
        this.client = client;
        this.packet = packet;
    }
    public Socket getClient() {
        return client;
    }
    public void setClient(Socket client) {
        this.client = client;
    }
    public Object getPacket() {
        return packet;
    }
    public void setPacket(Object packet) {
        this.packet = packet;
    }
    public String[] getStringPacket() {
        String stringParts = (String) packet;
        return stringParts.split("-");
    }
}
