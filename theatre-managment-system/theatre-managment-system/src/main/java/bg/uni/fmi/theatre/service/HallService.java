package bg.uni.fmi.theatre.service;

import bg.uni.fmi.theatre.config.AppLogger;
import bg.uni.fmi.theatre.domain.Hall;
import bg.uni.fmi.theatre.repository.HallRepository;
import bg.uni.fmi.theatre.repository.PerformanceRepository;
import org.springframework.stereotype.Service;

@Service
public class HallService {

    private final HallRepository hallRepository;
    private final AppLogger logger;

    public HallService(HallRepository hallRepository, AppLogger logger) {
        this.hallRepository = hallRepository;
        this.logger = logger;
    }

    public void addHall(Hall hall) {

    }
}
