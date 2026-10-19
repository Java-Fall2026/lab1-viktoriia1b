package ua.conference.model;

import ua.conference.util.ConferenceUtils;

public record Venue(String venueCode, String name, int capacity) {
    public Venue {
        venueCode = ConferenceUtils.requireNotBlank(venueCode, "Venue code");
        name = ConferenceUtils.requireNotBlank(name, "Venue name");
        ConferenceUtils.requirePositive(capacity, "Capacity");
    }
}
