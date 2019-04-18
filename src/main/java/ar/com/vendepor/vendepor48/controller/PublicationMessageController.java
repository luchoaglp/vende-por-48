package ar.com.vendepor.vendepor48.controller;

import ar.com.vendepor.vendepor48.domain.Publication;
import ar.com.vendepor.vendepor48.domain.PublicationMessage;
import ar.com.vendepor.vendepor48.exception.RestException;
import ar.com.vendepor.vendepor48.security.UserPrincipal;
import ar.com.vendepor.vendepor48.service.ClientService;
import ar.com.vendepor.vendepor48.service.PublicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.time.LocalDateTime;

@RestController
public class PublicationMessageController {

    private final PublicationService publicationService;
    private final ClientService clientService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public PublicationMessageController(PublicationService publicationService, ClientService clientService, SimpMessagingTemplate simpMessagingTemplate) {
        this.publicationService = publicationService;
        this.clientService = clientService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }


    @PostMapping("/publication/message/{publicationId}")
    public ResponseEntity<?> createPaymentLink(@PathVariable("publicationId") Long publicationId,
                                               @RequestBody PublicationMessage publicationMessage,
                                               Principal principal) {

        if(principal == null) {
            throw new RestException("Debes ingresar para realizar tu oferta.");
        }

        UserPrincipal user = (UserPrincipal) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();

        Publication publication = publicationService.findById(publicationId);

        // The message belongs to the publication owner
        if(user.getId().equals(publication.getClient().getId())) {
            throw new RestException("No podés realizar una oferta en tu publicación.");
        }

        publicationMessage.setMessageDateTime(LocalDateTime.now());
        publicationMessage.setClient(clientService.getById(user.getId()));

        publication.addPublicationMessage(publicationMessage);

        publicationService.save(publication);

        simpMessagingTemplate.convertAndSend("/queue/reply", publicationMessage);

        return ResponseEntity.ok(publicationMessage);
    }

}
