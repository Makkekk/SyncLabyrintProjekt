import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.List;


public class UDPServer {
    private List<InetSocketAddress> clients = new ArrayList<>();


    public void start() throws IOException {

        DatagramSocket socket = new DatagramSocket(7689);
        byte[] buffer = new byte[1024];
        System.out.println("Server lytter på port 7689...");

        while (true) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            socket.receive(packet);
            InetSocketAddress clientAddress = new InetSocketAddress(packet.getAddress(), packet.getPort());
            if (!clients.contains(clientAddress)) {
                clients.add(clientAddress);
                System.out.println("Ny client tilsluttet: med IP: " + clientAddress);
            }
            for (InetSocketAddress client : clients) {
                DatagramPacket reply = new DatagramPacket(packet.getData(), packet.getLength(), client.getAddress(), client.getPort());

                socket.send(reply);
            }

        }
    }

    public static void main(String[] args) {
        try {
            UDPServer server = new UDPServer();
            server.start();
        }catch (IOException e){
            System.err.println("Serveren kunne ikke starte: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

