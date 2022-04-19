package Physics;

import Game.FrankensteinProject;
import GameObjects.*;

public class ObjectPhysics {
    public void physics(FrankensteinProject game) {
        Wall wall;
        Floor floor;
        for (int p = 0; p < game.walls.get(game.level).size(); p++) {
            wall = game.walls.get(game.level).get(p);
            if (wall.getRigid()) {
                if ((wall.getY() + wall.getheight()) < game.floorLevel) {
                    wall.changeY(game.gravitySpeed);
                }
            }
        }
        for (int p = 0; p < game.floors.get(game.level).size(); p++) {
            floor = game.floors.get(game.level).get(p);
            if (floor.getRigid()) {
                if ((floor.getY() + floor.getheight()) < game.floorLevel) {
                    floor.changeY(game.gravitySpeed);
                }
            }
        }
    }
}
