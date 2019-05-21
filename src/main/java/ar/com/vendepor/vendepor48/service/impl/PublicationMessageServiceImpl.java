package ar.com.vendepor.vendepor48.service.impl;

import ar.com.vendepor.vendepor48.domain.PublicationMessage;
import ar.com.vendepor.vendepor48.repository.PublicationMessageRepository;
import ar.com.vendepor.vendepor48.service.PublicationMessageService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PublicationMessageServiceImpl implements PublicationMessageService {

    private final PublicationMessageRepository publicationMessageRepository;

    public PublicationMessageServiceImpl(PublicationMessageRepository publicationMessageRepository) {
        this.publicationMessageRepository = publicationMessageRepository;
    }

    @Override
    public List<PublicationMessage> getByPublicationId(Long publicationId) {
        return publicationMessageRepository.findTop5ByPublicationIdOrderByLikedDescMessageDateTimeDesc(publicationId);
    }

    /*
    @Override
    public PublicationMessage save(PublicationMessage publicationMessage, Long publicationId) {
        publicationMessage.setPublication(publicationRepository.findById(publicationId).orElse(null));
        return publicationMessageRepository.save(publicationMessage);
    }
    */
}
