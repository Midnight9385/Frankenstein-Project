package GameObjects;

import java.awt.Color;

public class Floor extends GameObjectBase {
    public Floor() {
        this(0, 0, 10, 10);
    }

    public Floor(int x, int y, int width, int height) {
        this(x, y, width, height, Color.GRAY, false);
    }

    public Floor(int x, int y, int width, int height, Color color, boolean rigid) {
        super(x, y, width, height, color, rigid);
    }
}
