package pl.edu.agh.rndvz.persistence;

import org.springframework.data.neo4j.annotation.Query;
import pl.edu.agh.rndvz.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends PagingAndSortingRepository<User, Long> {


    @Query("MATCH (p: User) \n" +
            "OPTIONAL MATCH (p)<-[:blocked | matched | accepted]-(c: User) \n" +
            "where id(c) ={0}\n" +
            "WITH p, c \n" +
            "WHERE c IS NULL and id(p) <> {0}\n" +
            "RETURN p LIMIT {1}")
    List<User> getPossiblePairs(Long userID, Integer howMany);

    @Override
    Optional<User> findById(Long aLong);


}