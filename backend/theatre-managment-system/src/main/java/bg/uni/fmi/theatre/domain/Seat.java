package bg.uni.fmi.theatre.domain;

import java.util.Objects;

public class Seat {

    private final Long id;
    private final Long hallId;
    private String rowLabel;
    private int seatNumber;
    private String zoneCode;

    public Seat(Long id, Long hallId, String rowLabel, int seatNumber, String zoneCode) {
        if (hallId == null) {
            throw new IllegalArgumentException("hallId is required");
        } else if (rowLabel == null || rowLabel.isBlank()) {
            throw new IllegalArgumentException("rowLabel is required");
        } else if (seatNumber <= 0) {
            throw new IllegalArgumentException("seatNumber must be positive");
        }

        this.id = id;
        this.hallId = hallId;
        this.rowLabel = rowLabel;
        this.seatNumber = seatNumber;
        this.zoneCode = zoneCode;
    }

    public Long getId() {
        return id;
    }

    public Long getHallId() {
        return hallId;
    }

    public String getRowLabel() {
        return rowLabel;
    }

    public void setRowLabel(String rowLabel) {
        this.rowLabel = rowLabel;
    }

    public int getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(int seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getZoneCode() {
        return zoneCode;
    }

    public void setZoneCode(String zoneCode) {
        this.zoneCode = zoneCode;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Seat seat)) return false;
        return Objects.equals(id, seat.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Seat{id=" + id + ", hall=" + hallId + ", row=" + rowLabel + seatNumber + ", zone=" + zoneCode + "}";
    }
}