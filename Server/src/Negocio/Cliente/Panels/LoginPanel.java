package Negocio.Cliente.Panels;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

public class LoginPanel extends JPanel {

    private final JLabel[] labels = new JLabel[4];
    private final JButton[] buttons = new JButton[4];
    protected JTextField[] textFields = new JTextField[3];

    public LoginPanel() {
        setLayout(null);
        setBackground(new Color(0x3D6176));
        setBounds(180,70,400,400);
        inicialiceComp();
        components();
    }
    private void inicialiceComp(){
        Arrays.setAll(labels, i -> new JLabel());
        Arrays.setAll(buttons, i -> new JButton());
        Arrays.setAll(textFields, i -> new JTextField());
    }
    private void components() {
        labels[0].setText(" SingIn And Login ");
        labels[0].setFont(new Font("Verdana", Font.ITALIC, 16));
        labels[0].setBounds(120,5,200,30);
        add(labels[0]);
        buttons[0].setText("SignIn");
        buttons[0].setBounds(100,200,100,25);
        buttons[0].setBackground(new Color(0x99BFC6));
        buttons[0].setFont(new Font("Verdana", Font.ITALIC, 16));
        buttons[0].setBorderPainted(false);
        buttons[0].addActionListener(e -> loadSignIn());
        add(buttons[0]);
        buttons[1].setText("Login");
        buttons[1].setBounds(210,200,100,25);
        buttons[1].setBackground(new Color(0x99BFC6));
        buttons[1].setFont(new Font("Verdana", Font.ITALIC, 16));
        buttons[1].setBorderPainted(false);
        buttons[1].addActionListener(e -> loadLogin());
        add(buttons[1]);
    }
    private void loadSignIn() {
        removeAll();
        labels[1].setText("Nombre :");
        labels[1].setFont(new Font("Verdana", Font.ITALIC, 16));
        labels[1].setBounds(50,50,100,30);
        add(labels[1]);
        labels[2].setText("Contraseña :");
        labels[2].setFont(new Font("Verdana", Font.ITALIC, 16));
        labels[2].setBounds(50,90,120,30);
        add(labels[2]);
        labels[3].setText("Contraseña :");
        labels[3].setFont(new Font("Verdana", Font.ITALIC, 16));
        labels[3].setBounds(50,130,120,30);
        add(labels[3]);
        textFields[0].setBounds(210,50,150,30);
        add(textFields[0]);
        textFields[1].setBounds(210,90,150,30);
        add(textFields[1]);
        textFields[2].setBounds(210,130,150,30);
        add(textFields[2]);
        buttons[2].setText("SignIn");
        buttons[2].setBounds(160,180,100,25);
        buttons[2].setBackground(new Color(0x99BFC6));
        buttons[2].setFont(new Font("Verdana", Font.ITALIC, 16));
        buttons[2].setBorderPainted(false);
        add(buttons[2]);
        revalidate();
        repaint();
    }
    private void loadLogin() {
        removeAll();
        labels[1].setText("Nombre :");
        labels[1].setFont(new Font("Verdana", Font.ITALIC, 16));
        labels[1].setBounds(50,50,100,30);
        add(labels[1]);
        labels[2].setText("Contraseña :");
        labels[2].setFont(new Font("Verdana", Font.ITALIC, 16));
        labels[2].setBounds(50,90,120,30);
        add(labels[2]);
        textFields[0].setBounds(210,50,150,30);
        add(textFields[0]);
        textFields[1].setBounds(210,90,150,30);
        add(textFields[1]);
        buttons[3].setText("login");
        buttons[3].setBounds(160,180,100,25);
        buttons[3].setBackground(new Color(0x99BFC6));
        buttons[3].setFont(new Font("Verdana", Font.ITALIC, 16));
        buttons[3].setBorderPainted(false);
        add(buttons[3]);
        revalidate();
        repaint();
    }
    public JButton getButtonSignIn() {
        return buttons[2];
    }
    public JButton getButtonlogin() {
        return buttons[3];
    }
    public String getNickName() {
        return textFields[0].getText();
    }
    public String getPassword() {
        return textFields[1].getText();
    }
    public String getConfPassword() {
        return textFields[2].getText();
    }
}
