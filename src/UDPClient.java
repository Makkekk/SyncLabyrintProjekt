import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class UDPClient {
    DatagramSocket socket;
    InetAddress serverAddress;

    public UDPClient() throws Exception {
        serverAddress = InetAddress.getByName("localhost");
        socket = new DatagramSocket(9000);
        socket.setBroadcast(true);
        (new RecieveThread(socket)).start();
    }

    public void sendMessage(String message) {
        byte[] sendData = message.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress,7689);
        try {
            socket.send(sendPacket);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
