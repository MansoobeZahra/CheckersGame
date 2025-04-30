
import java.awt.Color;

public class CheckerPiece {
    private Color color;
    private boolean isKing;
    private String imagePath;

    public CheckerPiece(Color color, String imagePath) {
        this.color = color;
        this.isKing = false;
        this.imagePath = imagePath;
    }

    public Color getColor() {
        return color;
    }

    public boolean isKing() {
        return isKing;
    }

    public void makeKing() {
        isKing = true;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }
}