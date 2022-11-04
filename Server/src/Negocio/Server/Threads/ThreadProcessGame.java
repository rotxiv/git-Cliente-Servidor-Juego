package Negocio.Server.Threads;

import Negocio.Server.OutputPacket;

import java.net.Socket;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static Negocio.Server.Server.Players;
import static Negocio.Server.Server.clientTable;

public class ThreadProcessGame extends Thread {

    private final List<String> playCategory;
    private final String[] dataGame;

    public ThreadProcessGame(String[] packet) {
        String[] cat = new String[]{"Uno", "Dos", "Tres", "Cuatro", "Cinco", "Seis", "Escalera", "Full", "Poker", "Grand"};
        playCategory = Arrays.stream(cat).toList();
        this.dataGame = packet;
    }
    @Override
    public void run() {
        processPlayerPlay();
        verifyEquality();
        sendTurnPlayer();
    }
    public void init() {
        start();
    }
    private String getOldTurnIdPlayer() {
        return dataGame[1];
    }
    private String getNewTurnIdPlayer() {
        return Players.remove();
    }
    private void verifyEquality() {
        if (Objects.equals(getOldTurnIdPlayer(), Players.peek()))
            Players.remove();
    }
    private void processPlayerPlay() {
        String[] auxData = new String[dataGame.length - 3];
        System.arraycopy(dataGame, 3, auxData, 0, 10);
        System.out.println(Arrays.toString(auxData));
        clientTable.get(getOldTurnIdPlayer()).setBoard(auxData);
        Players.add(getOldTurnIdPlayer());
        verifyPlay();
    }
    private void verifyPlay() {
        String category = dataGame[2];
        if (Objects.equals(category, playCategory.get(9)))
            sendMessageFinalGame();
        int pos = playCategory.indexOf(category);
        int[] auxCategoryBoard = clientTable.get(getOldTurnIdPlayer()).getCategoryBoard();
        auxCategoryBoard[pos] = 1;
        clientTable.get(getOldTurnIdPlayer()).setCategoryBoard(auxCategoryBoard);
    }
    private void sendTurnPlayer() {
        String newTurnId = getNewTurnIdPlayer();
        String[] message = new String[]{"available"};
        OutputPacket packet = new OutputPacket(this,"turn",newTurnId,message);
        sendMessage(clientTable.get(newTurnId).getClient(),packet);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        String[] msj = new String[]{"Server", "Es tu turno para jugar..."};
        OutputPacket packetTwo = new OutputPacket(this, "message", newTurnId, msj);
        sendMessage(clientTable.get(newTurnId).getClient(),packetTwo);
    }
    private void sendMessageFinalGame() {
        String[] message = new String[]{"SERVER", getOldTurnIdPlayer() + " Ha ganado el juego..."};
        for (String id : Players) {
            OutputPacket packet = new OutputPacket(this,"message",id,message);
            sendMessage(clientTable.get(id).getClient(),packet);
        }
    }
    private void sendMessage(Socket socket, OutputPacket packet) {
        Task task = new Task(socket, packet.toString());
        task.start();
    }
}
