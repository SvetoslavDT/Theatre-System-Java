package bg.uni.fmi.theatre.domain;

import java.util.Objects;

public class Hall {

    private final Long id;
    private String name;

    public Hall(Long id, String name) {
        if (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        } else if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name is required");
        }
        this.id = id;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Hall hall)) return false;
        return Objects.equals(id, hall.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Hall{id=" + id + ", name='" + name + "'}";
    }
}