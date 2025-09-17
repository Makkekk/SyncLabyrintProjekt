import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

public class UDPClient {
    public static void main(String[] args) throws Exception {
        DatagramSocket socket = new DatagramSocket();
        InetAddress serverAddr = InetAddress.getByName("localhost");
        int port = 7689;

        BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));

        while (true) {
            System.out.print("Indtast besked eller skriv forlad: ");
            String msg = userInput.readLine();
            if (!msg.equalsIgnoreCase("forlad")) {

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
