package bg.uni.fmi.theatre.service;

import bg.uni.fmi.theatre.config.AppLogger;
import bg.uni.fmi.theatre.config.TheatreProperties;
import bg.uni.fmi.theatre.domain.Genre;
import bg.uni.fmi.theatre.domain.Show;
import bg.uni.fmi.theatre.dto.ShowRequest;
import bg.uni.fmi.theatre.dto.ShowResponse;
import bg.uni.fmi.theatre.exception.NotFoundException;
import bg.uni.fmi.theatre.exception.ValidationException;
import bg.uni.fmi.theatre.repository.ShowRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Service for Show CRUD operations.
 * Accepts {@link bg.uni.fmi.theatre.dto.ShowRequest} DTOs and returns {@link bg.uni.fmi.theatre.dto.ShowResponse} DTOs;
 * the {@link bg.uni.fmi.theatre.domain.Show} entity stays internal.
 *
 * <p>Layering: {@code ShowService} → {@code ShowRepository} only.
 */
@Service
public class ShowService {

    private static final int DEFAULT_PAGE_SIZE = 10;

    private final ShowRepository showRepository;
    private final AppLogger logger;
    private final int defaultPageSize;

    private final AtomicLong idSeq = new AtomicLong(100);

    public ShowService(ShowRepository showRepository, AppLogger logger, TheatreProperties properties) {
//        if (showRepository == null) {
//            throw new IllegalArgumentException("showRepository is required");
//        } else if (performanceRepository == null) {
//            throw new IllegalArgumentException("performanceRepository is required");
//        }

        this.showRepository = showRepository;
        this.logger = logger;
        this.defaultPageSize = properties.getDefaultPageSize();
    }

    /**
     * Persists a new Show.
     * Generates an ID via an in-memory sequence (week 06 uses an in-memory repository).
     *
     * @param req the Show data; must not be {@code null}
     * @return the saved Show as a {@link bg.uni.fmi.theatre.dto.ShowResponse}
     * @throws bg.uni.fmi.theatre.exception.ValidationException if {@code req} is {@code null}
     */
    public ShowResponse addShow(ShowRequest req) {
        if (req == null) {
            throw new ValidationException("show must not be null");
        }
        Show show = new Show(idSeq.getAndIncrement(), req.getTitle(), req.getDescription(), req.getGenre(),
            req.getDurationMinutes(), req.getAgeRating());
        logger.debug("Adding show: " + show.getTitle());
        Show saved = showRepository.save(show);
        logger.info("Show added: [" + saved.getId() + "] " + saved.getTitle());

        return ShowResponse.from(saved);
    }

    // Not sure about this one
//    public Show addShow(String title, String genreStr, int durationMinutes) {
//        if (title == null || title.isBlank()) {
//            throw new IllegalArgumentException("Title is required");
//        }
//
//        Genre genre;
//        try {
//            genre = Genre.valueOf(genreStr.toUpperCase());
//        } catch (Exception e) {
//            throw new IllegalArgumentException("Invalid genre: " + genreStr);
//        }
//
//        Long id = null;
//
//        // ако използваш InMemory repo
//        if (showRepository instanceof bg.uni.fmi.theatre.repository.inmemory.InMemoryShowRepository repo) {
//            id = repo.nextId();
//        }
//
//        Show show = new Show(id, title, "", genre, durationMinutes, null);
//        return showRepository.save(show);
//    }

    /**
     * Returns a Show by its identifier.
     * Also used by {@link PerformanceService} for show-existence validation.
     *
     * @param id must not be {@code null}
     * @return the matching {@link bg.uni.fmi.theatre.dto.ShowResponse}
     * @throws bg.uni.fmi.theatre.exception.NotFoundException if no Show exists for {@code id}
     */
    public ShowResponse getShowById(Long id) {
        if (id == null) {
            throw new ValidationException("id must not be null");
        }

        return showRepository.findById(id).map(ShowResponse::from).orElseThrow(() -> {
            logger.error("Show not found: id=" + id);
            return new NotFoundException("Show", id);
        });
    }

    /**
     * Optionally returns a Show; returns {@link java.util.Optional#empty()} instead of throwing when absent.
     */
    public Optional<ShowResponse> findShowById(Long id) {
        return showRepository.findById(id).map(ShowResponse::from);
    }

    /**
     * Returns all Shows without pagination.
     */
    public List<ShowResponse> getAllShows() {
        logger.trace("getAllShows called");
        return showRepository.findAll().stream()
            .map(ShowResponse::from)
            .toList();
    }

    /**
     * Replaces all mutable fields of an existing Show (fetch → mutate → save).
     *
     * @throws bg.uni.fmi.theatre.exception.NotFoundException if no Show exists for {@code id}
     */
    public ShowResponse updateShow(Long id, ShowRequest req) {

        Show toUpdate = showRepository.findById(id).orElseThrow(() -> new NotFoundException("Show", id));
        toUpdate.setTitle(req.getTitle());
        toUpdate.setDescription(req.getDescription());
        toUpdate.setGenre(req.getGenre());
        toUpdate.setDurationMinutes(req.getDurationMinutes());
        toUpdate.setAgeRating(req.getAgeRating());
        logger.info("Show updated: id=" + id);

        return ShowResponse.from(showRepository.save(toUpdate));
    }

    /**
     * Searches Shows by optional title substring and genre, returning a paginated slice.
     * Results are sorted alphabetically by title.
     *
     * @param titleQuery case-insensitive title substring (nullable/blank = no filter)
     * @param genre genre filter (null = all genres)
     * @param page zero-based page index; must be &gt;= 0
     * @param size results per page; must be &gt; 0
     */
    public List<Show> searchShows(String titleQuery, Genre genre, int page, int size) {
        if (page < 0) {
            throw new ValidationException("page must not be negative");
        } else if (size <= 0) {
            throw new ValidationException("size must be positive");
        }

        logger.debug("Searching shows — title='" + titleQuery + "', genre=" + genre + ", page=" + page);

        List<ShowResponse> allResults = showRepository.findAll().stream()
            .filter(s -> titleQuery == null || titleQuery.isBlank()
                || s.getTitle().toLowerCase().contains(titleQuery.toLowerCase()))
            .filter(s -> genre == null || genre.equals(s.getGenre()))
            .sorted(Comparator.comparing(Show::getTitle))
            .map(ShowResponse::from)
            .toList();

        long total = allResults.size();
        logger.info("Search returned " + total + " total results");
        int fromIndex = page * size;
        List<ShowResponse> pageContent = fromIndex >= allResults.size()
            ? List.of() : allResults.subList(fromIndex, Math.min(fromIndex + size, allResults.size()));

        return new PageResponse<>(pageContent, page, size, total);
    }

    public PageResponse<ShowResponse> searchShows(String titleQuery, Genre genre) {
        return searchShows(titleQuery, genre, 0, defaultPageSize);
    }

    /**
     * Deletes a Show by its identifier.
     *
     * @throws bg.uni.fmi.theatre.exception.NotFoundException if no Show exists for {@code id}
     */
    public void deleteShow(Long id) {
        showRepository.findById(id).orElseThrow(() -> new NotFoundException("Show", id));
        showRepository.deleteById(id);
        logger.info("Show deleted: id=" + id);
    }
}
