package tech.holm.red01_wordCounter.javafx_first;

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

    public NetworkHelper() {
        try {
            ip = InetAddress.getByName("192.168.0.8");
            socket = new Socket(ip, 6780);
            System.out.println("Client Started with server socket: " + socket.getPort());

            outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();

        } catch (Exception e) {
            System.out.println("No connection");
        }
    }

    public void sendMessage(String name, String red, String green, String blue){
        PrintWriter output = new PrintWriter(outputStream, true);
        System.out.println("C: Sending message: " + name + " " + red  + " " + green  + " " + blue);
        output.println(name);
        output.println(red+","+green+","+blue);
        System.out.println("C: Message sent.");
    }

    public void sendArrow(String direction){
        PrintWriter output = new PrintWriter(outputStream, true);
        output.println(direction);
    }
}
