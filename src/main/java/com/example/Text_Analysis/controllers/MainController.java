package com.example.Text_Analysis.controllers;

import com.example.Text_Analysis.MatchingStringService;
import com.example.Text_Analysis.model.AnalysisCase;
import com.example.Text_Analysis.model.MatchingString;
import com.example.Text_Analysis.model.UploadForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class MainController {

    @Autowired
    private MatchingStringService service;

    @GetMapping("/")
    public String showIndex(Model model) {

        model.addAttribute("userCase", new AnalysisCase());
        model.addAttribute("uploadForm", new UploadForm());

        return "index";
    }

    @PostMapping("/saveInputManual")
    public String saveInput(@ModelAttribute("userCase") AnalysisCase userCase,
                            Model model) {


        if (userCase.getStartingInput() == null) {
            model.addAttribute("userCase", userCase);
            model.addAttribute("uploadForm", new UploadForm());
            return "redirect:/index";
        }

        List<MatchingString> activeCase = service.processUserInput(userCase.getStartingInput());

        model.addAttribute("activeCase", activeCase);
        model.addAttribute("userCase", userCase);
        model.addAttribute("uploadForm", new UploadForm());
        return "index";
    }

    @PostMapping("/saveFromFile")
    public String saveFromFile(HttpServletRequest request, Model model,
                               @ModelAttribute("uploadForm") UploadForm uploadForm) {

        String test = service.doUpload(request, uploadForm);

        List<MatchingString> activeCase = service.processUserInput(test);

        model.addAttribute("activeCase", activeCase);
        model.addAttribute("userCase", new AnalysisCase());
        model.addAttribute("uploadForm", new UploadForm());
        return "index";

    }

    @GetMapping("/showHistory")
    public String showHistory(Model model) {


        String input = service.loadFromFile(service.getMyObj());

        List<AnalysisCase> fullHistory = service.processHistory();

        model.addAttribute("fullHistory", fullHistory);
        return "history";
    }

    @ExceptionHandler(RuntimeException.class)
    public final ResponseEntity<Exception> handleAllExceptions(RuntimeException ex) {
        return new ResponseEntity<Exception>(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
