package tech.holm.third_semester_tasks.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import tech.holm.third_semester_tasks.fileanalyzer.Analyzer;

@Controller
public class GeometricObjectController {

    private Analyzer analyzer = new Analyzer();

    @GetMapping(value = {"/geometric"})
    public String showGeometricPage(Model model){
            return "opgave001";
    }

    @PostMapping("/submit/geometric")
    public String analyze(Model model) {

        return "opgave001";
    }



}
