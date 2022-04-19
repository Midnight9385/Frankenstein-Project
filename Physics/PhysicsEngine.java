package Physics;

import Game.FrankensteinProject;

public class PhysicsEngine {
    private PlayerPhysics p = new PlayerPhysics();
    private ObjectPhysics o = new ObjectPhysics();

    public void physics(FrankensteinProject game) {
        p.physics(game);
        o.physics(game);
    }
}
