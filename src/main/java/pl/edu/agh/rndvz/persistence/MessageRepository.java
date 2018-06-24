package pl.edu.agh.rndvz.persistence;

import org.neo4j.driver.internal.InternalPath;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import pl.edu.agh.rndvz.model.TextMessage;

import java.util.Map;

public interface MessageRepository extends PagingAndSortingRepository<TextMessage, Long> {
    int MESSAGES = 10;

    @Query("match p =(n:TextMessage)-[:precedes*0.." + MESSAGES + "]->(m:TextMessage)\n" +
            "where id(m) ={0}\n" +
            "RETURN p")
    Iterable<Map<String, InternalPath>> getLastMessages(Long lastMessageId);


}
