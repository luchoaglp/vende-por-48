package ar.com.vendepor.vendepor48.controller;

import ar.com.vendepor.vendepor48.domain.PublicationMessage;
import ar.com.vendepor.vendepor48.exception.RestException;
import ar.com.vendepor.vendepor48.security.UserPrincipal;
import ar.com.vendepor.vendepor48.service.ClientService;
import ar.com.vendepor.vendepor48.service.PublicationMessageService;
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

    private final PublicationMessageService publicationMessageService;
    private final ClientService clientService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public PublicationMessageController(PublicationMessageService publicationMessageService, ClientService clientService, SimpMessagingTemplate simpMessagingTemplate) {
        this.publicationMessageService = publicationMessageService;
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

        publicationMessage.setMessageDateTime(LocalDateTime.now());
        publicationMessage.setClient(clientService.getById(user.getId()));
        publicationMessageService.save(publicationMessage, publicationId);

        simpMessagingTemplate.convertAndSend("/queue/reply", publicationMessage);

        return ResponseEntity.ok(publicationMessage);
    }

}
