package ar.com.vendepor.vendepor48.service;

import ar.com.vendepor.vendepor48.domain.PublicationMessage;

public interface PublicationMessageService {

    PublicationMessage save(PublicationMessage publicationMessage, Long publicationId);
}
