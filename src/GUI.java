

import java.util.*;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.text.*;

public class GUI extends Application {

	public static final int size = 20;
	public static final int scene_height = size * 20 + 100;
	public static final int scene_width = size * 20 + 200;

	public static Image image_floor;
	public static Image image_wall;
	public static Image hero_right,hero_left,hero_up,hero_down;

	public static Player me;
    public static List<Player> players = new ArrayList<>();

	private static Label[][] fields;
	private static TextArea scoreList;

    private TCPClient client;

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


	@Override
	public void start(Stage primaryStage) {
		try {
            String myName = "Peter";
            me = new Player(myName,0,0,"up");
            players.add(me);
            client = new TCPClient("192.168.0.210",9000,this,myName);

			GridPane grid = new GridPane();
			grid.setHgap(10);
			grid.setVgap(10);
			grid.setPadding(new Insets(0, 10, 0, 10));

			Text mazeLabel = new Text("Maze:");
			mazeLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

			Text scoreLabel = new Text("Score:");
			scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 20));

			scoreList = new TextArea();

			GridPane boardGrid = new GridPane();

			image_wall  = new Image(getClass().getResourceAsStream("Image/wall4.png"),size,size,false,false);
			image_floor = new Image(getClass().getResourceAsStream("Image/floor1.png"),size,size,false,false);

			hero_right  = new Image(getClass().getResourceAsStream("Image/heroRight.png"),size,size,false,false);
			hero_left   = new Image(getClass().getResourceAsStream("Image/heroLeft.png"),size,size,false,false);
			hero_up     = new Image(getClass().getResourceAsStream("Image/heroUp.png"),size,size,false,false);
			hero_down   = new Image(getClass().getResourceAsStream("Image/heroDown.png"),size,size,false,false);

			fields = new Label[20][20];
			for (int j=0; j<20; j++) {
				for (int i=0; i<20; i++) {
					switch (board[j].charAt(i)) {
					case 'w':
						fields[i][j] = new Label("", new ImageView(image_wall));
						break;
					case ' ':
						fields[i][j] = new Label("", new ImageView(image_floor));
						break;
					default: throw new Exception("Illegal field value: "+board[j].charAt(i) );
					}
					boardGrid.add(fields[i][j], i, j);
				}
			}
			scoreList.setEditable(false);


			grid.add(mazeLabel,  0, 0);
			grid.add(scoreLabel, 1, 0);
			grid.add(boardGrid,  0, 1);
			grid.add(scoreList,  1, 1);

			Scene scene = new Scene(grid,scene_width,scene_height);
			primaryStage.setScene(scene);
			primaryStage.show();

			scene.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
				switch (event.getCode()) {
				case UP:
					client.sendMessage(me.name + " up");
//					playerMoved(0,-1,"up");
					break;
				case DOWN:
					client.sendMessage(me.name + " down");
//					playerMoved(0,+1,"down");
					break;
				case LEFT:
					client.sendMessage(me.name + " left");
//					playerMoved(-1,0,"left");
					break;
				case RIGHT:
					client.sendMessage(me.name + " right");
//					playerMoved(+1,0,"right");
					break;
				default: break;
				}
			});

		} catch(Exception e) {
			e.printStackTrace();
		}
	}

    // Modtag update fra server og opdater GUI
    public static void updatePlayer(String name, int x, int y, String direction, int points) {
            Player player = getPlayerByName(name);
            if (player == null) {
                player = new Player(name, x, y, direction);
                players.add(player);
            }

            // Fjern gammel position
            fields[player.getXpos()][player.getYpos()].setGraphic(new ImageView(image_floor));

            // Opdater data
            player.setXpos(x);
            player.setYpos(y);
            player.direction = direction;
            player.setPoint(points);

            if (direction.equals("up")) {
                fields[x][y].setGraphic(new ImageView(hero_up));
            } else if (direction.equals("down")) {
                fields[x][y].setGraphic(new ImageView(hero_down));
            } else if (direction.equals("left")) {
                fields[x][y].setGraphic(new ImageView(hero_left));
            } else if (direction.equals("right")) {
                fields[x][y].setGraphic(new ImageView(hero_right));
            }
            scoreList.setText(getScoreList());
        }

    public static Player getPlayerByName(String name) {
        for (Player p : players) {
            if (Objects.equals(p.name, name)) return p;
        }
        return null;
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
}

