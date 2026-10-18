package ua.conference;

import ua.conference.model.Event;
import ua.conference.model.EventRegistration;
import ua.conference.model.Participant;
import ua.conference.model.Venue;
import ua.conference.util.ConferenceUtils;

import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Participant alice = Participant.of("  ALICE@EXAMPLE.COM ", "Alice Example", "Example Labs");
        Participant sameAlice = Participant.of("alice@example.com", "Alice E.", "Another description");
        Participant bob = Participant.of("bob@example.com", "Bob Example", "Example Labs");
        Venue venue = new Venue("HALL-A", "Main Hall", 300);
        Event event = Event.of("JAVA-2026", "Java Conference", venue, " tech ",
                LocalDate.of(2026, 11, 10), LocalDate.of(2026, 11, 12));
        EventRegistration registration = new EventRegistration(event, alice, " standard ",
                LocalDate.of(2026, 11, 5));

        System.out.println("Email normalization: " + alice.getEmail());
        System.out.println("Category normalization: " + event.getCategory());

        try {
            new Venue("HALL-B", "Small Hall", 0);
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        try {
            Event.of("BAD-CATEGORY", "Invalid category", venue, "music",
                    LocalDate.of(2026, 11, 10), LocalDate.of(2026, 11, 10));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        try {
            new EventRegistration(event, alice, "GOLD", LocalDate.of(2026, 11, 5));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        try {
            new EventRegistration(event, bob, "VIP", LocalDate.of(2026, 11, 11));
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }

        System.out.println("Same instance (==): " + (alice == sameAlice));
        System.out.println("Same identity (equals): " + alice.equals(sameAlice));
        System.out.println("Same identity hash codes: " + (alice.hashCode() == sameAlice.hashCode()));
        System.out.println("Different identity (equals): " + alice.equals(bob));
        System.out.println("Event duration: " + ConferenceUtils.eventDays(event) + " days");
        System.out.println("Days before event: " + ConferenceUtils.daysBeforeEvent(registration));

        System.out.println(alice);
        System.out.println(venue);
        System.out.println(event);
        System.out.println(registration);
        System.out.println(bob);

        // ValidationHelper is package-private, so code in this package cannot access it.
        // ValidationHelper.requireNotBlank("x", "value");
    }
}
