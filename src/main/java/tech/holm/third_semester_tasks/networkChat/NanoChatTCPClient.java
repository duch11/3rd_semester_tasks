package tech.holm.red01_wordCounter.networkChat;

import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

public class NanoChatTCPClient implements Runnable {
        private String ip;
        private Socket socket;
        private InputStream inputStream;
        private OutputStream outputStream;
        private String message = "";
        BufferedReader bufferedReader;
    public NanoChatTCPClient() {
        try {
            ip = InetAddress.getLocalHost().getHostAddress();
            /*ip = "192.168.0.16";*/
            socket = new Socket(ip, 5555);
            System.out.println("Client Started with server socket: " + socket.getPort());

            outputStream = socket.getOutputStream();
            inputStream = socket.getInputStream();



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

    public String hasNewMessage() {
        String myMessage = "";
        if (!message.equals("")){
            myMessage = myMessage.concat("</br>" + "Server:" + message);
            message = "";
        }
        return myMessage;
    }

    public void sendMessage(String message){

        PrintWriter output = new PrintWriter(outputStream, true);
        System.out.println("C: Sending message: " + message);
        output.println(message);
        System.out.println("C: Message sent.");

    }

    @Override
    public void run() {
        while (true){
            try{
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                if (bufferedReader.ready()){
                    message = bufferedReader.readLine();
                    System.out.println("C: Message received: " + message);
                } else {
                    Thread.sleep(2000);
                    System.out.println("C: No message");
                }
            } catch (Exception e){

            }
        }
    }
}
