package tech.holm.third_semester_tasks.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import tech.holm.third_semester_tasks.fileanalyzer.Analyzer;
import tech.holm.third_semester_tasks.networkHelpMe.NanoTCPClient;
import tech.holm.third_semester_tasks.networkHelpMe.NanoTCPServer;

import java.io.IOException;

@Controller
public class NetworkHelpMeController {

    private Analyzer analyzer = new Analyzer();
    private NanoTCPServer server;
    private NanoTCPClient client;

    @GetMapping(value = {"/network"})
    public String showGeometricPage(Model model){
            return "networkhelp";
    }

    @PostMapping("/submit/startserver")
    public String startServer(Model model) {
        server = new NanoTCPServer();
        System.out.println("server started;");
        try {
            model.addAttribute("message", server.searchForClient());
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "networkhelp";
    }

    @PostMapping("/submit/startclient")
    public String startClient(Model model) {
        System.out.println("Creating client:");
        client = new NanoTCPClient();
        System.out.println("out of client");
        model.addAttribute("ip", client.getIp());
        model.addAttribute("message", client.getMessage());
        System.out.println("client started with ip: " + client.getIp() + "and message: " + client.getMessage());
        return "networkhelp";
    }

}
