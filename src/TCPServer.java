import java.net.*;

public class TCPServer {
    public static void main(String[] args) throws Exception {
        ServerSocket welcomeSocket = new ServerSocket(9000);
        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            (new SendThread(connectionSocket)).start();
//            (new RecieveThread(connectionSocket)).start();
        }
    }
}
