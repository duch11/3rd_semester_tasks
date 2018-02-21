package tech.holm.third_semester_tasks.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tech.holm.third_semester_tasks.fileanalyzer.AnalyticsResult;
import tech.holm.third_semester_tasks.fileanalyzer.Analyzer;

import java.io.IOException;
import java.util.ArrayList;

@Controller
public class AnalyzerController {

    private Analyzer analyzer = new Analyzer();
    private ArrayList<AnalyticsResult> urlCharResult;
    private ArrayList<AnalyticsResult> fileCharResult;
    private ArrayList<AnalyticsResult> fileWordResult;

    @GetMapping(value = {"/analyzer"})
    public String showAnalyzer(Model model){
            return "Frontpage";
    }

    @PostMapping("/submit")
    public String analyze(Model model, @RequestParam("file") MultipartFile file) throws IOException {
        if(!file.isEmpty()){
            fileCharResult = analyzer.analyzeFileForLetters(file);
            fileWordResult = analyzer.analyzeFileForWords(file);
            model.addAttribute("sortedLetterList", fileCharResult);
            model.addAttribute("sortedWordList", fileWordResult);
            model.addAttribute("sortedLetterListURL", urlCharResult);

        }
        return "Frontpage";
    }

    @PostMapping(value = "/submit", params = "url")
    public String analyze(Model model, @RequestParam("url") String url) throws IOException {
        if(!url.isEmpty()){
            urlCharResult = analyzer.analyzeUrlForLetters(url);

            model.addAttribute("sortedLetterListURL", urlCharResult);

            model.addAttribute("sortedLetterList", fileCharResult);
            model.addAttribute("sortedWordList", fileWordResult);
        }
        return "Frontpage";
    }



}
