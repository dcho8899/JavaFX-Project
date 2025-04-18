import javafx.scene.canvas.GraphicsContext;
import java.util.ArrayList;
import java.util.List;

public class GameLoop {
    private Player player;
    private List<Mine> mines;
    private double canvasWidth, canvasHeight;

    public GameLoop(Player player, double canvasWidth, double canvasHeight) {
        this.player = player;
        this.canvasWidth = canvasWidth;
        this.canvasHeight = canvasHeight;
        this.mines = new ArrayList<>();
        generateMines();  // Initial mine generation
    }

    public void update(GraphicsContext gc) {
        gc.clearRect(0, 0, canvasWidth, canvasHeight);
        player.drawMe(gc);
        for (Mine mine : mines) {
            mine.drawMe(gc);
            if (player.collidesWith(mine)) {
                endGame();
                return;
            }
        }
    }

    public void act() {
        player.act();
        // Additional game logic like mine movement, scoring, etc.
    }

    private void generateMines() {
        // Generate mines in a random location
        for (int i = 0; i < 10; i++) {
            mines.add(new Mine(Math.random() * canvasWidth, Math.random() * canvasHeight));
        }
    }

    private void endGame() {
        System.out.println("Game Over!");
        // Implement additional game over logic (stop game, show score, etc.)
    }
}
