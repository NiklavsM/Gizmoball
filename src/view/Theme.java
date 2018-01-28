package view;

import javafx.geometry.Insets;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;

public class Theme {

    public static class Fonts {
        public static final Font HEADING_FONT = new Font("Arial", 25);
        public static final Font CARD_TITLE = new Font("Arial", 15);
        public static final Font TITLE_FONT = new Font("Arial", 25);
        public static final Font SUBTITLE_FONT = new Font("Arial", 16);
        public static final Font REGULAR_FONT = new Font("Arial", 14);
    }


    public static class Colors {
        public static final Color BLUE = Color.rgb(3,169,244);
        public static final Color RED = Color.rgb(244,67,54);
        public static final Color ORANGE = Color.rgb(255,152,0);
        public static final Color PURPLE = Color.rgb(103,58,183);
        public static final Color WHITE = Color.rgb(255,255,255);
        public static final Color PINK = Color.rgb(233,30,99);
        public static final Color GREEN = Color.rgb(139,195,74);
        public static final Color GRAY = Color.rgb(139,195,74);
    }

    public static final Insets DEFAULT_PADDING = new Insets(16, 16 ,16, 16);


//    public static final Label titleLabel = new Label();
}
