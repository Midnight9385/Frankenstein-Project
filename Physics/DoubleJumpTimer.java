package Physics;

import java.util.Timer;
import java.util.TimerTask;
import Game.*;

public class DoubleJumpTimer {
    Timer timer;
    FrankensteinProject object;

    public DoubleJumpTimer(double seconds, FrankensteinProject object) {
        this.object = object;
        this.object.doubleJumpTiming = true;
        timer = new Timer();
        timer.schedule(new doubleJumpTask(), (long) seconds * 1000);
    }

    class doubleJumpTask extends TimerTask {
        public void run() {
            object.doubleJumpTiming = false;
            timer.cancel();
        }
    }
}
