package Negocio.Cliente.Panels;


import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class UserPanel extends JPanel {

    private static final String[] cat = new String[]{"Uno","Dos","Tres","Cuatro","Cinco","Seis","Escalera","Full","Poker","Grand"};
    protected static JComboBox<String> categories = new JComboBox<>(cat);

    private final JLabel[] dice = new JLabel[5];
    private final JTextArea message = new JTextArea(3,1);
    private final JButton game = new JButton();
    private final JButton endGame = new JButton();
    private final JButton selectDice = new JButton();
    private PlayerPanel myBoard;

    public UserPanel() {
        setLayout(null);
        setBackground(new Color(0x3D6176));
        setBounds(10,250,620,300);
        initializeComponents();
        components();
    }
    private void initializeComponents() {
        myBoard = new PlayerPanel(10,75);
        add(myBoard);
        Arrays.setAll(dice, i -> new JLabel());
        for (JLabel label : dice)
            add(label);
    }
    private void components() {
        dice[0].setBounds(25, 5, 100, 60);
        dice[0].setFont(new Font("Verdana", Font.ITALIC, 16));
        dice[0].setHorizontalAlignment(SwingConstants.CENTER);
        dice[0].setText("D-Uno");
        dice[0].setBackground(new Color(0x99BFC6));
        dice[0].setOpaque(true);
        add(dice[0]);
        dice[1].setBounds(135, 5, 100, 60);
        dice[1].setFont(new Font("Verdana", Font.ITALIC, 16));
        dice[1].setHorizontalAlignment(SwingConstants.CENTER);
        dice[1].setText("D-Dos");
        dice[1].setBackground(new Color(0x99BFC6));
        dice[1].setOpaque(true);
        add(dice[1]);
        dice[2].setBounds(245, 5, 100, 60);
        dice[2].setFont(new Font("Verdana", Font.ITALIC, 16));
        dice[2].setHorizontalAlignment(SwingConstants.CENTER);
        dice[2].setText("D-Tres");
        dice[2].setBackground(new Color(0x99BFC6));
        dice[2].setOpaque(true);
        add(dice[2]);
        dice[3].setBounds(355, 5, 100, 60);
        dice[3].setFont(new Font("Verdana", Font.ITALIC, 16));
        dice[3].setHorizontalAlignment(SwingConstants.CENTER);
        dice[3].setText("D-Cuatro");
        dice[3].setBackground(new Color(0x99BFC6));
        dice[3].setOpaque(true);
        add(dice[3]);
        dice[4].setBounds(465, 5, 100, 60);
        dice[4].setFont(new Font("Verdana", Font.ITALIC, 16));
        dice[4].setHorizontalAlignment(SwingConstants.CENTER);
        dice[4].setText("D-Cinco");
        dice[4].setBackground(new Color(0x99BFC6));
        dice[4].setOpaque(true);
        add(dice[4]);
        message.setBounds(250,200,320,80);
        message.setFont(new Font("Verdana", Font.ITALIC, 16));
        message.setBackground(new Color(0xC4DDDE));
        message.setLineWrap(true);
        message.setEditable(false);
        message.setOpaque(true);
        add(message);
        game.setBounds(250,100,150,40);
        game.setText("Jugar");
        game.setFont(new Font("Verdana", Font.ITALIC, 16));
        game.setBackground(new Color(0x7697A0));
        game.setForeground(new Color(0xC4DDDE));
        game.setBorderPainted(false);
        game.setOpaque(true);
        add(game);
        selectDice.setBounds(250,150,150,40);
        selectDice.setText("DemoGrand");
        selectDice.setFont(new Font("Verdana", Font.ITALIC, 16));
        selectDice.setBackground(new Color(0x7697A0));
        selectDice.setForeground(new Color(0xC4DDDE));
        selectDice.setOpaque(true);
        add(selectDice);
        endGame.setBounds(420,150,150,40);
        endGame.setText("Terminar");
        endGame.setFont(new Font("Verdana", Font.ITALIC, 16));
        endGame.setBackground(new Color(0x7697A0));
        endGame.setForeground(new Color(0xC4DDDE));
        endGame.setOpaque(true);
        add(endGame);
        categories.setBounds(420,100,150,30);
        add(categories);
    }
    public void setMyNickName(String val) {
        myBoard.setMyNickName(val);
        reloadAll();
    }
    public void setNewMessage(String msj) {
        message.setText(msj);
        reloadAll();
    }
    public String getMyNickName() {
        return myBoard.nickName.getText();
    }
    public JButton getGame() {
        return game;
    }
    public JButton getEndGame() {
        return endGame;
    }

    public JButton getSelectDice() {
        return selectDice;
    }

    public JComboBox getOptionCategories() {
        return categories;
    }
    public JLabel[] getDice() {
        return dice;
    }
    public JLabel getDiceOne() {
        return dice[0];
    }
    public JLabel getDiceTwo() {
        return dice[1];
    }
    public JLabel getDiceThree() {
        return dice[2];
    }
    public JLabel getDiceFour() {
        return dice[3];
    }
    public JLabel getDiceFive() {
        return dice[4];
    }
    public void setDiceValue(Integer[] valDice, int[] valState) {
        for (int i = 0; i < valState.length; i++) {
            if (valState[i] == 0)
                dice[i].setText(String.valueOf(valDice[i]));
        }
        reloadAll();
    }
    public void setFinalValue(String[] finalValue) {
        for (int i = 0; i < finalValue.length - 1; i++)
            myBoard.labels[i].setText(finalValue[i]);
        reloadAll();
    }
    public void resetStateDice() {
        for ( JLabel j : dice) {
            j.setText("0");
            j.setBackground(new Color(0x99BFC6));
        }
        reloadAll();
    }
    private void reloadAll() {
        revalidate();
        repaint();
    }

    /*public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setLayout(null);
        frame.setSize(1000,600);
        UserPanel userPanel = new UserPanel();
        PlayerPanel playerPanel = new PlayerPanel(10,10);
        PlayerPanel playerPanel1 = new PlayerPanel(220,10);
        PlayerPanel playerPanel2 = new PlayerPanel(430,10);
        MessagePanel messagePanel = new MessagePanel();
        frame.add(userPanel);
        frame.add(playerPanel);
        frame.add(playerPanel1);
        frame.add(playerPanel2);
        frame.add(messagePanel);
        frame.setVisible(true);
    }*/
}
