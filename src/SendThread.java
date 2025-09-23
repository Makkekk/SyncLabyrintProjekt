import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.SocketAddress;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class SendThread extends Thread{
    BufferedReader in;
    DataOutputStream out;
    String message;
    List<Socket> clients = new ArrayList<>();

    public SendThread(Socket socket) {
        clients.add(socket);
        System.out.println("Klient med ip " + socket.getInetAddress() + " tilføjet");
    }

    public void run(){
        try{
            while(true){
                for (Socket client : clients) {
                    in = new BufferedReader(new InputStreamReader(client.getInputStream()));
                    out = new DataOutputStream(client.getOutputStream());

                    message = in.readLine();
                    System.out.println("Server modtog: " + message);
                    out.writeBytes(message + '\n');
                }
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
