import javafx.application.Platform;

import java.net.*;


public class SenderTråd extends Thread {
    private DatagramSocket socket;
    private InetAddress serverAddress;
    private int serverPort;
    private String message;


    public SenderTråd(DatagramSocket socket, InetAddress serverAddress, int serverPort, String message) {
        this.socket = socket;
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
        this.message = message;

    }

    @Override
    public void run() {
        try {
            byte[] data = message.getBytes();
            DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, serverPort);
            socket.send(packet);
            System.out.println("Sendt: " + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}