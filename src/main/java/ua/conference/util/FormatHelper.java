package ua.conference.util;

/** Package-scoped string normalization shared by conference utilities. */
final class FormatHelper {
    private FormatHelper() { }

    static String trim(String value) {
        return value.strip();
    }

    static String lowercase(String value) {
        return value.toLowerCase(java.util.Locale.ROOT);
    }

    static String uppercase(String value) {
        return value.toUpperCase(java.util.Locale.ROOT);
    }
}
