package ua.conference.model;

import ua.common.BaseEntity;
import ua.conference.util.ConferenceUtils;

import java.time.LocalDate;
import java.util.Objects;

public class EventRegistration extends BaseEntity {
    private final Event event;
    private final Participant participant;
    private final String ticketType;
    private final LocalDate registrationDate;

    public EventRegistration(Event event, Participant participant, String ticketType, LocalDate registrationDate) {
        super();
        this.event = ConferenceUtils.requireNotNull(event, "Event");
        this.participant = ConferenceUtils.requireNotNull(participant, "Participant");
        this.ticketType = ConferenceUtils.normalizeTicketType(ticketType);
        this.registrationDate = ConferenceUtils.requireNotNull(registrationDate, "Registration date");
        if (this.registrationDate.isAfter(this.event.getStartDate())) {
            throw new IllegalArgumentException("Registration date must be on or before event start date, got: "
                    + registrationDate);
        }
    }

    public Event getEvent() { return event; }
    public Participant getParticipant() { return participant; }
    public String getTicketType() { return ticketType; }
    public LocalDate getRegistrationDate() { return registrationDate; }

    // Registrations are equal when the same participant has the same ticket for the same event and date.
    @Override public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        EventRegistration that = (EventRegistration) other;
        return event.equals(that.event) && participant.equals(that.participant)
                && ticketType.equals(that.ticketType) && registrationDate.equals(that.registrationDate);
    }

    @Override public int hashCode() {
        return Objects.hash(event, participant, ticketType, registrationDate);
    }

    @Override public String toString() {
        return "EventRegistration{event=" + event + ", participant=" + participant + ", ticketType='"
                + ticketType + "', registrationDate=" + registrationDate + ", createdAt=" + createdAt + '}';
    }
}
