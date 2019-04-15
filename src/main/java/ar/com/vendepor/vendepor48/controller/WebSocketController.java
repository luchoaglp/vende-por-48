package ar.com.vendepor.vendepor48.controller;

import ar.com.vendepor.vendepor48.domain.PublicationMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Slf4j
@Controller
public class WebSocketController {

    @MessageMapping("/dispute")
    @SendTo("/queue/reply")
    public void message(PublicationMessage message) {

        log.info("PublicationMessage: " + message);
    }

}
