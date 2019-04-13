package ar.com.vendepor.vendepor48.service.impl;

import ar.com.vendepor.vendepor48.domain.PublicationMessage;
import ar.com.vendepor.vendepor48.repository.PublicationMessageRepository;
import ar.com.vendepor.vendepor48.repository.PublicationRepository;
import ar.com.vendepor.vendepor48.service.PublicationMessageService;
import org.springframework.stereotype.Service;

@Service
public class PublicationMessageServiceImpl implements PublicationMessageService {

    private final PublicationMessageRepository publicationMessageRepository;
    private final PublicationRepository publicationRepository;

    public PublicationMessageServiceImpl(PublicationMessageRepository publicationMessageRepository, PublicationRepository publicationRepository) {
        this.publicationMessageRepository = publicationMessageRepository;
        this.publicationRepository = publicationRepository;
    }

    @Override
    public PublicationMessage save(PublicationMessage publicationMessage, Long publicationId) {
        publicationMessage.setPublication(publicationRepository.findById(publicationId).orElse(null));
        return publicationMessageRepository.save(publicationMessage);
    }
}
