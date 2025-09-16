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

            DatagramPacket sendPacket = new DatagramPacket(sentence.getBytes(), sentence.length(),packet.getAddress(), 7689);

            socket.send(sendPacket);
        }
    }
}
