package Negocio.Server;


import Negocio.Server.Listeners.ConnectListener;
import Negocio.Server.Listeners.MessageListener;
import Negocio.Server.Threads.*;
import com.google.gson.*;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class Server implements ConnectListener, MessageListener {

    public static Hashtable<String, EventOfClient> clientTable = new Hashtable<>();
    public static Queue<String> Players = new LinkedList<>();
    Server server;
    public int port;

    public Server(int port) {
        this.port = port;
    }
    public void start() {
        try {
            server = new Server(port);
            server.readDataUser();
            ClientListener newClient = new ClientListener(server);
            newClient.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//---------------------------------------------------------------------------------------------------------
    @Override
    public void OnConnect(EventOfClient event) {
        addNewConnect(event);
    }
    @Override
    public void OnDesconnect(EventOfClient event) {

    }
    @Override
    public void onReceivePackage(DataPacket packet) {
        ThreadProcessDataPacket processDataPacket = new ThreadProcessDataPacket(packet);
        processDataPacket.start();
    }
    @Override
    public void onDisconnect(Socket socket) {
        clientTable.forEach((key,value) -> {
            if (value.getClient() == socket) {
                try {
                    value.setState("offline");
                    value.getClient().close();
                    value.getThread().interrupt();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }
//---------------------------------------------------------------------------------------------------
    private void addNewConnect(EventOfClient event){
        String[] message = new String[]{"SingIn or Login"};
        TreadClient newClient = new TreadClient(event.getServer(), event.getClient());
        newClient.init();
        event.setThread(newClient);
        String hashCode = String.valueOf(event.hashCode());
        clientTable.put(hashCode,event);
        OutputPacket packet = new OutputPacket(this,"ID",hashCode,message);
        sendMessage(event.getClient(),packet);
    }
    private void sendMessage(Socket socket, OutputPacket packet) {
        Task task = new Task(socket, packet.toString());
        task.start();
    }
    private void readDataUser() throws IOException {
        FileReader file = new FileReader("ArchivesJson/TablaUsuarios.json");
        JsonElement element = new JsonParser().parse(file);
        JsonObject object = element.getAsJsonObject();
        HashMap userMap = new Gson().fromJson(object.toString(), HashMap.class);
        uploadUserInformation(userMap);
    }
    private void uploadUserInformation(HashMap userMap) {
        userMap.forEach((key,value) -> {
            String[] data = value.toString().split(",");
            List<String> auxData = Arrays.stream(data).toList();
            List<String> auxAux = getData(auxData);
            uploadUser(key.toString(), auxAux);
        });
        System.out.println(clientTable);
    }
    private List<String> getData(List<String> data) {
        List<String> auxData = new ArrayList<>(); int pos; String aux;
        for (int i = 0; i < data.size() - 1; i++) {
            pos = data.get(i).indexOf("=") + 1;
            aux = data.get(i).substring(pos);
            auxData.add(aux);
        }
        pos = data.get(data.size() - 1).indexOf("=") + 1;
        int val = data.get(data.size() - 1).length() - 1;
        aux = data.get(data.size() - 1).substring(pos,val);
        auxData.add(aux);
        return auxData;
    }
    private void uploadUser(String key, List<String> auxAux) {
        EventOfClient client = new EventOfClient(this);
        client.setIp(auxAux.get(0));
        client.setHour(auxAux.get(1));
        client.setNickName(auxAux.get(2));
        client.setPassword(auxAux.get(3));
        client.setState(auxAux.get(4));
        clientTable.put(key,client);
    }
    public void closeServer() throws IOException {
        Gson gson = new Gson();
        JsonObject object = gson.toJsonTree(clientTable).getAsJsonObject();
        PrintWriter out = new PrintWriter("ArchivesJson/TablaUsers.json");
        out.write(object.toString());
        out.flush();
        out.close();
        /*Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonText = gson.toJson(clientTable);
        System.out.println(jsonText);
        PrintWriter out = new PrintWriter("ArchivesJson/TablaUsuarios.json");
        out.write(jsonText);
        out.flush();
        out.close();*/
    }
}
