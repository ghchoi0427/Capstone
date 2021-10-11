package web.view;

import web.BootWebApplication;

import javax.swing.*;
import java.awt.*;

public class MainScreen extends JFrame {

    public MainScreen() {
        super("Main");
        setBounds(100, 100, 400, 300);

        JPanel panel = new JPanel();
        Container container = this.getContentPane();
        JButton btnConnect = new JButton("show graph");
        JButton btnServer = new JButton("start server");

        btnConnect.addActionListener(e -> SwingUtilities.invokeLater(MainScreen::showGraph));

        btnServer.addActionListener(e -> {
            try {
                BootWebApplication.main(new String[]{"start"});
                showMessage("Notification", "Server has been started", JOptionPane.INFORMATION_MESSAGE);
            } catch (Exception ex) {
                showMessage("Warning", ex.getMessage(), JOptionPane.WARNING_MESSAGE);
            }
        });

        panel.add(btnConnect);
        panel.add(btnServer);
        container.add(panel);

        setVisible(true);
    }

    public static void showGraph() {
        GraphScreen graph = new GraphScreen("Time Series Chart");
        graph.setExtendedState(JFrame.MAXIMIZED_BOTH);
        graph.setLocationRelativeTo(null);
        graph.setVisible(true);
    }

    private void showMessage(String title, String message, int type) {
        JOptionPane.showMessageDialog(null, message, title, type);
    }
}
