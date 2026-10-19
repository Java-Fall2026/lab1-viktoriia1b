package ua.conference;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ua.conference.model.Category;
import ua.conference.model.Event;
import ua.conference.model.EventRegistration;
import ua.conference.model.Participant;
import ua.conference.model.TicketType;
import ua.conference.model.Venue;
import ua.conference.util.ConferenceUtils;
import java.time.LocalDate;
import static org.junit.jupiter.api.Assertions.*;

class ConferenceDomainTest {
    private Venue venue() { return new Venue("HALL-A", "Main Hall", 300); }
    private Event event() {
        return new Event("CONF-1", "Conference", venue(), Category.TECH,
                LocalDate.of(2026, 11, 10), LocalDate.of(2026, 11, 12));
    }

    @Test @DisplayName("Creates a participant and normalizes the email")
    void createsParticipantAndNormalizesEmail() {
        assertEquals("alice@example.com", new Participant(" ALICE@EXAMPLE.COM ", "Alice Example", "Example Labs").email());
    }

    @Test @DisplayName("Calculates the inclusive event duration")
    void calculatesEventDuration() { assertEquals(3, ConferenceUtils.eventDays(event())); }

    @Test @DisplayName("Calculates days between registration and event start")
    void calculatesDaysBeforeEvent() {
        Event event = event();
        EventRegistration registration = new EventRegistration(event,
                new Participant("alice@example.com", "Alice Example", "Example Labs"), TicketType.STANDARD,
                LocalDate.of(2026, 11, 5));
        assertEquals(5, ConferenceUtils.daysBeforeEvent(registration));
    }

    @Test @DisplayName("Rejects a blank event title")
    void rejectsBlankEventTitle() {
        Event event = event();
        assertThrows(IllegalArgumentException.class, () -> event.setTitle(" "));
        assertEquals("Conference", event.getTitle());
    }

    @Test @DisplayName("Rejects a start date after the event end date without changing the event")
    void rejectsInvalidStartDateWithoutMutation() {
        Event event = event();
        assertThrows(IllegalArgumentException.class, () -> event.setStartDate(LocalDate.of(2026, 11, 13)));
        assertEquals(LocalDate.of(2026, 11, 10), event.getStartDate());
    }

    @Test @DisplayName("Rejects registration after the event begins")
    void rejectsLateRegistration() {
        Event event = event();
        Participant participant = new Participant("alice@example.com", "Alice Example", "Example Labs");
        assertThrows(IllegalArgumentException.class, () -> new EventRegistration(event, participant,
                TicketType.VIP, LocalDate.of(2026, 11, 11)));
    }
}
