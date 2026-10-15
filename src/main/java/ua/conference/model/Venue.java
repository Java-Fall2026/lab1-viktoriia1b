package ua.conference.model;

import ua.common.BaseEntity;
import ua.conference.util.ConferenceUtils;

import java.util.Objects;

public class Venue extends BaseEntity {
    private final String venueCode;
    private final String name;
    private final int capacity;

    public Venue(String venueCode, String name, int capacity) {
        super();
        this.venueCode = ConferenceUtils.requireNotBlank(venueCode, "Venue code");
        this.name = ConferenceUtils.requireNotBlank(name, "Venue name");
        this.capacity = ConferenceUtils.requirePositive(capacity, "Capacity");
    }

    public String getVenueCode() { return venueCode; }
    public String getName() { return name; }
    public int getCapacity() { return capacity; }

    @Override public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Venue that = (Venue) other;
        return venueCode.equals(that.venueCode);
    }

    @Override public int hashCode() { return Objects.hash(venueCode); }

    @Override public String toString() {
        return "Venue{venueCode='" + venueCode + "', name='" + name + "', capacity=" + capacity
                + ", createdAt=" + createdAt + '}';
    }
}
