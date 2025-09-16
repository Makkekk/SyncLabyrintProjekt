import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.*;

public class UDPClient {
    public static void main(String[] args) throws Exception {
        BufferedReader inFromUser = new BufferedReader(new InputStreamReader(System.in));

        DatagramSocket socket = new DatagramSocket();
        InetAddress ipAdress = InetAddress.getByName("localhost");
        byte[] recieveData = new byte[1024];
        byte[] sendData;

        String sentence = inFromUser.readLine();
        sendData = sentence.getBytes();

        DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, ipAdress, 7689);
        socket.send(sendPacket);

        DatagramPacket recievePacket = new DatagramPacket(recieveData, recieveData.length);
        socket.receive(recievePacket);

        socket.close();
    }
}
