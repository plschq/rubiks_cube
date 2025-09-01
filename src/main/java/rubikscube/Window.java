package rubikscube;


import javafx.application.Application;
import javafx.stage.Stage;


public final class Window extends Application {

    public static Stage stage;


    public static void init(Stage stage) {
        Window.stage = stage;
        Window.stage.setTitle(Config.WINDOW_TITLE);
        Window.stage.setScene(App.scene);
        Window.stage.setMinWidth(Config.MIN_WINDOW_WIDTH);
        Window.stage.setMinHeight(Config.MIN_WINDOW_HEIGHT);

        if (Config.DEV_MODE) {
            Window.stage.setAlwaysOnTop(true);
        }
    }

    @Override public void start(Stage stage) {
        Launcher.start(stage);
    }

    @Override public void stop() {
        Launcher.stop();
    }

    public static void main(String[] args) {
        Window.launch(args);
    }

}
