package Negocio.Cliente;

import Negocio.Cliente.Listeners.OrderTypeListener;
import Negocio.Cliente.Panels.LoginPanel;
import Negocio.Cliente.Panels.MessagePanel;
import Negocio.Cliente.Panels.PlayerPanel;
import Negocio.Cliente.Panels.UserPanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Objects;
import java.util.stream.IntStream;

public class ViewClient extends JFrame implements OrderTypeListener, MouseListener{

    Hashtable<String, PlayerPanel> players = new Hashtable<>();

    private Client userClient;
    private LoginPanel loginPanel;
    private MessagePanel messagePanel;
    private PlayerPanel playerPanel;
    private UserPanel userPanel;
    private Game game;
    private String myTurn;

    int coordX = 10;

    public ViewClient() {
        initAtribute();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        getContentPane().setBackground(new Color(0x3D6176));
        setSize(1000,600);
        setLocationRelativeTo(this);
        setTitle(" Usuario ");
        setResizable(false);
        setLayout(null);
        MyComponents();
        setVisible(true);
    }
    private void initAtribute() {
        userClient = new Client(this,"localhost",2020);
        messagePanel = new MessagePanel();
        loginPanel = new LoginPanel();
        userPanel = new UserPanel();
        game = new Game();
    }
    private void MyComponents() {
        add(loginPanel);
        loginPanel.getButtonSignIn().addActionListener(e -> SignIn());
        loginPanel.getButtonlogin().addActionListener(e -> login());
        userPanel.getGame().addActionListener(e -> rollDice());
        userPanel.getEndGame().addActionListener(e -> endGame());
        userPanel.getSelectDice().addActionListener(e -> demoGrand());
        userPanel.getOptionCategories().addActionListener(e -> selectCategory());
        userPanel.getDiceOne().addMouseListener(this);
        userPanel.getDiceTwo().addMouseListener(this);
        userPanel.getDiceThree().addMouseListener(this);
        userPanel.getDiceFour().addMouseListener(this);
        userPanel.getDiceFive().addMouseListener(this);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                logOut();
                System.exit(0);
            }
        });
    }
    private void SignIn() {
        if (Objects.equals(loginPanel.getPassword(), loginPanel.getConfPassword())) {
            String[] pack = new String[] {loginPanel.getNickName(), loginPanel.getPassword(), loginPanel.getConfPassword()};
            Packet packet = new Packet(this,pack);
            userClient.SignIn(packet);
        }
        else
            System.out.println("contraseña incorrecta...!");
    }
    private void login() {
        String[] pack = new String[] {loginPanel.getNickName(), loginPanel.getPassword()};
        Packet packet = new Packet(this,pack);
        userClient.login(packet);
    }
    private void logOut() {
        String[] message = new String[]{userPanel.getMyNickName()};
        Packet packet = new Packet(this,message);
        userClient.closeAll(packet);
    }
    private void rollDice() {
        if (Objects.equals(myTurn, "available") && game.getCant() <= 3) {
            game.rollDice();
            userPanel.setDiceValue(game.getDice(), game.getState());
            game.setCant(game.getCant() + 1);
            userPanel.setNewMessage("Desea seleccionar uno o mas dados");
        }
        else
            userPanel.setNewMessage("Te quedaste sin intentos o aun no es tu turno");
        reloadView();
    }
    private void setStateOnClick(JLabel label) {
        if (Objects.equals(myTurn, "available")) {
            int i = 0;
            for (JLabel l : userPanel.getDice()) {
                if (label == l && Game.state[i] == 0) {
                    game.setStateDice(i, 1);
                    game.setLevelDice(i, game.getCant() - 1);
                    label.setBackground(new Color(0xC4DDDE));
                    break;
                }
                if (label == l && Game.state[i] == 1) {
                    game.setStateDice(i, 0);
                    game.setLevelDice(i, 0);
                    label.setBackground(new Color(0x99BFC6));
                    break;
                }
                i++;
            }
        }
        else
            userPanel.setNewMessage("Te quedaste sin intentos o aun no es tu turno");
        reloadView();
    }
    private void selectCategory() {
        if (Objects.equals(myTurn, "available")) {
            String category = (String) userPanel.getOptionCategories().getSelectedItem();
            int index = userPanel.getOptionCategories().getSelectedIndex();
            game.setCategory(category);
            userPanel.getOptionCategories().removeItemAt(index);
            userPanel.setNewMessage("Seleccionaste la categoria : " + category);
        }
        else
            userPanel.setNewMessage("Te quedaste sin intentos o aun no es tu turno");
        reloadView();
    }
    private void endGame() {
        game.processGame();
        String[] tem = new String[]{game.getCategory()};
        int aux = game.getFinalValueTable().length;
        String[] pack = new String[tem.length + aux];
        System.arraycopy(tem,0,pack,0,tem.length);
        System.arraycopy(game.getFinalValueTable(),0,pack,tem.length,aux);
        userPanel.setFinalValue(game.getFinalValueTable());
        Packet packet = new Packet(this,pack);
        userClient.sendGame(packet);
        resetStateTurn();
    }
    private void resetStateTurn() {
        myTurn = "unavailable";
        game.setCant(1);
        game.resetState();
        game.resetLevel();
        game.setCategory("");
        userPanel.resetStateDice();
    }
    private void reloadView() {
        getContentPane().revalidate();
        getContentPane().repaint();
    }
    private void demoGrand() {
        game.demoGrand();
        userPanel.setDiceValue(game.getDice(), game.getState());
        reloadView();
    }
//--------------------------------------------------------------------------
    @Override
    public void onReceiveId(String id) {

    }
    @Override
    public void onReceiveRegister(String[] register) {
        getContentPane().removeAll();
        getContentPane().add(userPanel);
        userPanel.setMyNickName(register[1]);
        getContentPane().add(messagePanel);
        reloadView();
    }
    @Override
    public void onReceiveLogin(String[] login) {
        getContentPane().removeAll();
        getContentPane().add(userPanel);
        userPanel.setMyNickName(login[1]);
        getContentPane().add(messagePanel);
        reloadView();
    }
    @Override
    public void onReceiveMessage(String[] message) {
        messagePanel.setNewMessage(message);
        reloadView();
    }
    @Override
    public void onReceiveGame(String[] gameData) {
        players.get(gameData[0]).setPlayerGameData(gameData);
        reloadView();
    }
    @Override
    public void onReceiveGameTurn(String turn) {
        this.myTurn = turn;
    }
    @Override
    public void onReceiveUserAdd(String[] users) {
        System.out.println("los usuarios a agregar son : " + Arrays.toString(users));
        IntStream.iterate(0, i -> i < users.length, i -> i + 2).forEach(i -> {
            playerPanel = new PlayerPanel(coordX,10);
            players.put(users[i], playerPanel);
            playerPanel.setMyNickName(users[i + 1]);
            getContentPane().add(playerPanel);
            coordX = coordX + 210;
        });
        reloadView();
    }
    @Override
    public void onReceiveRemoveUser(String[] data) {
        String[] auxMsj = new String[]{data[1],data[2]};
        for (String key : players.keySet()) {
            if (Objects.equals(key, data[0])) {
                players.get(key).setVisible(false);
                break;
            }
        }
        messagePanel.setNewMessage(auxMsj);
        reloadView();
    }

    @Override
    public void mouseClicked(MouseEvent e) {    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getSource() == userPanel.getDiceOne())
            setStateOnClick(userPanel.getDiceOne());
        if (e.getSource() == userPanel.getDiceTwo())
            setStateOnClick(userPanel.getDiceTwo());
        if (e.getSource() == userPanel.getDiceThree())
            setStateOnClick(userPanel.getDiceThree());
        if (e.getSource() == userPanel.getDiceFour())
            setStateOnClick(userPanel.getDiceFour());
        if (e.getSource() == userPanel.getDiceFive())
            setStateOnClick(userPanel.getDiceFive());
    }

    @Override
    public void mouseReleased(MouseEvent e) {   }

    @Override
    public void mouseEntered(MouseEvent e) {    }

    @Override
    public void mouseExited(MouseEvent e) {     }
//---------------------------------------------------------------------------------------
}
