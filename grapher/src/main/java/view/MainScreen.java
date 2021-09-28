package view;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JFrame {

    public MainScreen() {
        super("graph");
        setBounds(100, 100, 400, 300);

        JPanel panel = new JPanel();
        Container container = this.getContentPane();
        JButton btnConnect = new JButton("connect");
        JTextField textAddress = new JTextField(15);

        btnConnect.setBounds(0, 0, 50, 50);
        btnConnect.addActionListener(e -> {

        });

        panel.add(textAddress);
        panel.add(btnConnect);
        container.add(panel);

        setVisible(true);
    }

    private void showMessage(String title, String message, int type){
        JOptionPane.showMessageDialog(null, message,title, type);
    }
}
