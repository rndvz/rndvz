package pl.edu.agh.rndvz.persistence;

import pl.edu.agh.rndvz.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends PagingAndSortingRepository<User, Long> {

    List<User> findUsersByAvgRateBetween(int from, int to);
    User findUserByFirstName(@Param("firstName") String firstName);

    @Override
    Optional<User> findById(Long aLong);


}