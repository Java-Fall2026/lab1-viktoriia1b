package ua.conference.loader;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ua.conference.exception.InvalidDataException;
import ua.conference.model.Venue;
import java.io.StringReader;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class VenueLoaderTest {
    @Test @DisplayName("Parses a valid venue record")
    void parsesValidVenue() throws InvalidDataException {
        assertEquals(new Venue("HALL-A", "Main Hall", 300), VenueLoader.parseLine("HALL-A,Main Hall,300", 2));
    }

    @Test @DisplayName("Reports the source line for invalid numeric capacity")
    void reportsLineForInvalidCapacity() {
        InvalidDataException exception = assertThrows(InvalidDataException.class,
                () -> VenueLoader.parseLine("HALL-A,Main Hall,large", 7));
        assertTrue(exception.getMessage().contains("Line 7"));
        assertInstanceOf(NumberFormatException.class, exception.getCause());
    }

    @Test @DisplayName("Skips invalid rows and continues loading valid venues")
    void skipsInvalidRowsAndContinues() {
        List<Venue> venues = VenueLoader.load(new StringReader(
                "venueCode,name,capacity\nHALL-A,Main Hall,300\n,No Code,40\nHALL-B,Small Hall,20\n"));
        assertEquals(2, venues.size());
        assertEquals("HALL-B", venues.get(1).venueCode());
    }

    @Test @DisplayName("Rejects non-positive venue capacity")
    void rejectsNonPositiveCapacity() {
        assertThrows(IllegalArgumentException.class, () -> new Venue("HALL-A", "Main Hall", 0));
    }
}

