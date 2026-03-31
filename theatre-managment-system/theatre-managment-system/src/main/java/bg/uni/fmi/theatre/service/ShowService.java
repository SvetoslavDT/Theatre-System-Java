package bg.uni.fmi.theatre.service;

import bg.uni.fmi.theatre.domain.Genre;
import bg.uni.fmi.theatre.domain.Performance;
import bg.uni.fmi.theatre.domain.Show;
import bg.uni.fmi.theatre.repository.PerformanceRepository;
import bg.uni.fmi.theatre.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class ShowService {

    private static final int DEFAULT_PAGE_SIZE = 10;

    private final ShowRepository showRepository;
    private final PerformanceRepository performanceRepository;

    @Autowired
    public ShowService(ShowRepository showRepository, PerformanceRepository performanceRepository) {
        if (showRepository == null) {
            throw new IllegalArgumentException("showRepository is required");
        } else if (performanceRepository == null) {
            throw new IllegalArgumentException("performanceRepository is required");
        }

        this.showRepository = showRepository;
        this.performanceRepository = performanceRepository;
    }

    public Show addShow(Show show) {
        if (show == null) {
            throw new IllegalArgumentException("show must not be null");
        }

        return showRepository.save(show);
    }

    public Optional<Show> getShowById(Long id) {
        if (showRepository.findById(id).isEmpty()) {
            throw new IllegalArgumentException("Show not found");
        }

        return showRepository.findById(id);
    }

    public List<Show> getAllShows() {
        return showRepository.findAll();
    }

    public List<Show> searchShows(String titleQuery, Genre genre, int page, int size) {
        if (page < 0) {
            throw new IllegalArgumentException("page must not be negative");
        } else if (size <= 0) {
            throw new IllegalArgumentException("size must be positive");
        }

        List<Show> results = showRepository.findAll().stream()
            .filter(s -> titleQuery.isBlank() || titleQuery.isBlank() ||
                s.getTitle().toLowerCase().contains(titleQuery.toLowerCase()))
            .filter(s -> genre == null || genre.equals(s.getGenre()))
            .sorted(Comparator.comparing(Show::getTitle))
            .toList();

        int fromIndex = page * size;
        if (fromIndex >= results.size()) {
            return List.of();
        }

        int toIndex = Math.min(fromIndex + size, results.size());
        return results.subList(fromIndex, toIndex);
    }

    public List<Show> searchShows(String titleQuery, Genre genre) {
        return searchShows(titleQuery, genre, 0, defaultPageSize);
    }

    public Performance addPerformance(Performance performance) {
        if (performance == null) {
            throw new IllegalArgumentException("Performance must not be null");
        } else if (!showRepository.existsById(performance.getShowId())) {
            throw new IllegalArgumentException("Show not found: " + performance.getShowId());
        }

        return performanceRepository.save(performance);
    }

    public List<Performance> findPerformancesByShow(Long showId) {
        if (showId == null) {
            throw new IllegalArgumentException("showId must not be null");
        } else if (!showRepository.existsById(showId)) {
            throw new IllegalArgumentException("Show not found: " + showId);
        }

        return performanceRepository.findByShowId(showId);
    }
}
