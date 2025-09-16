import java.net.*;

public class UDPClient {
    DatagramSocket socket;
    InetAddress serverAddress;
    int serverPort = 7689;

    public UDPClient() throws Exception {
        socket = new DatagramSocket();
        serverAddress = InetAddress.getByName("localhost");

        // Start modtage-tråd
        Thread receiver = new Thread(() -> {
            byte[] buffer = new byte[1024];
            while(true){
                try{
                    DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                    socket.receive(packet);
                    String msg = new String(packet.getData(), 0, packet.getLength());
                    System.out.println("Ekko fra server: " + msg);
                } catch(Exception e){ e.printStackTrace(); }
            }
        });
        receiver.start();
    }

    public void sendMoves(String message) throws Exception {
        byte[] data = message.getBytes();
        DatagramPacket packet = new DatagramPacket(data, data.length, serverAddress, serverPort);
        socket.send(packet);
    }

    public static void main(String[] args) throws Exception {
        UDPClient client = new UDPClient();
        // Test om der kan sendes moves
        // client.sendMoves("MOVE|UP|9|4");
    }
}
