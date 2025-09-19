import javafx.application.Platform;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.Objects;

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
                String[] messageArray = message.split(" ");
                if (message!= null){
                    if (Objects.equals(messageArray[1], "up")) {
                        Platform.runLater(() -> GUI.playerMoved(messageArray[0], 0, -1, "up"));
                    } else if (Objects.equals(messageArray[1], "down")) {
                        Platform.runLater(() -> GUI.playerMoved(messageArray[0], 0, +1, "down"));
                    } else if (Objects.equals(messageArray[1], "left")) {
                        Platform.runLater(() -> GUI.playerMoved(messageArray[0], -1, 0, "left"));
                    } else if (Objects.equals(messageArray[1], "right")) {
                        Platform.runLater(() -> GUI.playerMoved(messageArray[0], +1, 0, "right"));
                    }
                }
            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
    }
}
