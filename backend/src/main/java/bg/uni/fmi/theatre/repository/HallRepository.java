package bg.uni.fmi.theatre.repository;

import bg.uni.fmi.theatre.domain.Hall;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface HallRepository extends JpaRepository<Hall, Long> {
//    Hall save(Hall hall);
//    Optional<Hall> findById(Long id);
//    List<Hall> findAll();
//    void deleteById(Long id);
}
