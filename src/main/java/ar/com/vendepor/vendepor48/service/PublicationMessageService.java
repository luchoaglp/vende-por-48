package ar.com.vendepor.vendepor48.service;

import ar.com.vendepor.vendepor48.domain.PublicationMessage;

import java.util.List;

public interface PublicationMessageService {

    List<PublicationMessage> findTop5ByPublicationIdOrderByLikedDescMessageDateTimeDesc(Long publicationId);
    List<PublicationMessage> findTop10ByPublicationIdOrderByLikedDescMessageDateTimeDesc(Long publicationId);

}
