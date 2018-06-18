package pl.edu.agh.rndvz.persistence;

import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.edu.agh.rndvz.model.Chat;

import java.util.Optional;


public interface ChatRepository extends PagingAndSortingRepository<Chat, Long> {

    @Query("Match (u:User)<--(c:Chat)--> (v:User) \n" +
            "where id(u) ={0} and id(v) = {1} \n" +
            "return c")
    Optional<Chat> findByUsers(Long userId, Long userId2);


}
