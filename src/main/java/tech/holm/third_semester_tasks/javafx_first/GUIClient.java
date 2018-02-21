package tech.holm.third_semester_tasks.javafx_first;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUIClient extends Application {
    public NetworkHelper networkHelper;

    public static void main(String[] args) {
        Application.launch(args); //Manuel opstart?
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        networkHelper = new NetworkHelper();
        VBox vbox = new VBox();

        makeGUI(vbox);

        Scene scene = new Scene(vbox, 500,500);
        scene.getStylesheets().add("static/javafx.css");

        primaryStage.setScene(scene);

        primaryStage.show();
    }

    private void makeGUI(VBox vbox) {
        TextField namePrompt = new TextField();
        namePrompt.setPromptText("insert name");

        TextField redPrompt = new TextField();
        redPrompt.setPromptText("insert red (0-255)");

        TextField bluePrompt = new TextField();
        bluePrompt.setPromptText("insert blue (0-255)");

        TextField greenPrompt = new TextField();
        greenPrompt.setPromptText("insert green (0-255)");

        Label label = new Label("Gør brug af dette program tak");
        Button startBtn = new Button("Forbindelse");

        startBtn.setOnAction(
                event -> {
                    networkHelper.sendMessage(namePrompt.getText(), redPrompt.getText(), greenPrompt.getText(), bluePrompt.getText());
                    greenPrompt.setDisable(true);
                    redPrompt.setDisable(true);
                    bluePrompt.setDisable(true);
                    namePrompt.setDisable(true);

                }
        );

        startBtn.setOnKeyPressed(event -> {
            switch (event.getCode()) {
                case UP:
                    networkHelper.sendArrow("UP");
                    break;
                case RIGHT:
                    networkHelper.sendArrow("RIGHT");
                    break;
                case LEFT:
                    networkHelper.sendArrow("LEFT");
                    break;
                case DOWN:
                    networkHelper.sendArrow("DOWN");
            }
        });


        vbox.setSpacing(10);
        vbox.getChildren().addAll(label, namePrompt, redPrompt,bluePrompt,greenPrompt, startBtn);

    }
}
