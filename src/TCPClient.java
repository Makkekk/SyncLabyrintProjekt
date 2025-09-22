import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

import java.io.*;
import java.net.*;


public class TCPClient {
    private Socket socket;
    private DataOutputStream out;
    private BufferedReader in;
    private GUI gui;
    private String playerName;

    public TCPClient(String host, int port, GUI gui,String playerName) throws IOException {
        this.gui = gui;
        this.playerName = playerName;
        socket = new Socket(host, port);
        out = new DataOutputStream(socket.getOutputStream());
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        sendMessage(playerName);

        // Start dedicated receive thread
        new RecieveThread(socket, gui).start();
    }

    public void sendMessage(String message) {
        try {
            out.writeBytes(message + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}