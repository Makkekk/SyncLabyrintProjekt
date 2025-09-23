import java.io.*;
import java.net.*;
import java.util.*;

public class TCPServer {
    private static List<Socket> clients = new ArrayList<>();
    private static List<Player> players = new ArrayList<>();

    public static final String[] board = {    // 20x20
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

    public static void main(String[] args) throws Exception {
        ServerSocket welcomeSocket = new ServerSocket(9000);
        System.out.println("Server running on port 9000...");

        while (true) {
            Socket connectionSocket = welcomeSocket.accept();
            clients.add(connectionSocket);
            new SendThread(connectionSocket).start();
        }
    }

    public static void registerPlayer(String playerName) {
        int startX = 1, startY = 1;

        boolean found = false;

        for (int y = 1; y < 20 && !found; y++) {
            for (int x = 1; x < 20; x++) {
                if (board[y].charAt(x) == ' ' && getPlayerAt(x, y) == null) {
                    startX = x;
                    startY = y;
                    found = true;
                    break; // break inner loop
                }
            }
        }
        players.add(new Player(playerName, startX, startY, "up"));
        broadcast("UPDATE " + playerName + " " + startX + " " + startY + " up 0");
    }

    public static void handleMove(String message) {
        String[] parts = message.split(" ");
        if (parts.length != 2) return;

        String name = parts[0];
        String direction = parts[1];

        int dx = 0, dy = 0;
        if (direction.equals("up")) dy = -1;
        if (direction.equals("down")) dy = 1;
        if (direction.equals("left")) dx = -1;
        if (direction.equals("right")) dx = 1;

        playerMoved(name, dx, dy, direction);
    }

    public static void playerMoved(String playerName, int dx, int dy, String direction) {
        Player p = getPlayerByName(playerName);
        if (p == null) return;

        p.direction = direction;
        int x = p.getXpos(), y = p.getYpos();

        if (board[y + dy].charAt(x + dx) == 'w') {
            p.addPoints(-1);
        } else {
            Player other = getPlayerAt(x + dx, y + dy);
            if (other != null) {
                p.addPoints(10);
                other.addPoints(-10);
            } else {
                p.addPoints(1);
                x += dx;
                y += dy;
                p.setXpos(x);
                p.setYpos(y);
            }
        }

        broadcast("UPDATE " + p.name + " " + p.getXpos() + " " + p.getYpos() + " " + p.direction + " " + p.getPoint());
    }

    private static Player getPlayerByName(String name) {
        for (Player p : players) {
            if (Objects.equals(p.name, name)) return p;
        }
        return null;
    }

    private static Player getPlayerAt(int x, int y) {
        for (Player p : players) {
            if (p.getXpos() == x && p.getYpos() == y) return p;
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