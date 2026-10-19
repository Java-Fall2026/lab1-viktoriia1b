# Task Context & Prompt for Refactoring Java Lab 1 to Lab 2 (Variant 9: Event & Conference Management)

## 1. Task Context & Environment
- **Branch Context:** You are currently checked out on the `lab2` branch in an existing Java project built in Lab 1.
- **Goal:** Refactor and update the codebase to satisfy all Lab 2 requirements (Records, Enums, and Switch Expressions) based on **Variant 9: Event & Conference Management**.
- **Namespace:** `ua.conference`

---

## 2. Refactoring Rules & Requirements

### 1. Enums (`ua.conference.model`)
- Replace String-based fixed choice fields (`category`, `ticketType`, etc.) with `enum` types.
- Ensure all enum constants match the domain specification:
  - **`Category`**: `TECH`, `SCIENCE`, `BUSINESS`, `ART`
  - **`TicketType`**: `STANDARD`, `VIP`, `SPEAKER`, `PRESS`
- **At least one enum MUST have custom fields and a constructor.**
  - Create a custom property on `TicketType` (e.g., `private final double discountFactor` or `priceMultiplier`) with a constructor and getter method.

### 2. Records & Class Hierarchy Changes
- **Remove `BaseEntity`:** Since Java `record`s cannot extend classes, remove inheritance from `BaseEntity`. Manage `createdAt` or standard identifiers within the records directly if needed.
- **Convert Immutable Entities to `record`:**
  - Entities whose fields do NOT change after instantiation (no `✎` mark) MUST be converted to `record`.
  - Place validation checks inside **compact constructors** (e.g., `public Participant { ValidationHelper.requireNonEmpty(fullName, "fullName"); ... }`).
- **Keep Mutable Classes as Standard Classes:**
  - Entities with fields marked as editable (`✎`) must remain standard `class`es (e.g., `Event` or `EventRegistration` depending on mutable status).
- **Clean up Records:**
  - Explicitly **remove** all boilerplate getters, `equals()`, `hashCode()`, and `toString()` methods from records—let the Java compiler generate them automatically.

### 3. Switch Expressions (`ua.conference.util.ConferenceUtils`)
Implement **two** modern Java 14+ `switch` expressions (using arrow `->` syntax, returning values):
1. **Exhaustive Switch over Enum:** A `switch` expression operating on your custom Enum (`Category` or `TicketType`) without a `default` clause, listing all enum constants explicitly.
2. **Behavioral Switch:** A `switch` expression determining business rules or domain choices (e.g., pricing multipliers, user status messages, or permission logic).

### 4. Language & Documentation Standards
- **English Only:** All code comments, Javadoc, field names, and exception messages **must** be written in English.
- **Ukrainian for UI/Console Output:** User-facing strings in `Main.java` (system outputs) must be in Ukrainian.

### 5. Application Entry Point (`Main.java`)
Update `Main.java` to demonstrate all newly refactored features:
1. Instantiation of `record` objects and access to their components.
2. `enum` functionality: `values()`, access to custom enum fields/methods, and `valueOf()` handling invalid string input in a `try-catch` block.
3. Execution and printed results of **both** new `switch` expressions.
4. Attempting to create a `record` with invalid data to trigger `IllegalArgumentException` from its compact constructor.
5. Equality demonstration: comparing two identical `record` instances using generated `equals()` and `hashCode()`.
6. Record output demonstrating generated `toString()`.

---

## 3. Variant 9 Entity Profiles & Domain Data

### `Participant` (Record candidate)
- `email` (String, key, lowercased, non-empty)
- `fullName` (String, non-empty)
- `organization` (String, non-empty)

### `Venue` (Record candidate)
- `venueCode` (String, key, non-empty, e.g., "HALL-A")
- `name` (String, non-empty)
- `capacity` (int, strictly > 0)

### `Event`
- `eventCode` (String, key, non-empty)
- `title` (String, non-empty) ✎
- `venue` (Venue, non-null)
- `category` (Category Enum: `TECH`, `SCIENCE`, `BUSINESS`, `ART`) ✎
- `startDate` (LocalDate, non-null) ✎
- `endDate` (LocalDate, non-null, >= startDate) ✎

### `EventRegistration`
- `event` (Event, non-null)
- `participant` (Participant, non-null)
- `ticketType` (TicketType Enum: `STANDARD`, `VIP`, `SPEAKER`, `PRESS`)
- `registrationDate` (LocalDate, non-null, <= event.getStartDate())

### Domain Utility Methods (`ConferenceUtils`)
- `eventDays(Event event)`: Returns the total duration of the event in days.
- `daysBeforeEvent(EventRegistration reg)`: Calculates days between registration and event start date.

---

## 4. Verification Step
Make sure all existing and updated code compiles cleanly without build errors:
- Execute `./gradlew build`
- Execute `./gradlew run`

Please apply all necessary modifications to the existing files on the `lab2` branch.
