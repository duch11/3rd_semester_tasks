package tech.holm.third_semester_tasks.networkHelpMe;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class NanoTCPClient {
        private String ip;
        private Socket socket;
        private InputStream inputStream;
        private OutputStream outputStream;
        private String message;
    public NanoTCPClient() {
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
            /*ip = "192.168.0.16";*/
            socket = new Socket(ip, 5555);

            outputStream = socket.getOutputStream();
            PrintWriter output = new PrintWriter(outputStream, true);

            System.out.println("C:Sending message:");
            output.println("hej fra client");

            System.out.println("C: setting up message receiver");
            inputStream = socket.getInputStream();
            Scanner scan = new Scanner(inputStream);

            System.out.println("C: waiting for message");
            message = scan.nextLine();
            System.out.println(message);

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    @Override
    public String toString() {
        return "client: " + socket.toString() + " " + ip;
    }

    public String getIp() {
        return ip;
    }

    public String getMessage() {
        return message;
    }
}
