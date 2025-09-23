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
    private String myName;
    private RecieveThread receiveThread;


    public TCPClient(String host, int port, GUI gui,String myName) throws IOException {
        this.myName = myName;
        socket = new Socket(host, port);
        out = new DataOutputStream(socket.getOutputStream());
        out.writeBytes(myName + "\n");

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