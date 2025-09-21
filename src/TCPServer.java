import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class TCPServer {
    private static List<Socket> clients = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        ServerSocket welcomeSocket = new ServerSocket(9000);
        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            clients.add(connectionSocket);
            new Thread(() -> håndterClient(connectionSocket).start();
        }
    }
    private static void håndterClient(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Server modtog: " + message);
                broadcast(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void broadcast(String message) {
        for (Socket client : clients) {
            try {
                DataOutputStream out = new DataOutputStream(client.getOutputStream());
                out.writeBytes(message + "\n");
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
}
