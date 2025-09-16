import java.io.IOException;
import java.net.*;

public class UDPServer {
    public static void main(String[] args) throws IOException {
        DatagramSocket socket = new DatagramSocket();
        socket.setBroadcast(true);

        byte[] reciveData = new byte[1024];
        byte[] sendData;

        while (true){
            DatagramPacket receivePacket = new DatagramPacket(reciveData, reciveData.length);
            socket.receive(receivePacket);
            String sentence = new String(receivePacket.getData());
            InetAddress IpAdress = InetAddress.getByName("255.255.255.255");
            int port = receivePacket.getPort();
            sendData = sentence.getBytes();
            DatagramPacket sendpacket = new DatagramPacket(sendData, sendData.length, IpAdress, port);
            socket.send(sendpacket);
        }
    }
}
