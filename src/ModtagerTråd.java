import java.net.*;
import javafx.application.Platform;

public class ModtagerTråd extends Thread {
    private DatagramSocket socket;

    public ModtagerTråd(DatagramSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            byte[] buffer = new byte[1024];
            while (true) {
                DatagramPacket reply = new DatagramPacket(buffer, buffer.length);
                socket.receive(reply);
                String response = new String(reply.getData(), 0, reply.getLength());

                // Opdater GUI på JavaFX-tråden
                Platform.runLater(() -> {
                    GUI.addMessage(response);
                });

                System.out.println("Modtaget: " + response);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
