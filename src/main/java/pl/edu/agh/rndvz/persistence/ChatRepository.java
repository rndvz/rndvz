package pl.edu.agh.rndvz.persistence;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import pl.edu.agh.rndvz.model.Chat;


@RepositoryRestResource(collectionResourceRel = "chats", path = "chats")
public interface ChatRepository extends PagingAndSortingRepository<Chat, Long> {
}
