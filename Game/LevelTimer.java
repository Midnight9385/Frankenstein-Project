package Game;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JLabel;

public class LevelTimer {
    double seconds = 0;
    int minuets = 0;
    boolean running = false;
    FrankensteinProject object;
    Timer timer;

    public LevelTimer(FrankensteinProject object) {
        this.object = object;
        timer = new Timer();
        timer.scheduleAtFixedRate(new doubleJumpTask(object), 1, 1);
    }

    class doubleJumpTask extends TimerTask {
        FrankensteinProject object;

        public doubleJumpTask(FrankensteinProject a) {
            this.object = a;
        }

        public void run() {
            seconds += .001;
            if (seconds >= 60) {
                minuets++;
                seconds = 0;
            }
            if (seconds < 10)
                this.object.doubleJ = new JLabel(Integer.toString(minuets) + ":0" + String.format("%.3f", seconds));
            else
                this.object.doubleJ = new JLabel(Integer.toString(minuets) + ":" + String.format("%.3f", seconds));
        }
    };
}