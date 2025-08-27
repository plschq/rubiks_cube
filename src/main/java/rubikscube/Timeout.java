package rubikscube;


import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Callable;


public class Timeout {
    public static <T> void set(Callable<T> function, int milliseconds) {
        new Timer().schedule(new TimerTask() {@Override public void run() {
            try {
                function.call();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }}, milliseconds);
    }
}
