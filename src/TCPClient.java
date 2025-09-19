import java.io.DataOutputStream;
import java.io.IOException;
import java.net.*;

public class TCPClient {
    Socket socket = new Socket("localhost", 9000);
//    InetAddress serverAddress;
    RecieveThread recieveThread;
    DataOutputStream out = new DataOutputStream(socket.getOutputStream());

    public TCPClient() throws Exception {
//        serverAddress = InetAddress.getByName("localhost");
        recieveThread = new RecieveThread(socket);
        recieveThread.start();
    }

    public void sendMessage(String message){
        try {
            out.writeBytes(message + "\n");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
