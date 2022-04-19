package GameObjects;

import java.awt.Color;

public class Enemy extends GameObjectBase {

    public Enemy() {
        this(0, 0, 10, 10);
    }

    public Enemy(int x, int y, int width, int height) {
        super(x, y, width, height, Color.RED, false);
    }
}
