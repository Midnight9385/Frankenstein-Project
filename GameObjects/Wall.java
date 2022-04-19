package GameObjects;

import java.awt.Color;

public class Wall extends GameObjectBase {

    public Wall() {
        this(0, 0, 10, 10);
    }

    public Wall(int x, int y, int width, int height) {
        this(x, y, width, height, Color.GRAY, false);
    }

    public Wall(int x, int y, int width, int height, Color color, boolean rigid) {
        super(x, y, width, height, color, rigid);
    }

}
