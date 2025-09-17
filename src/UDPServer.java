import java.net.*;


public class UDPServer {

        public static void main(String[] args) throws Exception {
            DatagramSocket socket = new DatagramSocket(7689);
            byte[] buffer = new byte[1024];
            System.out.println("Server lytter på port 7689...");

            while (true) {
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
                socket.receive(packet);

                String msg = new String(packet.getData(), 0, packet.getLength());
                System.out.println("Modtaget: " + msg);

                // ekko tilbage til klienten
                DatagramPacket reply = new DatagramPacket(
                        msg.getBytes(),
                        msg.length(),
                        packet.getAddress(),
                        packet.getPort()
                );
                socket.send(reply);
            }
        }
    }