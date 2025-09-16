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

                // send besked
                byte[] sendData = msg.getBytes();
                DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length, serverAddr, port);
                socket.send(sendPacket);

                // modtag echo
                byte[] buffer = new byte[1024];
                DatagramPacket receivePacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(receivePacket);

                String echo = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("FROM SERVER: " + echo);
            }

            socket.close();
        }
    }
}
