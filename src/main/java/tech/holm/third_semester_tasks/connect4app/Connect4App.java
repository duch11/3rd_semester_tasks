package tech.holm.third_semester_tasks.connect4app;

import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @author Almas Baimagambetov (AlmasB) (almaslvl@gmail.com)
 * @author Jon Eikholm (jone@kea.dk) Network preparation
 */
public class Connect4App extends Application {

    private static final int TILE_SIZE = 80;
    private static final int COLUMNS = 7;
    private static final int ROWS = 6;

    private boolean redMove = true;
    private Disc[][] grid = new Disc[COLUMNS][ROWS];

    private Pane discRoot = new Pane();

    // network part
    private Button startServerButton = new Button("Start Server");
    private Button connectToServerButton = new Button("Connect to Server");
    private TextField statusLabel = new TextField();
    private TextField gameOverLabel = new TextField();
    private String messageStyle =  "; -fx-text-fill: white";
    public final int serverPortNumber = 1337;
    private TextField ipEntry = new TextField("192.168.1.168");
    private TextField portEntry = new TextField(serverPortNumber+"");
    private Label ipLabel = new Label("IP");
    private boolean gameOver = false;
    private Pane root;

    Socket socket;
    OutputStream outputStream;
    InputStream inputStream;

    @Override
    public void stop(){
        System.out.println("app was closed...");
        // fill
    }

    private void clientConnectToServer(){
        System.out.println("connecting to server...");
        try {

            /*ip = "192.168.0.16";*/
            String port = portEntry.getText();

            socket = new Socket(ipEntry.getText(), Integer.valueOf(portEntry.getText()));
            System.out.println("Client Started with server socket: " + socket.getPort());
            outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();
            PrintWriter printWriter = new PrintWriter(outputStream);



        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startServer(){
        System.out.println("starting server...");
        ServerSocket serverSocket;
        try {
            serverSocket = new ServerSocket(serverPortNumber);
            System.out.println("Server Started on port: " + serverSocket.getLocalPort());

        } catch (IOException e) {
            System.out.println("Server Constructor error: " + e.getMessage());
        }
    }


    public void receiveNetworkMove(int column){
        Platform.runLater(() -> placeDisc(new Disc(redMove), column));
        // possibly add further code

    }

    public void sendNetworkMove(int column){
        System.out.println("Sending move to network:  " + column);
        // fill
    }

    // Exercise: Call this method to display server status
    public void setServerStatus(boolean isRunning, String hostAddress){
        Platform.runLater(() -> {
            if(isRunning){
                statusLabel.setText("Server is Running ");
                ipLabel.setText(hostAddress);
                statusLabel.setStyle("-fx-background-color: cornflowerblue" + messageStyle);
            }else{
                statusLabel.setText("Server is OFF");
                statusLabel.setStyle("-fx-background-color: darkgray" + messageStyle);
            }
        });
    }

    // Exercise: Call this method to display connection status
    public void setConnectionStatus(boolean isConnected){
        Platform.runLater(() -> {
            if (isConnected)
            {
                statusLabel.setText("Connected.");
                statusLabel.setStyle("-fx-background-color: green" + messageStyle);

            } else
            {
                statusLabel.setText("Not connected.");
                statusLabel.setStyle("-fx-background-color: red" + messageStyle);
            }
        });
    }

    // END OF EXERCISE AREA // END OF EXERCISE AREA // END OF EXERCISE AREA


    private Parent createContent() {
        root = new Pane();
        root.getChildren().add(discRoot);
        HBox networkPanel = makeNetworkPanel();
        Shape gridShape = makeGrid();
        root.getChildren().add(gridShape);
        root.getChildren().addAll(makeColumns());
        root.getChildren().add(networkPanel);
        root.getChildren().add(statusLabel);
        return root;
    }



    private HBox makeNetworkPanel()
    {
        HBox networkPanel = new HBox();
        ipEntry.setPromptText("add server ip");
        portEntry.setPromptText("add server port");

        ToggleGroup toggleGroup = new ToggleGroup();
        RadioButton serverOn = makeServerRadioButton(networkPanel, toggleGroup);
        RadioButton clientOn = makeClientRadioButton(networkPanel, toggleGroup);

        networkPanel.getChildren().addAll(serverOn, clientOn);
        configureStatusLabel();
        configureGameOverLabel();
        startServerButton.setOnAction(event -> startServer());
        connectToServerButton.setOnAction(event -> clientConnectToServer());

        return networkPanel;
    }



    private RadioButton makeServerRadioButton(HBox networkPanel, ToggleGroup toggleGroup)
    {
        RadioButton serverOn = new RadioButton("Server");
        serverOn.setStyle("-fx-text-fill:white");
        serverOn.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue)
                toggleClientServer(networkPanel, newValue);
        });
        serverOn.setToggleGroup(toggleGroup);
        return serverOn;
    }

    private RadioButton makeClientRadioButton(HBox networkPanel, ToggleGroup toggleGroup)
    {
        RadioButton clientOn = new RadioButton("Client");
        clientOn.setStyle("-fx-text-fill:white");
        clientOn.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if(newValue)
                toggleClientServer(networkPanel, !newValue);
        });
        clientOn.setToggleGroup(toggleGroup);
        return clientOn;
    }


    private void configureStatusLabel()
    {
        statusLabel.setTranslateY(ROWS * 5);
        statusLabel.setMinWidth(TILE_SIZE * (COLUMNS + 1));
        statusLabel.setMinHeight(30);
        String style = "-fx-background-color:green; -fx-text-fill: white; -fx-font-weight: 200";
        statusLabel.setStyle(style);
        statusLabel.setAlignment(Pos.CENTER);

        ipLabel.setStyle(messageStyle);
    }

    private void configureGameOverLabel()
    {
        gameOverLabel.setTranslateY(ROWS * 11);
        gameOverLabel.setMinWidth(TILE_SIZE * (COLUMNS + 1));
        gameOverLabel.setMinHeight(30);
        String style = "-fx-background-color:sandybrown; -fx-text-fill: red; -fx-font-weight: 200";
        gameOverLabel.setStyle(style);
        gameOverLabel.setAlignment(Pos.CENTER);
        ipLabel.setStyle(messageStyle);
    }

    private void toggleClientServer(HBox networkPanel, Boolean isServer)
    {
        if(isServer){
            ipEntry.setVisible(false);

            networkPanel.getChildren().add(startServerButton);
            networkPanel.getChildren().add(ipLabel);
            networkPanel.getChildren().remove(ipEntry);
            networkPanel.getChildren().remove(portEntry);
            networkPanel.getChildren().remove(connectToServerButton);
        }else{
            ipEntry.setVisible(true);
            networkPanel.getChildren().remove(startServerButton);
            networkPanel.getChildren().remove(ipLabel);
            networkPanel.getChildren().add(ipEntry);
            networkPanel.getChildren().add(portEntry);
            networkPanel.getChildren().add(connectToServerButton);

        }
    }

    private void showGameOverLabel(String text)
    {
        gameOverLabel.setText(text);
        root.getChildren().add(gameOverLabel);
    }


    // end of network code.

    private Shape makeGrid() {
        Shape shape = new Rectangle((COLUMNS + 1) * TILE_SIZE, (ROWS + 2) * TILE_SIZE);

        for (int y = 0; y < ROWS; y++) {
            for (int x = 0; x < COLUMNS; x++) {
                Circle circle = new Circle(TILE_SIZE / 2);
                circle.setCenterX(TILE_SIZE / 2);
                circle.setCenterY(TILE_SIZE / 2);
                circle.setTranslateX(x * (TILE_SIZE + 5) + TILE_SIZE / 4);
                circle.setTranslateY((y + 1) * (TILE_SIZE + 5) + TILE_SIZE / 4);

                shape = Shape.subtract(shape, circle);
            }
        }

        Light.Distant light = new Light.Distant();
        light.setAzimuth(45.0);
        light.setElevation(30.0);

        Lighting lighting = new Lighting();
        lighting.setLight(light);
        lighting.setSurfaceScale(5.0);

        shape.setFill(Color.BLUE);
        shape.setEffect(lighting);

        return shape;
    }

    private List<Rectangle> makeColumns() {
        List<Rectangle> list = new ArrayList<>();

        for (int x = 0; x < COLUMNS; x++) {
            Rectangle rect = new Rectangle(TILE_SIZE, (ROWS + 1) * TILE_SIZE);
            rect.setTranslateX(x * (TILE_SIZE + 5) + TILE_SIZE / 4);
            rect.setTranslateY(50);
            rect.setFill(Color.TRANSPARENT);

            rect.setOnMouseEntered(e -> rect.setFill(Color.rgb(200, 200, 50, 0.3)));
            rect.setOnMouseExited(e -> rect.setFill(Color.TRANSPARENT));

            final int column = x;

            rect.setOnMouseClicked(e -> {
                if(!gameOver)
                {
                    placeDisc(new Disc(redMove), column);
                    sendNetworkMove(column);
                }else {
                    System.out.println("Game over. No move.");
                }
            });

            list.add(rect);
        }

        return list;
    }

    private void placeDisc(Disc disc, int column) {
        int row = ROWS - 1;
        do {
            if (!getDisc(column, row).isPresent())
                break;

            row--;
        } while (row >= 0);

        if (row < 0)
            return;

        grid[column][row] = disc;
        discRoot.getChildren().add(disc);
        disc.setTranslateX(column * (TILE_SIZE + 5) + TILE_SIZE / 4);

        final int currentRow = row;

        TranslateTransition animation = new TranslateTransition(Duration.seconds(0.5), disc);
        animation.setToY((row + 1) * (TILE_SIZE + 5) + TILE_SIZE / 4);
        animation.setOnFinished(e -> {
            if (gameEnded(column, currentRow)) {
                gameOver();
            }

            redMove = !redMove;
        });
        animation.play();
    }

    private boolean gameEnded(int column, int row) {
        List<Point2D> vertical = IntStream.rangeClosed(row - 3, row + 3)
                .mapToObj(r -> new Point2D(column, r))
                .collect(Collectors.toList());

        List<Point2D> horizontal = IntStream.rangeClosed(column - 3, column + 3)
                .mapToObj(c -> new Point2D(c, row))
                .collect(Collectors.toList());

        Point2D topLeft = new Point2D(column - 3, row - 3);
        List<Point2D> diagonal1 = IntStream.rangeClosed(0, 6)
                .mapToObj(i -> topLeft.add(i, i))
                .collect(Collectors.toList());

        Point2D botLeft = new Point2D(column - 3, row + 3);
        List<Point2D> diagonal2 = IntStream.rangeClosed(0, 6)
                .mapToObj(i -> botLeft.add(i, -i))
                .collect(Collectors.toList());

        return checkRange(vertical) || checkRange(horizontal)
                || checkRange(diagonal1) || checkRange(diagonal2);
    }

    private boolean checkRange(List<Point2D> points) {
        int chain = 0;

        for (Point2D p : points) {
            int column = (int) p.getX();
            int row = (int) p.getY();

            Disc disc = getDisc(column, row).orElse(new Disc(!redMove));
            if (disc.red == redMove) {
                chain++;
                if (chain == 4) {
                    return true;
                }
            } else {
                chain = 0;
            }
        }

        return false;
    }

    private void gameOver() {
        gameOver = true;
        String winnerText = "Winner: " + (redMove ? "RED" : "YELLOW");
        System.out.println(winnerText);
        showGameOverLabel(winnerText);
    }

    private Optional<Disc> getDisc(int column, int row) {
        if (column < 0 || column >= COLUMNS
                || row < 0 || row >= ROWS)
            return Optional.empty();

        return Optional.ofNullable(grid[column][row]);
    }

    private static class Disc extends Circle {
        private final boolean red;
        public Disc(boolean red) {
            super(TILE_SIZE / 2, red ? Color.RED : Color.YELLOW);
            this.red = red;

            setCenterX(TILE_SIZE / 2);
            setCenterY(TILE_SIZE / 2);
        }
    }

    @Override
    public void start(Stage stage) throws Exception {
        stage.setScene(new Scene(createContent()));
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}