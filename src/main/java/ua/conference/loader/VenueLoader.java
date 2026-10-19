package ua.conference.loader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ua.conference.exception.InvalidDataException;
import ua.conference.model.Venue;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/** Loads venues from a header-based CSV file and skips invalid rows. */
public final class VenueLoader {
    private static final Logger LOGGER = LoggerFactory.getLogger(VenueLoader.class);
    private static final String HEADER = "venueCode,name,capacity";
    private VenueLoader() { }

    /** Loads a UTF-8 CSV file. Access failures are logged and return an empty list. */
    public static List<Venue> load(Path path) {
        try (BufferedReader reader = Files.newBufferedReader(path)) {
            return load(reader);
        } catch (IOException | SecurityException exception) {
            LOGGER.error("Unable to read venue file {}", path, exception);
            return List.of();
        } finally {
            LOGGER.debug("Finished venue file access attempt for {}", path);
        }
    }

    /** Loads CSV rows and closes the supplied reader. */
    public static List<Venue> load(Reader source) {
        List<Venue> venues = new ArrayList<>();
        try (BufferedReader reader = source instanceof BufferedReader bufferedReader
                ? bufferedReader : new BufferedReader(source)) {
            String line;
            int lineNumber = 0;
            while ((line = reader.readLine()) != null) {
                lineNumber++;
                if (lineNumber == 1 && HEADER.equals(line.trim())) continue;
                try {
                    venues.add(parseLine(line, lineNumber));
                } catch (InvalidDataException exception) {
                    LOGGER.warn("Skipping invalid CSV line {}: {}", lineNumber, exception.getMessage(), exception);
                }
            }
            LOGGER.info("Loaded {} venues", venues.size());
        } catch (IOException | SecurityException exception) {
            LOGGER.error("Unable to read venue data", exception);
        } finally {
            LOGGER.debug("Completed venue CSV processing");
        }
        return List.copyOf(venues);
    }

    static Venue parseLine(String line, int lineNumber) throws InvalidDataException {
        try {
            String[] fields = line.split(",", -1);
            if (fields.length != 3) throw new IllegalArgumentException("Expected 3 comma-separated fields");
            return new Venue(fields[0], fields[1], Integer.parseInt(fields[2].trim()));
        } catch (IllegalArgumentException exception) {
            throw new InvalidDataException("Line " + lineNumber + ": " + exception.getMessage(), exception);
        }
    }
}
