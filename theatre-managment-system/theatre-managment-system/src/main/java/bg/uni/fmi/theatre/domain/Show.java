package bg.uni.fmi.theatre.domain;

import java.util.Objects;

public class Show {

    private static final int MAX_TITLE_LENGTH = 100;

    private final Long id;
    private String title;
    private String description;
    private Genre genre;
    private int durationMinutes;
    private AgeRating ageRating;

    public Show(Long id, String title, String description, Genre genre, int durationMinutes, AgeRating ageRating) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        } else if (title == null) {
            throw new IllegalArgumentException("Title length required");
        } else if (title.length() > MAX_TITLE_LENGTH) {
            throw new IllegalArgumentException("Title length exceeds maximum of " + MAX_TITLE_LENGTH);
        } else if (durationMinutes <= 0) {
            throw new IllegalArgumentException("Duration length must be greater than 0");
        }

        this.id = id;
        this.title = title;
        this.description = description;
        this.genre = genre;
        this.durationMinutes = durationMinutes;
        this.ageRating = ageRating;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public int getDurationMinutes() {
        return durationMinutes;
    }

    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    public AgeRating getAgeRating() {
        return ageRating;
    }

    public void setAgeRating(AgeRating ageRating) {
        this.ageRating = ageRating;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Show show)) return false;
        return Objects.equals(id, show.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Show{id=" + id + ", title='" + title + "', genre=" + genre + ", duration=" + durationMinutes + "min}";
    }
}
