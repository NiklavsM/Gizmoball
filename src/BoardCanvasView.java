import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class BoardCanvasView extends Canvas {

    public BoardCanvasView(double width, double height) {
        super(width, height);

        setup();
    }

    private void setup() {
        GraphicsContext gc = this.getGraphicsContext2D();

        gc.setFill(Color.WHITE);
        gc.strokeRect(0, 0, super.getWidth(), super.getHeight());

        gc.setFill(Color.WHITE);
        for (int i = 0; i < 20; i++) {
            for (int j = 0; j < 20; j++) {
                gc.strokeRect(i*25, j*25, 25, 25);
            }
        }

    }
}
