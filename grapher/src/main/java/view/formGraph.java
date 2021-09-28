package view;

import javax.swing.*;
import java.awt.*;

public class formGraph extends JFrame {

    private JButton btnConnect;

    public formGraph() {
        super("graph");
        setBounds(100, 100, 400, 300);

        Container container = this.getContentPane();
        JPanel panel = new JPanel();
        btnConnect = new JButton("connect");
        btnConnect.setBounds(0,0,50,50);
        btnConnect.setMnemonic('S');

        panel.add(btnConnect);
        container.add(panel);

        setVisible(true);
    }
}
