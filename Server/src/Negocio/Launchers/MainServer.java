package Negocio.Launchers;

import Negocio.Server.Server;

import java.io.IOException;

public class MainServer {

    public static void main(String[] args) throws IOException {

        Server server = new Server(2020);
        server.start();
        System.out.println("Servidor de ROTXIV iniciado...!");

        /*try {
            Thread.sleep(120000);
            server.closeServer();
            System.exit(0);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }*/
    }
}
