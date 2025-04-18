import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.animation.AnimationTimer;

import java.io.*;
import java.util.ArrayList;
import java.util.Random;

public class Main extends Application {

    public static final int CANVAS_WIDTH = 800;
    public static final int CANVAS_HEIGHT = 600;

    private ArrayList<Mine> mines = new ArrayList<>();
    private Player player;
    private Image background;
    private boolean gameOver = false;
    private int highScore = 0;

    private double lastX = 0;
    private double lastY = 0;

    @Override
    public void start(Stage stage) {
        Canvas canvas = new Canvas(CANVAS_WIDTH, CANVAS_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Pane root = new Pane(canvas);
        Scene scene = new Scene(root);

        player = new Player(CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2);  // Start the player centered
        background = new Image("stars.png");

        loadHighScore();

        scene.setOnKeyPressed(e -> player.handleKeyPress(e.getCode().toString()));
        scene.setOnKeyReleased(e -> player.handleKeyRelease(e.getCode().toString()));

        new AnimationTimer() {
            public void handle(long now) {
                if (!gameOver) {
                    player.act();
                    updateMines();
                    player.updateScore();
                    player.checkForReturnToStart();
                    checkCollisions();
                }
                draw(gc);
            }
        }.start();

        stage.setTitle("Mine Avoider");
        stage.setScene(scene);
        stage.show();
    }

    private void updateMines() {
        double playerX = player.getX();
        double playerY = player.getY();

        if (mines.size() < 25) {
            Random rand = new Random();
            double mineX = playerX + rand.nextInt(1600) - 800;
            double mineY = playerY + rand.nextInt(1200) - 600;
            mines.add(new Mine(mineX, mineY));
        }
    }

    private void checkCollisions() {
        for (Mine mine : mines) {
            if (mine.collidesWith(player)) {
                gameOver = true;
                if (player.getScore() > highScore) {
                    highScore = player.getScore();
                    saveHighScore();
                }
                break;
            }
        }
    }

    private void draw(GraphicsContext gc) {
        gc.drawImage(background, 0, 0, CANVAS_WIDTH, CANVAS_HEIGHT);
        if (!gameOver) {
            for (Mine mine : mines) {
                mine.draw(gc, player.getX(), player.getY());
            }
            player.draw(gc, CANVAS_WIDTH / 2, CANVAS_HEIGHT / 2);  // Always center player on canvas
            gc.setFill(Color.WHITE);
            gc.setFont(new Font(20));
            gc.fillText("Score: " + player.getScore(), 10, 25);
            gc.fillText("Highscore: " + highScore, 10, 50);
        } else {
            gc.setFill(Color.RED);
            gc.setFont(new Font(40));
            gc.fillText("GAME OVER", CANVAS_WIDTH / 2.0 - 100, CANVAS_HEIGHT / 2.0);
            gc.setFont(new Font(20));
            gc.fillText("Final Score: " + player.getScore(), CANVAS_WIDTH / 2.0 - 70, CANVAS_HEIGHT / 2.0 + 30);
            gc.fillText("Highscore: " + highScore, CANVAS_WIDTH / 2.0 - 70, CANVAS_HEIGHT / 2.0 + 60);
        }
    }

    private void loadHighScore() {
        File file = new File("highscore.txt");
        if (file.exists()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                highScore = Integer.parseInt(reader.readLine().trim());
            } catch (Exception e) {
                highScore = 0;
            }
        }
    }

    private void saveHighScore() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("highscore.txt"))) {
            writer.write(String.valueOf(highScore));
        } catch (IOException e) {
            System.out.println("Failed to save highscore.");
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
