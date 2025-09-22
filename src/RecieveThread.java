import javafx.application.Platform;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class RecieveThread extends Thread {
    private Socket socket;
    private GUI gui;

    public RecieveThread(Socket socket, GUI gui) {
        this.socket = socket;
        this.gui = gui;
    }

    @Override
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("From server: " + message);

                if (message.startsWith("UPDATE")) {
                    String[] parts = message.split(" ");
                    String name = parts[1];
                    int x = Integer.parseInt(parts[2]);
                    int y = Integer.parseInt(parts[3]);
                    String direction = parts[4];
                    int points = Integer.parseInt(parts[5]);

                    // Push to JavaFX thread
                    Platform.runLater(() -> gui.updatePlayer(name, x, y, direction, points));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
