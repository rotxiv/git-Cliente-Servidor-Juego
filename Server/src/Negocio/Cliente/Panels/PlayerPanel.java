package Negocio.Cliente.Panels;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class PlayerPanel extends JPanel {

    JPanel gridPanel =new JPanel();
    JLabel[] labels = new JLabel[9];
    JLabel nickName = new JLabel();

    public PlayerPanel(int valX, int valY) {
        setLayout(null);
        setBackground(new Color(0x3D6176));
        setBounds(valX,valY,200,220);
        inicialiceComp();
        components();
        setVisible(true);
    }
    private void inicialiceComp() {
        Arrays.setAll(labels, i -> new JLabel());
        for (JLabel label : labels)
            gridPanel.add(label);
    }
    private void components() {
        gridPanel.setLayout(new GridLayout(3,3,5,5));
        gridPanel.setBackground(new Color(0x3D6176));
        gridPanel.setBounds(0,0,200,200);
        add(gridPanel);
        nickName.setBounds(0,200,200,20);
        nickName.setFont(new Font("Verdana", Font.ITALIC, 12));
        nickName.setHorizontalAlignment(SwingConstants.CENTER);
        nickName.setBackground(new Color(0x99BFC6));
        nickName.setOpaque(true);
        add(nickName);
//----------------------------------------------------------------------------------
        labels[0].setFont(new Font("Verdana", Font.ITALIC, 16));
        labels[0].setHorizontalAlignment(SwingConstants.CENTER);
        labels[0].setBackground(new Color(0x99BFC6));
        labels[0].setOpaque(true);

        labels[1].setFont(new Font("Verdana", Font.ITALIC, 16));
        labels[1].setHorizontalAlignment(SwingConstants.CENTER);
        labels[1].setBackground(new Color(0x99BFC6));
        labels[1].setOpaque(true);

        labels[2].setFont(new Font("Verdana", Font.ITALIC, 16));
        labels[2].setHorizontalAlignment(SwingConstants.CENTER);
        labels[2].setBackground(new Color(0x99BFC6));
        labels[2].setOpaque(true);

        labels[3].setFont(new Font("Verdana", Font.ITALIC, 16));
        labels[3].setHorizontalAlignment(SwingConstants.CENTER);
        labels[3].setBackground(new Color(0x99BFC6));
        labels[3].setOpaque(true);

        labels[4].setFont(new Font("Verdana", Font.ITALIC, 16));
        labels[4].setHorizontalAlignment(SwingConstants.CENTER);
        labels[4].setBackground(new Color(0x99BFC6));
        labels[4].setOpaque(true);

        labels[5].setFont(new Font("Verdana", Font.ITALIC, 16));
        labels[5].setHorizontalAlignment(SwingConstants.CENTER);
        labels[5].setBackground(new Color(0x99BFC6));
        labels[5].setOpaque(true);

        labels[6].setFont(new Font("Verdana", Font.ITALIC, 16));
        labels[6].setHorizontalAlignment(SwingConstants.CENTER);
        labels[6].setBackground(new Color(0x99BFC6));
        labels[6].setOpaque(true);

        labels[7].setFont(new Font("Verdana", Font.ITALIC, 16));
        labels[7].setHorizontalAlignment(SwingConstants.CENTER);
        labels[7].setBackground(new Color(0x99BFC6));
        labels[7].setOpaque(true);

        labels[8].setFont(new Font("Verdana", Font.ITALIC, 16));
        labels[8].setHorizontalAlignment(SwingConstants.CENTER);
        labels[8].setBackground(new Color(0x99BFC6));
        labels[8].setOpaque(true);

    }
    public void setMyNickName(String nickName) {
        this.nickName.setText(nickName);
        revalidate();
        repaint();
    }
    public void setPlayerGameData(String[] data) {
        for (int i = 0; i < labels.length; i++) {
            labels[i].setText(data[i + 2]);
        }
    }

    /*public static void main(String[] args) {
        JFrame frame = new JFrame();
        frame.setSize(500,500);
        frame.setLayout(null);
        PlayerPanel playerPanel = new PlayerPanel(20,20);
        frame.add(playerPanel);
        frame.setVisible(true);
    }*/

}
