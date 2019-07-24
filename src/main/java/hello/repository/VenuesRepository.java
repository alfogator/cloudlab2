package hello.repository;

import hello.model.Venue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface VenuesRepository extends JpaRepository<Venue, Long>{
    List<Venue> findByName(@Param("name") String name);
}
