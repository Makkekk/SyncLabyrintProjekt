import java.net.*;

public class UDPClient {
    private DatagramSocket socket;
    private InetAddress serverAddress;
    private int serverPort = 7689;

    public UDPClient() throws Exception {
        socket = new DatagramSocket();
        serverAddress = InetAddress.getByName("localhost");

        // Start receivertråd
        ModtagerTråd receiver = new ModtagerTråd(socket);
        receiver.start();
    }

    public void send(String message) {
        SenderTråd sender = new SenderTråd(socket, serverAddress, serverPort, message);
        sender.start();
    }
}