package tech.holm.red01_wordCounter.javafx_first;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUIClient extends Application {

    public static void main(String[] args) {
        Application.launch(args); //Manuel opstart?
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox vbox = new VBox();

        Label label = new Label("hej med dig");
        vbox.getChildren().add(label);

        Scene scene = new Scene(vbox);

        primaryStage.setScene(scene);

        primaryStage.show();
    }
}
