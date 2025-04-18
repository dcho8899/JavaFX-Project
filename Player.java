import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Player {
    private double x, y;
    private double speed = 2;
    private int score = 0;
    private boolean movingUp, movingDown, movingLeft, movingRight;
    private double startX, startY;  // To track the starting position

    public Player(double x, double y) {
        this.x = x;
        this.y = y;
        this.startX = x;  // Set the starting position
        this.startY = y;
    }

    public void act() {
        if (movingUp) y -= speed;
        if (movingDown) y += speed;
        if (movingLeft) x -= speed;
        if (movingRight) x += speed;
    }

    public void handleKeyPress(String key) {
        switch (key) {
            case "W": movingUp = true; break;
            case "S": movingDown = true; break;
            case "A": movingLeft = true; break;
            case "D": movingRight = true; break;
        }
    }

    public void handleKeyRelease(String key) {
        switch (key) {
            case "W": movingUp = false; break;
            case "S": movingDown = false; break;
            case "A": movingLeft = false; break;
            case "D": movingRight = false; break;
        }
    }

    // Method to calculate the distance traveled between frames
    public double getDistanceTraveled(double lastX, double lastY) {
        return Math.sqrt(Math.pow(x - lastX, 2) + Math.pow(y - lastY, 2));
    }

    // Method to accumulate score based on distance
    public void addScore(double distance) {
        score += distance;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public int getScore() {
        return score;
    }

    // Drawing the player as a circle
    public void draw(GraphicsContext gc, double centerX, double centerY) {
        // Draw the player relative to the center of the screen
        double drawX = centerX - 15;
        double drawY = centerY - 15;
        gc.setFill(Color.BLUE);
        gc.fillOval(drawX, drawY, 30, 30);  // Drawing player as a circle
    }

    public boolean collidesWith(Mine mine) {
        double distance = Math.sqrt(Math.pow(x - mine.getX(), 2) + Math.pow(y - mine.getY(), 2));
        return distance < 30;  // Assuming player and mine both have radius of 15
    }

    // Update the score based on distance from the starting position
    public void updateScore() {
        double distanceTraveled = Math.sqrt(Math.pow(x - startX, 2) + Math.pow(y - startY, 2));
        score = (int) distanceTraveled;  // Update score with distance traveled
    }

    // If player returns to the starting position, subtract the distance
    public void checkForReturnToStart() {
        if (Math.sqrt(Math.pow(x - startX, 2) + Math.pow(y - startY, 2)) < 5) {
            score -= 10;  // Subtract points if close to the starting position
        }
    }
}
