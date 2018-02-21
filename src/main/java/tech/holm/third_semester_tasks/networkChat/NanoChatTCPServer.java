package tech.holm.red01_wordCounter.networkChat;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class NanoChatTCPServer implements Runnable{
    private ServerSocket serverSocket;
    private Socket socket;
    private OutputStream outputStream;
    private InputStream inputStream;
    private String message = "";

    public NanoChatTCPServer() {
        try {
            serverSocket = new ServerSocket(5555);
            System.out.println("Server Started on port: " + serverSocket.getLocalPort());

        } catch (IOException e) {
            System.out.println("Server Constructor error: " + e.getMessage());
        }


    }

    public void searchForClient() {
        try {
            //find client
            System.out.println("S:Searching for client...");
            socket = serverSocket.accept();
            System.out.println("S:Client found! " + socket.getRemoteSocketAddress());

        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }




    }

    private void sendAndReceiveMessages() throws IOException {
        //receive message
        inputStream = socket.getInputStream();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
        System.out.println("S: message: " + bufferedReader.readLine());


        //send message
        outputStream = socket.getOutputStream();
        PrintWriter output = new PrintWriter(outputStream, true);
        serverSocket.close();
    }

    @Override
    public String toString() {
        return "server: " + serverSocket.toString();
    }

    @Override
    public void run() {

        while (true) {
            try{
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                if (bufferedReader.ready()){
                    message = bufferedReader.readLine();
                    System.out.println("Server: Message received: " + message);
                } else {
                    Thread.sleep(2000);
                    /*System.out.println("Server: No message");*/
                }
            } catch (Exception e){

            }
        }
    }

    public void sendMessage(String message) {

        PrintWriter output = new PrintWriter(outputStream, true);
        System.out.println("S: Sending message: " + message);
        output.println(message);
        System.out.println("S: Message sent.");

    }
    public String hasNewMessage() {
        String myMessage = "";
        if (!message.equals("")){
            myMessage = myMessage.concat("</br>" + "Client: " + message);
            message = "";
        }
        return myMessage;
    }
}
