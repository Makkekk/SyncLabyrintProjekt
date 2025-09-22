import javafx.scene.image.ImageView;

import java.io.*;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class TCPServer {
    private static List<Socket> clients = new ArrayList<>();
    private static List<Player> players = new ArrayList<>();

    private static String[] board = {    // 20x20
            "wwwwwwwwwwwwwwwwwwww",
            "w        ww        w",
            "w w  w  www w  w  ww",
            "w w  w   ww w  w  ww",
            "w  w               w",
            "w w w w w w w  w  ww",
            "w w     www w  w  ww",
            "w w     w w w  w  ww",
            "w   w w  w  w  w   w",
            "w     w  w  w  w   w",
            "w ww ww        w  ww",
            "w  w w    w    w  ww",
            "w        ww w  w  ww",
            "w         w w  w  ww",
            "w        w     w  ww",
            "w  w              ww",
            "w  w www  w w  ww ww",
            "w w      ww w     ww",
            "w   w   ww  w      w",
            "wwwwwwwwwwwwwwwwwwww"
    };


    // -------------------------------------------
    // | Maze: (0,0)              | Score: (1,0) |
    // |-----------------------------------------|
    // | boardGrid (0,1)          | scorelist    |
    // |                          | (1,1)        |
    // -------------------------------------------


    public static void main(String[] args) throws Exception {
        ServerSocket welcomeSocket = new ServerSocket(9000);

        players.add(new Player("Peter", 9, 4, "up"));

        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            clients.add(connectionSocket);
            new Thread(() -> haandterClient(connectionSocket)).start();

        }
    }


    private static void haandterClient(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String message;
            while ((message = in.readLine()) != null) {
                // Hver besked er f.eks. "Peter up"
                handleMove(message);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static synchronized void handleMove(String message) {
        String[] parts = message.split(" ");
        if (parts.length != 2) return;

        String name = parts[0];
        String direction = parts[1];

        int delta_x = 0, delta_y = 0;
        if (direction.equals("up")) delta_y = -1;
        if (direction.equals("down")) delta_y = 1;
        if (direction.equals("left")) delta_x = -1;
        if (direction.equals("right")) delta_x = 1;

        playerMoved(name, delta_x, delta_y, direction);
    }

    public static void playerMoved(String playerName, int delta_x, int delta_y, String direction) {
        Player newplayer = null;
        for (Player player : players) {
            if (Objects.equals(player.name, playerName)){
                newplayer = player;
            }
        }
        if (newplayer == null) return;

        newplayer.direction = direction;
        int x = newplayer.getXpos(), y = newplayer.getYpos();

        if (board[y+delta_y].charAt(x+delta_x)=='w') {
            newplayer.addPoints(-1);
        } else {
            Player p = getPlayerAt(x+delta_x, y+delta_y);
            if (p != null) {
                newplayer.addPoints(10);
                p.addPoints(-10);
            } else {
                newplayer.addPoints(1);
                x += delta_x;
                y += delta_y;
                newplayer.setXpos(x);
                newplayer.setYpos(y);
            }
        }

        // Send opdatering til alle klienter
        broadcast("UPDATE " + newplayer.name + " " +
                newplayer.getXpos() + " " +
                newplayer.getYpos() + " " +
                newplayer.direction + " " +
                newplayer.getPoint());
    }

    public static String getScoreList() {
        String result = "";
        for (Player p : players) {
            result += p + "\r\n";
        }
        return result;
    }

    public static Player getPlayerAt(int x, int y) {
        for (Player p : players) {
            if (p.getXpos()==x && p.getYpos()==y) {
                return p;
            }
        }
        return null;
    }

    private static void broadcast(String message) {
        for (Socket client : clients) {
            try {
                DataOutputStream out = new DataOutputStream(client.getOutputStream());
                out.writeBytes(message + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}



