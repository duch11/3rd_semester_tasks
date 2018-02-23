package tech.holm.third_semester_tasks.javafx_first;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.InputEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import javax.xml.crypto.Data;


public class GUIClient extends Application {
    public NetworkHelper networkHelper;
    private double width = 500;
    private double height = 500;
    private GraphicsContext graphicsContext;

    public static void main(String[] args) {
        Application.launch(args); //Manuel opstart?
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        //netværk code
        //networkHelper = new NetworkHelper();
        VBox vbox = new VBox();

        makeGUI(vbox);

        Scene scene = new Scene(vbox, 500,height+200);
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

        configureButton(namePrompt, redPrompt, bluePrompt, greenPrompt, startBtn);


        vbox.setSpacing(10);
        vbox.getChildren().addAll(label, namePrompt, redPrompt,bluePrompt,greenPrompt, startBtn);

        //make canvas
        Canvas canvas = new Canvas(width, height);

        //vi laver et anonymt objekt af et interface.
        EventHandler<MouseEvent> mouseClickedHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("X: " + event.getX());
                System.out.println("Y: " + event.getY());
                graphicsContext.beginPath();
                graphicsContext.moveTo(event.getX(), event.getY());
            }
        };



        canvas.addEventHandler(MouseEvent.MOUSE_PRESSED, mouseClickedHandler);

        EventHandler<MouseEvent> mouseDraggedHandler = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                System.out.println("X: " + event.getX());
                System.out.println("Y: " + event.getY());
                graphicsContext.lineTo(event.getX(),event.getY());
                graphicsContext.stroke();
            }
        };

        canvas.addEventHandler(MouseEvent.MOUSE_MOVED, mouseDraggedHandler);

        graphicsContext = canvas.getGraphicsContext2D();
        graphicsContext.setStroke(Color.rgb(255,244,1));
        graphicsContext.setLineWidth(20);

        vbox.getChildren().add(canvas);


    }

    private void configureButton(TextField namePrompt, TextField redPrompt, TextField bluePrompt, TextField greenPrompt, Button startBtn) {
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
    }
}
