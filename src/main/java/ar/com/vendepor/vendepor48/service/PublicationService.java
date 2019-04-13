package ar.com.vendepor.vendepor48.service;

import ar.com.vendepor.vendepor48.domain.Publication;

import java.util.List;

public interface PublicationService {

    Publication findById(Long id);
    List<Publication> findAll();

    Publication save(Publication publication);
}
