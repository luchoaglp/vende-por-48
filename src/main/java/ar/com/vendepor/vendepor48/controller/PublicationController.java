package ar.com.vendepor.vendepor48.controller;

import ar.com.vendepor.vendepor48.service.PublicationService;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;

@Controller
public class PublicationController {

    private final PublicationService publicationService;
    private final ResourceLoader resourceLoader;

    public PublicationController(PublicationService publicationService, ResourceLoader resourceLoader) {
        this.publicationService = publicationService;
        this.resourceLoader = resourceLoader;
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

    @GetMapping(value = "/publication/img/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] publicationImg(@PathVariable("id") Long id) throws IOException {

        Resource img = resourceLoader.getResource("file:/home/ele/Datos/workspace/files/vende-por-48/img/" + id + ".jpg");

        return IOUtils.toByteArray(img.getInputStream());
    }

}
