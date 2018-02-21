package tech.holm.third_semester_tasks.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {


    @GetMapping(value = {"", ""})
    public String showMainPage(Model model){
            return "redirect:/clientnetworkchat";
    }



}
