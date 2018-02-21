package tech.holm.red01_wordCounter.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import tech.holm.red01_wordCounter.fileanalyzer.AnalyticsResult;
import tech.holm.red01_wordCounter.fileanalyzer.Analyzer;

import java.io.IOException;
import java.util.ArrayList;

@Controller
public class MainController {


    @GetMapping(value = {"", ""})
    public String showMainPage(Model model){
            return "redirect:/clientnetworkchat";
    }



}
