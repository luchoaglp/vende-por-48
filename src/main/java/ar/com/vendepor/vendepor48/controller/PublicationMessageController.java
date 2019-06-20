package ar.com.vendepor.vendepor48.controller;

import ar.com.vendepor.vendepor48.domain.Publication;
import ar.com.vendepor.vendepor48.domain.PublicationMessage;
import ar.com.vendepor.vendepor48.exception.RestException;
import ar.com.vendepor.vendepor48.security.UserPrincipal;
import ar.com.vendepor.vendepor48.service.ClientService;
import ar.com.vendepor.vendepor48.service.PublicationMessageService;
import ar.com.vendepor.vendepor48.service.PublicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
public class PublicationMessageController {

    private final PublicationService publicationService;
    private final PublicationMessageService publicationMessageService;
    private final ClientService clientService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public PublicationMessageController(PublicationService publicationService, PublicationMessageService publicationMessageService, ClientService clientService, SimpMessagingTemplate simpMessagingTemplate) {
        this.publicationService = publicationService;
        this.publicationMessageService = publicationMessageService;
        this.clientService = clientService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @GetMapping("/publication/messages/buyer/{publicationId}")
    public ResponseEntity<?> getBuyerPublicationMessages(@PathVariable("publicationId") Long publicationId,
                                                         Principal principal) {

        List<PublicationMessage> messages = publicationMessageService.findTop5ByPublicationIdOrderByLikedDescMessageDateTimeDesc(publicationId);

        if(principal == null) {
            messages.forEach(message -> {
                message.setDescription(null);
                message.getClient().setEmail("secret@email.com");
            });
        } else {

            Publication publication = publicationService.findById(publicationId);

            UserPrincipal user = (UserPrincipal) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();

            // The publication does not belong to the owner
            if (!user.getId().equals(publication.getClient().getId())) {
                messages.forEach(message -> {
                    message.setDescription(null);
                    message.getClient().setEmail("secret@email.com");
                });
            }
        }

        return ResponseEntity.ok(messages);
    }

    @GetMapping("/publication/messages/seller/{publicationId}")
    public ResponseEntity<?> getSellerPublicationMessages(@PathVariable("publicationId") Long publicationId,
                                                          Principal principal) {

        List<PublicationMessage> messages = publicationMessageService.findTop10ByPublicationIdOrderByLikedDescMessageDateTimeDesc(publicationId);

        if(principal == null) {
            messages.forEach(message -> message.setDescription(null));
        } else {

            Publication publication = publicationService.findById(publicationId);

            UserPrincipal user = (UserPrincipal) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();

            // The publication does not belong to the owner
            if (!user.getId().equals(publication.getClient().getId())) {
                throw new RestException("No eres el propietario de esta oferta.");
            }
        }

        return ResponseEntity.ok(messages);
    }

    @PostMapping("/publication/message/{publicationId}")
    public ResponseEntity<?> createPublicationMessage(@PathVariable("publicationId") Long publicationId,
                                                      @RequestBody PublicationMessage message,
                                                      Principal principal) {

        if(principal == null) {
            throw new RestException("Debes ingresar para realizar tu oferta.");
        }

        Publication publication = publicationService.findById(publicationId);

        UserPrincipal user = (UserPrincipal) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();

        // The message belongs to the publication owner
        if(user.getId().equals(publication.getClient().getId())) {
            throw new RestException("No podés realizar una oferta en tu publicación.");
        }

        message.setPublication(publication);
        message.setClient(clientService.getById(user.getId()));
        message.setMessageDateTime(LocalDateTime.now());
        message.setLiked(false);
        message.setSold(false);

        publicationMessageService.save(message);

        simpMessagingTemplate.convertAndSend("/queue/seller", message);
        message.setDescription(null);
        simpMessagingTemplate.convertAndSend("/queue/buyer", message);

        return ResponseEntity.ok(message);
    }

    @PostMapping("/publication/message/{publicationId}/like/{messageId}")
    public ResponseEntity<?> messageLiked(@PathVariable("publicationId") Long publicationId,
                                          @PathVariable("messageId") Long messageId,
                                          Principal principal) {

        if(principal == null) {
            throw new RestException("Debes ingresar para afirmar que te gusta el mensaje.");
        }

        PublicationMessage message = publicationMessageService.findById(messageId);

        if(!message.getPublication().getId().equals(publicationId)) {
            throw new RestException("No podes realizar una oferta en una publicación distinta a la que estás visualizando.");
        }

        message.setLiked(true);

        publicationMessageService.save(message);

        simpMessagingTemplate.convertAndSend("/queue/seller", message);
        message.setDescription(null);
        simpMessagingTemplate.convertAndSend("/queue/buyer", message);

        return ResponseEntity.ok(message);
    }

    @PostMapping("/publication/message/{publicationId}/sell/{messageId}")
    public ResponseEntity<?> soldPublication(@PathVariable("publicationId") Long publicationId,
                                             @PathVariable("messageId") Long messageId,
                                             Principal principal) {

        if(principal == null) {
            throw new RestException("Debes ingresar para vender tu oferta.");
        }

        Publication publication = publicationService.findById(publicationId);

        UserPrincipal user = (UserPrincipal) ((UsernamePasswordAuthenticationToken) principal).getPrincipal();

        if(!user.getId().equals(publication.getClient().getId())) {
            throw new RestException("No podés vender una oferta que no sea de tu propiedad.");
        }

        publication.setSold(true);

        PublicationMessage message = null;

        for(PublicationMessage publicationMessage : publication.getMessages()) {
            if(publicationMessage.getId().equals(messageId)) {
                publicationMessage.setSold(true);
                message = publicationMessage;
                break;
            }
        }

        publicationService.save(publication);

        simpMessagingTemplate.convertAndSend("/queue/seller", message);
        message.setDescription(null);
        simpMessagingTemplate.convertAndSend("/queue/buyer", message);

        return ResponseEntity.ok(message);
    }

}
