package ar.com.vendepor.vendepor48.repository;

import ar.com.vendepor.vendepor48.domain.PublicationMessage;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PublicationMessageRepository extends CrudRepository<PublicationMessage, Long> {

    List<PublicationMessage> findTop5ByPublicationIdOrderByLikedDescMessageDateTimeDesc(Long publicationId);
    List<PublicationMessage> findTop10ByPublicationIdOrderByLikedDescMessageDateTimeDesc(Long publicationId);

}
