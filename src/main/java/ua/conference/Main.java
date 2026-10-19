package ua.conference;

import ua.conference.model.Category;
import ua.conference.model.Event;
import ua.conference.model.EventRegistration;
import ua.conference.model.Participant;
import ua.conference.model.TicketType;
import ua.conference.model.Venue;
import ua.conference.util.ConferenceUtils;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        Participant participant = new Participant(" ALICE@EXAMPLE.COM ", "Alice Example", "Example Labs");
        Participant identicalParticipant = new Participant("alice@example.com", "Alice Example", "Example Labs");
        Venue venue = new Venue("HALL-A", "Main Hall", 300);
        Event event = new Event("JAVA-2026", "Java Conference", venue, Category.TECH,
                LocalDate.of(2026, 11, 10), LocalDate.of(2026, 11, 12));
        EventRegistration registration = new EventRegistration(event, participant, TicketType.STANDARD,
                LocalDate.of(2026, 11, 5));

        System.out.println("Електронна пошта учасника: " + participant.email());
        System.out.println("Усі категорії: ");
        for (Category category : Category.values()) System.out.println("  " + category);
        System.out.println("Опис категорії: " + ConferenceUtils.categoryDescription(event.getCategory()));
        System.out.println("Правило квитка: " + ConferenceUtils.ticketMessage(registration.ticketType()));
        System.out.println("Множник ціни VIP-квитка: " + TicketType.VIP.getPriceMultiplier());
        try {
            System.out.println(TicketType.valueOf("GOLD"));
        } catch (IllegalArgumentException exception) {
            System.out.println("Невідомий тип квитка: GOLD");
        }

        try {
            new Participant("", "Invalid", "Example Labs");
        } catch (IllegalArgumentException exception) {
            System.out.println("Перевірка даних: " + exception.getMessage());
        }

        System.out.println("Рівні записи: " + participant.equals(identicalParticipant));
        System.out.println("Однакові хеш-коди: " + (participant.hashCode() == identicalParticipant.hashCode()));
        System.out.println("Учасник: " + participant);
        System.out.println("Тривалість події: " + ConferenceUtils.eventDays(event) + " днів");
        System.out.println("Днів до початку події: " + ConferenceUtils.daysBeforeEvent(registration));
    }
}
