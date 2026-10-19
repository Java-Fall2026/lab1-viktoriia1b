package ua.conference.util;

import ua.conference.model.Category;
import ua.conference.model.Event;
import ua.conference.model.EventRegistration;
import ua.conference.model.TicketType;
import java.time.temporal.ChronoUnit;

/** Validation and domain calculations for conference entities. */
public final class ConferenceUtils {
    private ConferenceUtils() { }

    public static String requireNotBlank(String value, String fieldName) {
        return FormatHelper.trim(ValidationHelper.requireNotBlank(value, fieldName));
    }

    public static String normalizeEmail(String value) {
        return FormatHelper.lowercase(requireNotBlank(value, "Email"));
    }

    public static <T> T requireNotNull(T value, String fieldName) {
        return ValidationHelper.requireNotNull(value, fieldName);
    }

    public static int requirePositive(int value, String fieldName) {
        return ValidationHelper.requirePositive(value, fieldName);
    }

    public static String categoryDescription(Category category) {
        return switch (requireNotNull(category, "Category")) {
            case TECH -> "Технологічна конференція";
            case SCIENCE -> "Наукова конференція";
            case BUSINESS -> "Бізнес-конференція";
            case ART -> "Мистецька подія";
        };
    }

    public static String ticketMessage(TicketType ticketType) {
        return switch (requireNotNull(ticketType, "Ticket type")) {
            case STANDARD -> "Стандартний квиток";
            case VIP -> "VIP-квиток";
            case SPEAKER -> "Квиток доповідача";
            case PRESS -> "Акредитація преси";
        };
    }

    /** Returns the inclusive calendar-day count, so a one-day event lasts one day. */
    public static long eventDays(Event event) {
        Event checked = requireNotNull(event, "Event");
        return ChronoUnit.DAYS.between(checked.getStartDate(), checked.getEndDate()) + 1;
    }

    /** Returns calendar days between registration and the event start date. */
    public static long daysBeforeEvent(EventRegistration registration) {
        EventRegistration checked = requireNotNull(registration, "Registration");
        return ChronoUnit.DAYS.between(checked.registrationDate(), checked.event().getStartDate());
    }
}
