import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class Client {
    private Socket socket;
    private BufferedReader br;
    private String ip;
    private int port;

    public Client(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void startConnection() {
        try {
            socket = new Socket(ip, port);
            System.out.println("connected to: " + socket.getInetAddress().getHostAddress());

            br = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            while (true) {
                System.out.println(br.readLine());
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (socket != null) {
                    socket.close();
                }
                if (br != null) {
                    br.close();
                }
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }
    }
}