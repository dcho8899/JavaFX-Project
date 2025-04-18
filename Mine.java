import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Mine {
    private double x, y;
    private double radius = 15;

    public Mine(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void draw(GraphicsContext gc, double playerX, double playerY) {
        double screenX = Main.CANVAS_WIDTH / 2.0 + (x - playerX);
        double screenY = Main.CANVAS_HEIGHT / 2.0 + (y - playerY);

        gc.setFill(Color.RED);
        gc.fillOval(screenX - radius, screenY - radius, radius * 2, radius * 2);
    }

    public boolean collidesWith(Player player) {
        double dx = player.getX() - x;
        double dy = player.getY() - y;
        double distance = Math.sqrt(dx * dx + dy * dy);
        return distance < 10 + radius;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
}
