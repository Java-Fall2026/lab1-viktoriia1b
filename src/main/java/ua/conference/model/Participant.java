package ua.conference.model;

import ua.conference.util.ConferenceUtils;

public record Participant(String email, String fullName, String organization) {
    public Participant {
        email = ConferenceUtils.normalizeEmail(email);
        fullName = ConferenceUtils.requireNotBlank(fullName, "Full name");
        organization = ConferenceUtils.requireNotBlank(organization, "Organization");
    }
}
