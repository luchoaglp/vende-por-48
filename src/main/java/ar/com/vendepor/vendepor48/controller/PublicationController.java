package ar.com.vendepor.vendepor48.controller;

import ar.com.vendepor.vendepor48.domain.Client;
import ar.com.vendepor.vendepor48.domain.Publication;
import ar.com.vendepor.vendepor48.exception.MvcException;
import ar.com.vendepor.vendepor48.security.UserPrincipal;
import ar.com.vendepor.vendepor48.service.ClientService;
import ar.com.vendepor.vendepor48.service.PublicationService;
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.security.Principal;
import java.time.LocalDateTime;

@Controller
public class PublicationController {

    private final PublicationService publicationService;
    private final ResourceLoader resourceLoader;
    private final ClientService clientService;

    public PublicationController(PublicationService publicationService, ResourceLoader resourceLoader, ClientService clientService) {
        this.publicationService = publicationService;
        this.resourceLoader = resourceLoader;
        this.clientService = clientService;
    }

    @GetMapping("")
    public String home(Model model) {

        model.addAttribute("publications", publicationService.findAll());

        return "publication/index";
    }

    @GetMapping("/publication/detail/{id}")
    public String publicationDetail(@PathVariable("id") Long id,
                                    Model model,
                                    Principal principal) {

        Publication publication = publicationService.findById(id);

        model.addAttribute("publication", publicationService.findById(id));

        if(principal == null) {
            return "publication/buyer/detail";
        }

        UserPrincipal user = (UserPrincipal) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();

        if(publication.getClient().getId().equals(user.getId())) {
            return "publication/seller/detail";
        }

        return "publication/buyer/detail";
    }

    @GetMapping(value = "/publication/img/{id}", produces = MediaType.IMAGE_JPEG_VALUE)
    public @ResponseBody byte[] publicationImage(@PathVariable("id") Long id) throws IOException {

        Resource img = resourceLoader.getResource("file:/home/ele/Datos/workspace/files/vende-por-48/img/" + id + ".jpg");

        if(!img.exists()) {

            img = resourceLoader.getResource("https://dummyimage.com/400x400/06c2c2/11027d.jpg");
        }

        return IOUtils.toByteArray(img.getInputStream());
    }

    @GetMapping("/publication/new")
    public String newPublication(Model model) {

        Publication publication = new Publication();
        publication.setAmount(0.0);
        publication.setStartDateTime(LocalDateTime.now());

        model.addAttribute("publication", publication);

        return "publication/form";
    }

    @GetMapping("/publication/update/{id}")
    public String updatePublication(@PathVariable("id") Long id,
                                    Model model) {

        model.addAttribute("publication", publicationService.findById(id));

        return "publication/form";
    }

    @PostMapping("/publication")
    public String saveOrUpdatePublication(Publication publication) {

        UserPrincipal user = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Client client = clientService.getById(user.getId());

        // Update
        if(publication.getId() != null) {

            // The publication does not belong to the client
            if(!publicationService.findById(publication.getId())
                    .getClient().getId().equals(client.getId())) {
                throw new MvcException("No se puede modificar");
            }
        }

        publication.setClient(client);

        publication = publicationService.save(publication);

        return "redirect:/publication/detail/" + publication.getId();
    }

}
