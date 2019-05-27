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
    public PublicationMessage findById(Long id) {
        return publicationMessageRepository.findById(id).orElse(null);
    }

    @Override
    public PublicationMessage save(PublicationMessage publicationMessage) {
        return publicationMessageRepository.save(publicationMessage);
    }

    @Override
    public List<PublicationMessage> findTop5ByPublicationIdOrderByLikedDescMessageDateTimeDesc(Long publicationId) {
        return publicationMessageRepository.findTop5ByPublicationIdOrderByLikedDescMessageDateTimeDesc(publicationId);
    }

    @Override
    public List<PublicationMessage> findTop10ByPublicationIdOrderByLikedDescMessageDateTimeDesc(Long publicationId) {
        return publicationMessageRepository.findTop10ByPublicationIdOrderByLikedDescMessageDateTimeDesc(publicationId);
    }

    /*
    @Override
    public PublicationMessage save(PublicationMessage publicationMessage, Long publicationId) {
        publicationMessage.setPublication(publicationRepository.findById(publicationId).orElse(null));
        return publicationMessageRepository.save(publicationMessage);
    }
    */
}
