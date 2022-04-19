package GameObjects;

import java.awt.Color;

public class Player extends GameObjectBase {
    public boolean onObject = false;
    public boolean onEnemy = false;

    public Player() {
        this(0, 0, 15, 25);
    }

    public Player(int x, int y, int height, int width) {
        super(x, y, width, height, Color.BLACK, true);
    }
}