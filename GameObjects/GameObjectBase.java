package GameObjects;

import java.awt.Color;

public class GameObjectBase {
    protected int x;
    protected int y;
    protected int width;
    protected int height;
    protected Color color;
    protected boolean rigid;

    protected GameObjectBase() {
        this(0, 0, 10, 10, Color.BLACK, false);
    }

    /**
     * creates the object with set X and Y cords
     * 
     * @param x     - the X value to start at
     * @param y     - the Y value to start at
     * @param rigid - if the object is affected by gravity
     */
    protected GameObjectBase(int x, int y, int width, int height, Color color, boolean rigid) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
        this.rigid = rigid;
    }

    /**
     * changes the X value by the passed value
     * 
     * @param x - the value to change X by
     */
    public void changeX(int x) {
        this.x += x;
    }

    /**
     * changes the Y value by the passed value
     * 
     * @param y - the value to change Y by
     */
    public void changeY(int y) {
        this.y += y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the X value of the object
     */
    public int getX() {
        return this.x;
    }

    /**
     * @return the Y value of the object
     */
    public int getY() {
        return this.y;
    }

    public boolean getRigid() {
        return rigid;
    }

    /**
     * @return the height of the object
     */
    public int getheight() {
        return this.height;
    }

    /**
     * @return the width of the object
     */
    public int getwidth() {
        return this.width;
    }

    public Color getColor() {
        return color;
    }

    public boolean check(int x, int y, int hieght, int width) {
        if ((y + hieght) == getY()) {
            if (this.x < (x + width) && x < (this.x + this.width))
                return true;
            else
                return false;
        } else
            return false;

    }

    /**
     * check for collision between the cords of a player object and the wall object
     * 
     * @param direction - the Direction realtive to the player object
     * @param x         - the x value of the player object
     * @param y         - the y value of the player object
     */
    public boolean checkX(Player player, Direction direction) {
        if (player.getY() <= getY() + getheight() && player.getY() + player.getheight() >= getY()) {
            if (direction == Direction.RIGHT) {
                if (player.x + player.width == getX())
                    return true;
            }
            if (direction == Direction.LEFT) {
                if (player.x == getX() + getwidth())
                    return true;
            }
        }
        return false;
    }

    /**
     * check for collision between the cords of a player object and the wall object
     * 
     * @param direction - the Direction realtive to the player object
     * @param x         - the x value of the player object
     * @param y         - the y value of the player object
     */
    public boolean checkUp(Player player) {
        if (player.getX() <= getX() + getwidth() && player.getX() + player.getwidth() >= getX()) {
            if(player.getY() == getY() + getheight())
                return true;
        }
        return false;
    }

    public enum Direction {
        RIGHT,
        LEFT,
    }
}