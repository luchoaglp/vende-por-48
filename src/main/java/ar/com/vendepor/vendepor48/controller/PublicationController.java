package ar.com.vendepor.vendepor48.controller;

import ar.com.vendepor.vendepor48.service.PublicationService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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

    @GetMapping("/publication/{id}")
    public String publication(@PathVariable("id") Long id,
                              Model model) {

        model.addAttribute("publication", publicationService.findById(id));

        return "publication";
    }

}
