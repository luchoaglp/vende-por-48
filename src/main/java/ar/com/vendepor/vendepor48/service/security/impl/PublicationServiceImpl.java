package ar.com.vendepor.vendepor48.service.security.impl;

import ar.com.vendepor.vendepor48.domain.Publication;
import ar.com.vendepor.vendepor48.repository.PublicationRepository;
import ar.com.vendepor.vendepor48.service.PublicationService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PublicationServiceImpl implements PublicationService {

    private final PublicationRepository publicationRepository;

    public PublicationServiceImpl(PublicationRepository publicationRepository) {
        this.publicationRepository = publicationRepository;
    }

    public List<Publication> findAll() {
        List<Publication> publications = new ArrayList<>();
        publicationRepository.findAll()
                .forEach(publications::add);
        return publications;
    }
}
