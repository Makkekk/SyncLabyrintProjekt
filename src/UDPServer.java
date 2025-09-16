import java.io.IOException;
import java.net.*;

public class UDPServer {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket(7689);
        socket.setBroadcast(true);
        byte[] recieveData = new byte[1024];

        while (true) {
            DatagramPacket recievePacket = new DatagramPacket(recieveData, recieveData.length);
            socket.receive(recievePacket);

            String sentence = new String(recievePacket.getData(), 0, recievePacket.getLength());
            System.out.println("Modtaget: " + sentence + " fra " + recievePacket.getAddress());

            DatagramPacket sendPacket = new DatagramPacket(recievePacket.getData(), recievePacket.getLength(), InetAddress.getByName("255.255.255.255"), 9000);
            socket.send(sendPacket);
        }
    }
}
