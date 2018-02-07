package tech.holm.red01_wordCounter.networkHelpMe;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class NanoTCPServer {
    private ServerSocket serverSocket;
    private Socket socket;
    private OutputStream outputStream;
    private InputStream inputStream;
    private String message;
    public NanoTCPServer() {
        try {
            serverSocket = new ServerSocket(5555);

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public String toString() {
        return "server: " + serverSocket.toString();
    }

    public String searchForClient() throws IOException {
        try {
            //find client
            System.out.println("S:Searching for client...");
            socket = serverSocket.accept();
            System.out.println("S:Client found! " + socket.getRemoteSocketAddress());

            //receive message
            inputStream = socket.getInputStream();
            Scanner scan = new Scanner(inputStream);
            System.out.println("S:Waiting for message:");
            message = scan.nextLine();
            System.out.println("S:Message received: " + message);

            //send message
            outputStream = socket.getOutputStream();
            PrintWriter output = new PrintWriter(outputStream, true);
            System.out.println("S:Sending message..");
            output.println("11.14");

            System.out.println("S:message sent..");
            serverSocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Finished, message received: " + message ;
    }
}
