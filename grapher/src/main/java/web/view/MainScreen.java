package web.view;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JFrame {

    public MainScreen() {
        super("Main");
        setBounds(100, 100, 400, 300);

        JPanel panel = new JPanel();
        Container container = this.getContentPane();
        JButton btnConnect = new JButton("connect");
        JTextField textAddress = new JTextField(15);

        btnConnect.setBounds(0, 0, 50, 50);
        btnConnect.addActionListener(e -> {
            SwingUtilities.invokeLater(() -> {
                GraphScreen graph = new GraphScreen("Time Series Chart");
                graph.setSize(800, 400);
                graph.setLocationRelativeTo(null);
                graph.setVisible(true);
                graph.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            });
            setVisible(false);
        });

        panel.add(textAddress);
        panel.add(btnConnect);
        container.add(panel);

        setVisible(true);
    }

    private void showMessage(String title, String message, int type) {
        JOptionPane.showMessageDialog(null, message, title, type);
    }
}
