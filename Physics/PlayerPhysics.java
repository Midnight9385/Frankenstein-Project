package Physics;

import Game.FrankensteinProject;
import GameObjects.GameObjectBase.Direction;

public class PlayerPhysics {
    public void physics(FrankensteinProject game) {
        if (game.jumpTarget < game.player.getY() && game.jump) {
            game.player.changeY(-game.jumpSpeed);
            if (game.jumpTarget == game.player.getY() && !game.doubleJump)
                game.startTimer();
        } else
            game.jump = false;
        for (int p = 0; p < game.floors.get(game.level).size(); p++) {
            if (game.floors.get(game.level).get(p).check(game.player.getX(), game.player.getY(),
                    game.player.getheight(),
                    game.player.getwidth())) {
                game.player.onObject = true;
                p = game.floors.get(game.level).size() + 1;
            } else
                game.player.onObject = false;
        }
        for (int p = 0; p < game.floors.get(game.level).size(); p++) {
            if (game.floors.get(game.level).get(p).checkUp(game.player)) {
                game.jump = false;
                game.doubleJump = true;
                p = game.floors.get(game.level).size() + 1;
            }
        }
        for (int p = 0; p < game.spikes.get(game.level).size(); p++) {
            if (game.spikes.get(game.level).get(p).check(game.player.getX(), game.player.getY(),
                    game.player.getheight(),
                    game.player.getwidth())) {
                game.player.onEnemy = true;
                p = game.spikes.get(game.level).size() + 1;
            } else {
            }
            // game.player.onEnemy = false;
        }
        for (int p = 0; p < game.walls.get(game.level).size(); p++) {
            if (game.walls.get(game.level).get(p).check(game.player.getX(), game.player.getY(), game.player.getheight(),
                    game.player.getwidth())) {
                game.player.onObject = true;
                p = game.walls.get(game.level).size() + 1;
            }
        }
        if (game.player.onEnemy) {
            game.player.setX(game.startX[game.level]);
            game.player.setY(game.startY[game.level]);
            game.player.onEnemy = false;
        }
        if ((game.player.getY() + game.player.getheight()) < game.floorLevel && !game.player.onObject && !game.jump) {
            game.player.changeY(game.gravitySpeed);
        }
        if ((game.player.getY() + game.player.getheight()) == game.floorLevel || game.player.onObject) {
            game.doubleJump = false;
            game.jump = false;
            game.justJumped = false;
        }
    }
}
