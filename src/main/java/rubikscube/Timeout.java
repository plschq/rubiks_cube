package rubikscube;


import javafx.animation.TranslateTransition;
import javafx.scene.shape.Box;
import javafx.util.Duration;
import java.util.concurrent.Callable;


public final class Timeout {
    public static <T> void set(Callable<T> action, int milliseconds){
        TranslateTransition transition = new TranslateTransition();

        transition.setDuration(Duration.millis(milliseconds));
        transition.setNode(new Box());
        transition.setOnFinished(event -> {try {action.call();} catch (Exception e) {throw new RuntimeException(e);}});

        transition.play();
    }
}
