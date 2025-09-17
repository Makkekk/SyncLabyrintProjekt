import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;

public class UDPServer {

    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(7689);
        byte[] buffer = new byte[1024];
        System.out.println("Server lytter på port 7689...");

        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);

            String msg = new String(packet.getData(), 0, packet.getLength());
            System.out.println("Modtaget: " + msg + " fra " + packet.getAddress());

            // Send ekko tilbage til afsender
            DatagramPacket sendPacket = new DatagramPacket(
                    msg.getBytes(), msg.length(),
                    packet.getAddress(), packet.getPort()
            );
            socket.send(sendPacket);
        }
    }
}
