package box;

import javafx.application.Application;
import javafx.scene.Camera;
import javafx.scene.Group;
import javafx.scene.PerspectiveCamera;
import javafx.scene.Scene;
import javafx.scene.shape.Box;
import javafx.stage.Stage;

public class drawingBox extends Application {
    private static final double WIDTH=800;
    private static final double HEIGHT=600;
    @Override
    public void start(Stage stage) throws Exception {
        Box box=new Box(100,80,60);
        Group group=new Group(box);
        group.translateXProperty().set(WIDTH/2);
        group.translateYProperty().set(HEIGHT/2);

        Camera camera=new PerspectiveCamera(true);
        Scene scene=new Scene(group,WIDTH,HEIGHT);
        stage.setScene(scene);
        stage.show();

    }

    public static void main(String[] args) {
        launch(args);
    }
}
