package Negocio.Cliente.Panels;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class MessagePanel extends JPanel {

    JLabel nickName;
    JLabel message;

    private int val = 0;

    public MessagePanel() {
        setLayout(null);
        setBackground(new Color(0x99BFC6));
        setBounds(635,10,345,540);
    }
    public void setNewMessage(String[] message) {
        this.nickName = new JLabel();
        this.nickName.setFont(new Font("Verdana", Font.ITALIC, 16));
        this.nickName.setBounds(0,val,345,25);
        this.nickName.setText(message[0]);
        add(this.nickName);
        val = val + 25;
        this.message = new JLabel();
        this.message.setFont(new Font("Verdana", Font.ITALIC, 16));
        this.message.setBounds(0,val,345,25);
        this.message.setText(message[1]);
        add(this.message);
        val = val + 25;
        revalidate();
        repaint();
    }
    private void test() {
        String[] message = new String[]{"Soy el Usuario : ", " Soy el mensaje "};
        for (int i = 0 ; i < 3; i++)
            setNewMessage(message);
    }
}
