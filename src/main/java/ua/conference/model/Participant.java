package ua.conference.model;

import ua.common.BaseEntity;
import ua.conference.util.ConferenceUtils;

import java.util.Objects;

public class Participant extends BaseEntity {
    private final String email;
    private final String fullName;
    private final String organization;

    private Participant(String email, String fullName, String organization) {
        super();
        this.email = ConferenceUtils.normalizeEmail(email);
        this.fullName = ConferenceUtils.requireNotBlank(fullName, "Full name");
        this.organization = ConferenceUtils.requireNotBlank(organization, "Organization");
    }

    public static Participant of(String email, String fullName, String organization) {
        return new Participant(email, fullName, organization);
    }

    public String getEmail() { return email; }
    public String getFullName() { return fullName; }
    public String getOrganization() { return organization; }

    @Override public boolean equals(Object other) {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Participant that = (Participant) other;
        return email.equals(that.email);
    }

    @Override public int hashCode() { return Objects.hash(email); }

    @Override public String toString() {
        return "Participant{email='" + email + "', fullName='" + fullName + "', organization='"
                + organization + "', createdAt=" + createdAt + '}';
    }
}
