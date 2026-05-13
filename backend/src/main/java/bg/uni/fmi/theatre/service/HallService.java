package bg.uni.fmi.theatre.service;

import bg.uni.fmi.theatre.config.AppLogger;
import bg.uni.fmi.theatre.domain.Hall;
import bg.uni.fmi.theatre.dto.HallResponse;
import bg.uni.fmi.theatre.exception.NotFoundException;
import bg.uni.fmi.theatre.exception.ValidationException;
import bg.uni.fmi.theatre.repository.HallRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HallService {

    private final HallRepository hallRepository;
    private final AppLogger logger;

    public HallService(HallRepository hallRepository, AppLogger logger) {
        this.hallRepository = hallRepository;
        this.logger = logger;
    }

    public HallResponse addHall(Hall hall) {
        if (hall == null) {
            throw new ValidationException("Hall cannot be null");
        }

        logger.debug("Adding Hall: " + hall.getName());
        hallRepository.save(hall);
        logger.info("Added Hall: [" + hall.getId() + "] " + hall.getName());

        return HallResponse.from(hall);
    }

    public HallResponse getShowById(Long id) {
        if (id == null) {
            throw new ValidationException("id must not be null");
        }

        return hallRepository.findById(id).map(HallResponse::from).orElseThrow(() -> {
            logger.error("Hall not found: id=" + id);
            return new NotFoundException("Hall", id);
        });
    }

    public Optional<HallResponse> getHallById(Long id) {
        return hallRepository.findById(id).map(HallResponse::from);
    }

    public List<HallResponse> getAllHalls() {
        return hallRepository.findAll().stream()
            .map(HallResponse::from)
            .toList();
    }

    public HallResponse updateHall(Long id, Hall hall) {
        Hall toUpdate = hallRepository.findById(id).orElseThrow(() -> new NotFoundException("Hall", id));

        toUpdate.setName(hall.getName());
        logger.info("Hall updated: id=" + id);

        return HallResponse.from(hallRepository.save(toUpdate));
    }

    public void deleteHall(Long id) {
        if (id == null) {
            throw new ValidationException("id must not be null");
        }
        hallRepository.findById(id).orElseThrow(() -> new NotFoundException("Hall", id));

        hallRepository.deleteById(id);
        logger.info("Deleted Hall: id=" + id);
    }
}
