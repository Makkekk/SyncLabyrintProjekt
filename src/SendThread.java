import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SendThread extends Thread {
    private Socket socket;

    public SendThread(Socket socket) {
        this.socket = socket;
        System.out.println("Klient med ip " + socket.getInetAddress() + " tilføjet");
    }

    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String message;

            //første besked = playername
            String playerName = in.readLine();
            if (playerName == null) return;

            TCPServer.registerPlayer(playerName);

            while ((message = in.readLine()) != null) {
                System.out.println("Server modtog: " + message);
                // Process the move
                TCPServer.handleMove(message);
            }
        } catch (IOException e) {
            System.out.println("Client disconnected: " + socket.getInetAddress());
        }

    }
}
