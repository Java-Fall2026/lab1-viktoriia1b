package ua.conference.model;

import ua.conference.util.ConferenceUtils;
import java.time.LocalDate;

public class Event {
    private final String eventCode;
    private String title;
    private final Venue venue;
    private Category category;
    private LocalDate startDate;
    private LocalDate endDate;

    public Event(String eventCode, String title, Venue venue, Category category,
                 LocalDate startDate, LocalDate endDate) {
        this.eventCode = ConferenceUtils.requireNotBlank(eventCode, "Event code");
        this.title = ConferenceUtils.requireNotBlank(title, "Title");
        this.venue = ConferenceUtils.requireNotNull(venue, "Venue");
        this.category = ConferenceUtils.requireNotNull(category, "Category");
        this.startDate = ConferenceUtils.requireNotNull(startDate, "Start date");
        this.endDate = ConferenceUtils.requireNotNull(endDate, "End date");
        validateDateRange(this.startDate, this.endDate);
    }

    private static void validateDateRange(LocalDate startDate, LocalDate endDate) {
        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date must be on or after start date");
        }
    }

    public String getEventCode() { return eventCode; }
    public String getTitle() { return title; }
    public Venue getVenue() { return venue; }
    public Category getCategory() { return category; }
    public LocalDate getStartDate() { return startDate; }
    public LocalDate getEndDate() { return endDate; }

    public void setTitle(String title) { this.title = ConferenceUtils.requireNotBlank(title, "Title"); }
    public void setCategory(Category category) { this.category = ConferenceUtils.requireNotNull(category, "Category"); }

    public void setStartDate(LocalDate startDate) {
        LocalDate checkedStartDate = ConferenceUtils.requireNotNull(startDate, "Start date");
        validateDateRange(checkedStartDate, endDate);
        this.startDate = checkedStartDate;
    }

    public void setEndDate(LocalDate endDate) {
        LocalDate checkedEndDate = ConferenceUtils.requireNotNull(endDate, "End date");
        validateDateRange(startDate, checkedEndDate);
        this.endDate = checkedEndDate;
    }
}
