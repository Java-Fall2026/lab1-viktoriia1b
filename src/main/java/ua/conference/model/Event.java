package ua.conference.model;

import ua.common.BaseEntity;
import ua.conference.util.ConferenceUtils;

import java.time.LocalDate;
import java.util.Objects;

public class Event extends BaseEntity {
    private final String eventCode;
    private final String title;
    private final Venue venue;
    private final String category;
    private final LocalDate startDate;
    private final LocalDate endDate;

    private Event(String eventCode, String title, Venue venue, String category,
                  LocalDate startDate, LocalDate endDate) {
        super();
        this.eventCode = ConferenceUtils.requireNotBlank(eventCode, "Event code");
        this.title = ConferenceUtils.requireNotBlank(title, "Title");
        this.venue = ConferenceUtils.requireNotNull(venue, "Venue");
        this.category = ConferenceUtils.normalizeCategory(category);
        this.startDate = ConferenceUtils.requireNotNull(startDate, "Start date");
        this.endDate = ConferenceUtils.requireNotNull(endDate, "End date");
        if (this.endDate.isBefore(this.startDate)) {
            throw new IllegalArgumentException("End date must be on or after start date, got: " + endDate);
        }
    }

    public static Event of(String eventCode, String title, Venue venue, String category,
                           LocalDate startDate, LocalDate endDate) {
        return new Event(eventCode, title, venue, category, startDate, endDate);
    }

    public String getEventCode() { return eventCode; }
    public String getTitle() { return title; }
    public Venue getVenue() { return venue; }
    public String getCategory() { return category; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }

    @Override public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Event that = (Event) other;
        return eventCode.equals(that.eventCode);
    }

    @Override public int hashCode() { return Objects.hash(eventCode); }

    @Override public String toString() {
        return "Event{eventCode='" + eventCode + "', title='" + title + "', venue=" + venue
                + ", category='" + category + "', startDate=" + startDate + ", endDate=" + endDate
                + ", createdAt=" + createdAt + '}';
    }
}
