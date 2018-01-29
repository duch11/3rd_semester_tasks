package tech.holm.red01_wordCounter;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.DataInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

@Controller
public class MainController {

    @GetMapping(value = {"", ""})
    public String showMainPage(Model model){
            return "Frontpage";
    }

    @PostMapping("/submit")
    public String analyze(Model model, @RequestParam("file") MultipartFile file) throws IOException {
        if(!file.isEmpty()){
            analyzeFile(file);
            model.addAttribute("fileRecieved", true);
        }
        return "Frontpage";
    }

    private void analyzeFile(MultipartFile file) throws IOException {
        System.out.println("Analyzing: " + file.getOriginalFilename());
        System.out.println(file.getSize() + "Bytes " + file.getContentType());

        if(file.getContentType().equals("text/plain")){
            Scanner scanner = new Scanner(file.getInputStream());
            scanner.useDelimiter("");
            while (scanner.hasNext()){
                String character = scanner.next();
                if(character.matches("\\p{IsLatin}+")){
                    System.out.println(character);
                }
            }
        }


    }

}
