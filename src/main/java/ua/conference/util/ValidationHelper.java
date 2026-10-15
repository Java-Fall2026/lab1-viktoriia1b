package ua.conference.util;

/** Package-scoped validation shared by conference utilities. */
final class ValidationHelper {
    private ValidationHelper() { }

    static <T> T requireNotNull(T value, String fieldName) {
        if (value == null) {
            throw new IllegalArgumentException(fieldName + " must not be null");
        }
        return value;
    }

    static String requireNotBlank(String value, String fieldName) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException(fieldName + " must not be null or blank, got: " + value);
        }
        return value;
    }

    static int requirePositive(int value, String fieldName) {
        if (value <= 0) {
            throw new IllegalArgumentException(fieldName + " must be greater than 0, got: " + value);
        }
        return value;
    }

    static String requireAllowed(String value, String fieldName, String... allowedValues) {
        for (String allowed : allowedValues) {
            if (allowed.equals(value)) return value;
        }
        throw new IllegalArgumentException(fieldName + " must be one of "
                + String.join(", ", allowedValues) + ", got: " + value);
    }
}
