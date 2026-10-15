package ua.conference.util;

import ua.conference.model.Event;
import ua.conference.model.EventRegistration;

import java.time.temporal.ChronoUnit;

/** Public validation, normalization, and computed-method entry point for conference models. */
public final class ConferenceUtils {
    private static final String[] CATEGORIES = {"TECH", "SCIENCE", "BUSINESS", "ART"};
    private static final String[] TICKET_TYPES = {"STANDARD", "VIP", "SPEAKER", "PRESS"};

    private ConferenceUtils() { }

    public static String requireNotBlank(String value, String fieldName) {
        return FormatHelper.trim(ValidationHelper.requireNotBlank(value, fieldName));
    }

    public static String normalizeEmail(String value) {
        return FormatHelper.lowercase(requireNotBlank(value, "Email"));
    }

    public static String normalizeCategory(String value) {
        return normalizeAllowed(value, "Category", CATEGORIES);
    }

    public static String normalizeTicketType(String value) {
        return normalizeAllowed(value, "Ticket type", TICKET_TYPES);
    }

    private static String normalizeAllowed(String value, String fieldName, String[] allowed) {
        String normalized = FormatHelper.uppercase(requireNotBlank(value, fieldName));
        return ValidationHelper.requireAllowed(normalized, fieldName, allowed);
    }

    public static <T> T requireNotNull(T value, String fieldName) {
        return ValidationHelper.requireNotNull(value, fieldName);
    }

    public static int requirePositive(int value, String fieldName) {
        return ValidationHelper.requirePositive(value, fieldName);
    }

    /** Returns the inclusive calendar-day count, so a one-day event lasts one day. */
    public static long eventDays(Event event) {
        Event checked = ValidationHelper.requireNotNull(event, "Event");
        return ChronoUnit.DAYS.between(checked.getStartDate(), checked.getEndDate()) + 1;
    }

    /** Returns calendar days from registration through the event start; same-day registration is zero. */
    public static long daysBeforeEvent(EventRegistration registration) {
        EventRegistration checked = ValidationHelper.requireNotNull(registration, "Registration");
        return ChronoUnit.DAYS.between(checked.getRegistrationDate(), checked.getEvent().getStartDate());
    }
}
