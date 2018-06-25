package pl.edu.agh.rndvz.persistence;

import org.glassfish.jersey.internal.inject.Custom;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.web.bind.annotation.CrossOrigin;
import pl.edu.agh.rndvz.model.User;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;
import java.util.Optional;

@CrossOrigin(origins = "*")
@RepositoryRestResource(collectionResourceRel = "users", path = "users")
public interface UserRepository extends PagingAndSortingRepository<User, Long> {


    @Query("MATCH (p: User)\n" +
            "OPTIONAL MATCH (p)<-[:blocked | :matched | :accepted]-(c: User)\n" +
            "where id(c) = {0}\n" +
            "WITH p, c\n" +
            "WHERE c IS NULL and id(p) <> {0} \n" +
            "\tand (p.avgRate - p.acceptedRateDifference) < {2}  and p.avgRate + p.acceptedRateDifference >{2}\n" +
            "    and  {2}-{3} < p.avgRate and {2}+{3} > p.avgRate\n" +
            "    and  (p.sexPreference ={4} or p.sexPreference = \"all\") \n" +
            "    and  ({5} = p.sex or {5} = \"all\" )\n" +
//            "    and  date({6}) -duration(\"P\"+{7}+\"Y\")   < date(p.birthDate)\n" +
//            "    and  date({6}) + duration(\"P\"+{7}+\"Y\")  > date(p.birthDate)\n" +
            "    and  sqrt((p.latitude -{8})^2 + (p.longitude -{9})^2) < {10}\n" +
            "    RETURN p LIMIT {1}")
    List<User> getPossiblePairs(Long userID,
                                Integer howMany,
                                Double avgRate,
                                Integer acceptedRateDifference,
                                String sex,
                                String sexPreference,
                                String birthDate,
                                Integer acceptedYearDifference,
                                Double latitude,
                                Double longitude,
                                Integer distance
    );

    @Override
    Optional<User> findById(Long aLong);


    Optional<User> findUserByLogin(String login);



}