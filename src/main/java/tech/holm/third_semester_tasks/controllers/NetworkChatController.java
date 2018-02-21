package tech.holm.third_semester_tasks.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import tech.holm.third_semester_tasks.networkChat.NanoChatTCPClient;
import tech.holm.third_semester_tasks.networkChat.NanoChatTCPServer;

@Controller
public class NetworkChatController {

    NanoChatTCPClient client;
    NanoChatTCPServer server;

    Thread serverThread;
    Thread clientThread;

    String clientMessages = "Hello Client: new chat started";
    String serverMessages = "Hello Server: new chat started";


    @GetMapping(value = {"/clientnetworkchat"})
    public String showClientChatPage(Model model){
        model.addAttribute("isclient", true);
        if(client != null){
            clientMessages = clientMessages + client.hasNewMessage();
        }
        model.addAttribute("message", clientMessages);
        return "chatpage";
    }

    @GetMapping(value = {"/servernetworkchat"})
    public String showServerChatPage(Model model){
        model.addAttribute("isclient", false);
        if(server != null){
            serverMessages = serverMessages + server.hasNewMessage();
        }
        model.addAttribute("message", serverMessages);
        return "chatpage";
    }

    @PostMapping(value = {"/startchatserver"})
    public String startServer(){
        if(server == null){
            server = new NanoChatTCPServer();
            server.searchForClient();
            serverThread = new Thread(server);
            serverThread.start();
            serverMessages = serverMessages + "</br>"+ "server connected!";
        }

        return "redirect:/servernetworkchat";
    }

    @PostMapping(value = {"/startchatclient"})
    public String startClient(){
        if (server != null && client == null){
            client = new NanoChatTCPClient();
            clientThread = new Thread(client);
            clientThread.start();
            clientMessages = clientMessages + "</br>"+ "server connected!";
        } else if (server == null) {
            clientMessages = clientMessages + "</br>"+ "server hasn't been started yet";
        } else if (client != null) {
            clientMessages = clientMessages + "</br>"+ "a client object already exists";
        }
        return "redirect:/clientnetworkchat";
    }

    @PostMapping(value = {"/submit/sendmessage"})
    public String sendMessage(@RequestParam Boolean isclient, @RequestParam String message){
        if (isclient){
            client.sendMessage(message);
            return "redirect:/clientnetworkchat";
        } else {
            server.sendMessage(message);
            return "redirect:/servernetworkchat";
        }

    }


}
