import javafx.scene.canvas.GraphicsContext;

public abstract class DrawableObject {
    protected double x, y;
    
    public DrawableObject(double x, double y) {
        this.x = x;
        this.y = y;
    }
    
    public abstract void drawMe(GraphicsContext gc);
    public void act() {}
    
    public double getX() { return x; }
    public double getY() { return y; }
    
    public void setX(double x) { this.x = x; }
    public void setY(double y) { this.y = y; }
    
    public static double distance(DrawableObject a, DrawableObject b) {
        return Math.sqrt(Math.pow(a.getX() - b.getX(), 2) + Math.pow(a.getY() - b.getY(), 2));
    }
}