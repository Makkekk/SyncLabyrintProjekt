import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;

public class UDPClient {
    DatagramSocket socket;
    InetAddress serverAddress;

    public UDPClient() throws Exception {
        serverAddress = InetAddress.getByName("localhost");
        socket = new DatagramSocket();
        socket.setBroadcast(true);
    }

    public void sendMessage(String message) throws IOException {
        byte[] sendData = message.getBytes();
        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddress,7689);
        socket.send(sendPacket);
    }
}
