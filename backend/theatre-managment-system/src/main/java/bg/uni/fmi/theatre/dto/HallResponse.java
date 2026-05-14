package bg.uni.fmi.theatre.dto;

import bg.uni.fmi.theatre.domain.Hall;

public class HallResponse {

    private Long id;
    private String name;

    public static HallResponse from(Hall hall) {
        HallResponse hallResponse = new HallResponse();
        hallResponse.id = hall.getId();
        hallResponse.name = hall.getName();
        return hallResponse;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
