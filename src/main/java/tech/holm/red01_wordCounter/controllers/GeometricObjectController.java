package tech.holm.red01_wordCounter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.holm.red01_wordCounter.fileanalyzer.FileAnalyzer;

import java.io.IOException;

@Controller
public class AnalyzerController {

    private FileAnalyzer analyzer = new FileAnalyzer();

    @GetMapping(value = {"", ""})
    public String showMainPage(Model model){
            return "Frontpage";
    }

    @PostMapping("/submit")
    public String analyze(Model model, @RequestParam("file") MultipartFile file) throws IOException {
        if(!file.isEmpty()){
            model.addAttribute("sortedLetterList", analyzer.analyzeFileForLetters(file));
            model.addAttribute("sortedWordList", analyzer.analyzeFileForWords(file));
            model.addAttribute("fileAnalyzed", true);
        }
        model.addAttribute("fileRecieved", true);
        return "Frontpage";
    }



}
