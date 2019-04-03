package ar.com.vendepor.vendepor48.controller;

import ar.com.vendepor.vendepor48.service.PublicationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PublicationController {

    private final PublicationService publicationService;

    public PublicationController(PublicationService publicationService) {
        this.publicationService = publicationService;
    }

    @GetMapping({ "", "/home"})
    public String home(Model model) {

        model.addAttribute("publications", publicationService.findAll());

        return "home";
    }

}
