package Negocio.Server;

import java.io.Serializable;
import java.net.Socket;
import java.util.EventObject;

public class EventOfClient extends EventObject implements Serializable {

    private transient Thread thread;
    private transient Server server;
    private transient Socket client;
    private String ip;
    private String hour;
    private String nickName;
    private String password;
    private String state;

    private transient String[] board;
    private transient int[] categoryBoard = new int[10];

    public EventOfClient(Object source) {
        super(source);
    }
    public EventOfClient(Object source, String ip, String hour,Server server, Socket client) {
        super(source);
        this.ip = ip;
        this.hour = hour;
        this.client = client;
        this.server = server;
    }

    @Override
    public Object getSource() {
        return super.getSource();
    }
    public void setThread(Thread thread) {
        this.thread = thread;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public void setHour(String hour) {
        this.hour = hour;
    }
    public void setClient(Socket socket) {
        this.client = socket;
    }
    public Socket getClient() {
        return client;
    }
    public void setServer(Server server) {
        this.server = server;
    }
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public void setState(String state) {
        this.state = state;
    }
    public Thread getThread() {
        return thread;
    }
    public String getIp() {
        return ip;
    }
    public String getHour() {
        return hour;
    }
    public String getNickName() {
        return nickName;
    }
    public String getPassword() {
        return password;
    }
    public Server getServer() {
        return server;
    }
    public String getState() {
        return state;
    }
    public void setBoard(String[] board) {
        this.board = board;
    }
    public String[] getBoard() {
        return board;
    }
    public void setCategoryBoard(int[] categoryBoard) {
        this.categoryBoard = categoryBoard;
    }
    public int[] getCategoryBoard() {
        return categoryBoard;
    }

    @Override
    public String toString() {
        return "EventOfClient{" +
                "ip='" + ip + '\'' +
                ", hour='" + hour + '\'' +
                ", nickName='" + nickName + '\'' +
                ", password='" + password + '\'' +
                ", state='" + state + '\'' +
                '}';
    }
}
