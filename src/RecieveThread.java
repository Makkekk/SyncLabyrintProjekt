import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.Socket;
import java.net.SocketException;

public class RecieveThread extends Thread{
    DatagramSocket socket;
    String message;

    public RecieveThread(DatagramSocket socket) throws Exception {
        this.socket = socket;
        socket.setBroadcast(true);
    }

    public void run() {
        try {
            while (true){
                byte[] recieveData = new byte[1024];
                DatagramPacket recievePacket = new DatagramPacket(recieveData, recieveData.length);
                socket.receive(recievePacket);
                message = new String(recievePacket.getData(), 0, recievePacket.getLength());
                System.out.println(message);
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}
