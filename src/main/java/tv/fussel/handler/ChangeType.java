package tv.fussel.handler;

import java.awt.*;

public enum ChangeType {
    JOIN(Color.GREEN),
    PROMOTION(Color.BLUE),
    DEMOTION(Color.RED),
    LEFT(Color.ORANGE);
    public final Color color;

    ChangeType(Color color) {
        this.color = color;
    }
}
