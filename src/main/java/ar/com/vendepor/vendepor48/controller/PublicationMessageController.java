package ar.com.vendepor.vendepor48.controller;

import ar.com.vendepor.vendepor48.domain.PublicationMessage;
import ar.com.vendepor.vendepor48.service.PublicationMessageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.time.LocalDateTime;

@Slf4j
//@EnableScheduling
@Controller
public class PublicationMessageController {

    private final PublicationMessageService publicationMessageService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    public PublicationMessageController(PublicationMessageService publicationMessageService, SimpMessagingTemplate simpMessagingTemplate) {
        this.publicationMessageService = publicationMessageService;
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @PostMapping("/publication/message/{publicationId}")
    public String saveOrUpdatePublication(@PathVariable("publicationId") Long publicationId,
                                          PublicationMessage publicationMessage) {

        publicationMessage.setMessageDateTime(LocalDateTime.now());

        publicationMessageService.save(publicationMessage, publicationId);

        return "redirect:/publication/detail/" + publicationId;
    }

    @MessageMapping("/dispute")
    @SendTo("/queue/reply")
    public PublicationMessage message(PublicationMessage message) {

        log.info("Message: " + message);

        message.setMessageDateTime(LocalDateTime.now());

        return message;
    }
/*
    @Scheduled(fixedRate = 50000)
    public void greeting() throws InterruptedException {
        Thread.sleep(1000); // simulated delay
        System.out.println("scheduled");
        this.simpMessagingTemplate.convertAndSend("/queue/reply", "Hello");
    }
*/
}
