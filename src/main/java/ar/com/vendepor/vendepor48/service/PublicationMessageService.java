package ar.com.vendepor.vendepor48.service;

import ar.com.vendepor.vendepor48.domain.PublicationMessage;

import java.util.List;

public interface PublicationMessageService {

    List<PublicationMessage> getByPublicationId(Long publicationId);

}
