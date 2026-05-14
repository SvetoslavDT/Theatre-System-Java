package bg.uni.fmi.theatre.repository.inmemory;

import bg.uni.fmi.theatre.domain.Hall;
import bg.uni.fmi.theatre.repository.HallRepository;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class InMemoryHallRepository implements HallRepository {

    private final Map<Long, Hall> store = new HashMap<>();
    private final AtomicLong idSequence = new AtomicLong(1);

    @Override
    public Hall save(Hall hall) {
        store.put(hall.getId(), hall);
        return hall;
    }

    @Override
    public Optional<Hall> findById(Long id) {
        return Optional.ofNullable(store.get(id));
    }

    @Override
    public List<Hall> findAll() {
        return List.copyOf(store.values());
    }

    @Override
    public void deleteById(Long id) {
        store.remove(id);
    }
}
