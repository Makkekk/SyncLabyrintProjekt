import java.io.IOException;
import java.net.*;

public class UDPServer {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(7689);
        socket.setBroadcast(true);
        byte[] buffer = new byte[1024];

        System.out.println("Server lytter på port 7689");

        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            String sentence = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Modtaget: " + sentence + " fra " + packet.getAddress());

            // Echo back to the client on the port it came from
            DatagramPacket sendPacket = new DatagramPacket(packet.getData(), packet.getLength(), InetAddress.getByName("255.255.255.255"), 7689);

            socket.send(sendPacket);
        }
    }
}
