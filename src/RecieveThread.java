import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;

public class RecieveThread extends Thread{
    Socket socket;
    String message;

    public RecieveThread(Socket socket) throws Exception {
        this.socket = socket;
    }

    public void run() {
        try {
            BufferedReader inFromAfsender = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Forbundet til server.");

            while (true) {
                message = inFromAfsender.readLine();
                System.out.println("From sender: " + message);
            }

        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
