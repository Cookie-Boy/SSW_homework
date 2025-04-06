package ru.subsutis.lab3.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
@RequestMapping("/demo")
public class DemoController {
    @GetMapping("/author")
    public String getAuthorPage(Model model) {
        model.addAllAttributes(Map.of(
                "header", "Информация об авторе: ",
                "name", "Виталий Медведенко"));
        return "author";
    }
}
