package tech.holm.third_semester_tasks.javafx_first;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class NetworkHelper {

    private InetAddress ip;
    private Socket socket;
    private OutputStream outputStream;
    private InputStream inputStream;
    private PrintWriter printWriter;

    public NetworkHelper() {
        try {
            ip = InetAddress.getByName("192.168.0.8");
            socket = new Socket(ip, 6780);
            System.out.println("Client Started with server socket: " + socket.getPort());

            outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();
            printWriter = new PrintWriter(outputStream);
            System.out.println("Connection: " + outputStream.toString());

        } catch (Exception e) {
            System.out.println("No connection");
        }
    }

    public void sendMessage(String name, String red, String green, String blue){
        System.out.println("C: Sending message: " + name + " " + red  + " " + green  + " " + blue);
        printWriter.println(name);
        printWriter.println(red+","+green+","+blue);
        System.out.println("C: Message sent.");
    }

    public void sendArrow(String direction){
        PrintWriter printWriter1 = new PrintWriter(outputStream);
        printWriter1.println(direction);
        System.out.println(direction);
    }

    /*public void sendReleaseEvent(Double x, Double y){
        printWriter.println("R,"+x.intValue()+","+y.intValue());
        System.out.println("R,"+x.intValue()+","+y.intValue());
    }

    public void sendDraggedEvent(Double x, Double y){
        printWriter.println("D,"+x.intValue()+","+y.intValue());
        System.out.println("D,"+x.intValue()+","+y.intValue());
    }

    public void sendPressEvent(Double x, Double y){
        printWriter.println("P,"+x.intValue()+","+y.intValue());
        System.out.println(("P," + x.intValue() + "," + y.intValue()));
    }*/
}
