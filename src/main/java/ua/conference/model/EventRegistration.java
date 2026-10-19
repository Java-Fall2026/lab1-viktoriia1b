package ua.conference.model;

import ua.conference.util.ConferenceUtils;
import java.time.LocalDate;

public record EventRegistration(Event event, Participant participant, TicketType ticketType,
                                LocalDate registrationDate) {
    public EventRegistration {
        event = ConferenceUtils.requireNotNull(event, "Event");
        participant = ConferenceUtils.requireNotNull(participant, "Participant");
        ticketType = ConferenceUtils.requireNotNull(ticketType, "Ticket type");
        registrationDate = ConferenceUtils.requireNotNull(registrationDate, "Registration date");
        if (registrationDate.isAfter(event.getStartDate())) {
            throw new IllegalArgumentException("Registration date must be on or before event start date");
        }
    }
}
