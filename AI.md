# Codex Prompt --- Java Lab 1, Variant 9: Event & Conference Management

You are working in the existing Java repository for **Laboratory Work
№1: Java Basics and Access Modifiers**.

## Source requirements

Read and follow both documents before changing code:

1.  Lab instructions:
    https://github.com/Java-Univer-2025/Java-Univer-2025.github.io/blob/main/lab-01/README.md
2.  Domain profiles:
    https://github.com/Java-Univer-2025/Java-Univer-2025.github.io/blob/main/lab-01/tasks_2025_domains.md#варіант-9-управління-конференціями

Implement **Variant 9 --- Event & Conference Management (Управління
конференціями)**. Treat the repository and these instructions as the
source of truth. First inspect the existing project structure and
`ua.common.BaseEntity`; do not replace or modify `BaseEntity`.

## Variant 9 domain model

Create exactly these five domain entities, all as ordinary Java classes
(do not use `record`). Every entity must extend `ua.common.BaseEntity`,
and each constructor must begin with `super()`.

### 1. `Participant`

-   `String email` --- identity key; must not be null or blank, trim
    whitespace, and normalize to lowercase.
-   `String fullName` --- must not be null or blank.
-   `String organization` --- must not be null or blank.
-   Include public getters and no setters.

### 2. `Venue`

-   `String venueCode` --- identity key; must not be null or blank
    (example: `"HALL-A"`).
-   `String name` --- must not be null or blank.
-   `int capacity` --- must be strictly greater than zero.
-   Include public getters and no setters.

### 3. `Event`

-   `String eventCode` --- identity key; must not be null or blank.
-   `String title` --- must not be null or blank.
-   `Venue venue` --- must not be null.
-   `String category` --- must be normalized to uppercase and must be
    one of `TECH`, `SCIENCE`, `BUSINESS`, `ART`.
-   `LocalDate startDate` --- must not be null.
-   `LocalDate endDate` --- must not be null and must be on or after
    `startDate`.
-   Include public getters and no setters.

### 4. `EventRegistration` --- entity without a predefined identity key

-   `Event event` --- must not be null.
-   `Participant participant` --- must not be null.
-   `String ticketType` --- normalize to uppercase and allow only
    `STANDARD`, `VIP`, `SPEAKER`, `PRESS`.
-   `LocalDate registrationDate` --- must not be null and must be on or
    before `event.getStartDate()`.
-   Include public getters and no setters.
-   Choose a meaningful set of fields for `equals()` and `hashCode()`
    because this entity has no predefined key. Use the same fields in
    both methods and explain the choice in a brief comment or project
    documentation.

## Required project structure and access rules

-   Use the repository's existing conventions and package layout. Create
    `ua.<domain>.model` and `ua.<domain>.util`, choosing a domain
    package name appropriate for conference/event management (for
    example, `ua.conference`).
-   Reuse the existing `ua.common.BaseEntity`; do not edit it.
-   Every entity field must be `private`; getters must be `public`.
-   There are no mutable (`✎`) fields in Variant 9, so do not add
    setters to these five entities.
-   Implement `equals()`, `hashCode()`, and `toString()` in every
    entity.
-   `equals()` must compare runtime types using `getClass()`, not
    `instanceof`.
-   For identity-key entities, use only the marked key field in
    `equals()` and `hashCode()`:
    -   `Participant`: `email`
    -   `Venue`: `venueCode`
    -   `Event`: `eventCode`
-   For `EventRegistration`, use your documented, consistent field
    selection.
-   Do not include inherited `createdAt` in `equals()` or `hashCode()`.
-   Each `toString()` must include the class name, all declared fields,
    and inherited `createdAt`.
-   Use `LocalDate` for all dates.

## Utility classes

In the `util` package, implement these three non-instantiable utility
classes. Each must have a private constructor:

1.  `ValidationHelper` --- **package-private**. Provide reusable
    validation methods such as `requireNotNull`, `requireNotBlank`, and
    positive-integer validation. Throw `IllegalArgumentException` with a
    clear English message stating what was expected and, where
    practical, the value received.
2.  `FormatHelper` --- **package-private**. Provide string normalization
    helpers, including trimming and uppercase/lowercase normalization as
    appropriate.
3.  A public domain utility class, such as `ConferenceUtils` ---
    **public**. It is the only utility entry point available to classes
    in the `model` package. It should delegate to `ValidationHelper` and
    `FormatHelper` internally.

Because `ValidationHelper` and `FormatHelper` are package-private and
are in a different package from the model classes, model classes must
not access them directly. Expose the required validation and
normalization operations through `ConferenceUtils`.

Implement the two computed methods in `ConferenceUtils`; do not store
their results in entity fields:

-   `eventDays(Event)` --- calculate the number of days an event lasts.
    Since `endDate` may equal `startDate`, define the result
    consistently with the domain meaning of duration and document
    whether the count is inclusive. Prefer an inclusive calendar-day
    count (`ChronoUnit.DAYS.between(startDate, endDate) + 1`) so a
    one-day event returns `1`.
-   `daysBeforeEvent(EventRegistration)` --- calculate the number of
    calendar days between `registrationDate` and the event's `startDate`
    using
    `ChronoUnit.DAYS.between(registrationDate, event.getStartDate())`. A
    registration on the event's start date returns `0`.

Validate arguments passed to computed methods and avoid null-related
failures.

## Validation and exception messages

-   Validate all domain rules in constructors.
-   Use `IllegalArgumentException` for invalid input.
-   Normalize strings before storing them.
-   Category and ticket type validation errors must list the allowed
    values.
-   Keep comments, Javadocs, and exception messages in **English**.
    Ukrainian is allowed for user-facing output in `Main`.

## Factory methods

Following the lab README, choose two appropriate entity classes to use
static factory methods:

-   Make each selected class's constructor `private`.
-   Add a `public static of(...)` factory method that creates and
    returns the entity.
-   Keep public constructors for the other three entity classes.
-   Ensure `Main` demonstrates both normal constructor-based creation
    and factory-based creation.

## `Main` demonstration

Create or update `Main` to run from the console without requiring
interactive keyboard input. Demonstrate all of the following:

1.  Create objects both through public constructors and through the two
    static `of(...)` factory methods.
2.  Show normalization in action, including an email such as
    `"  ALICE@EXAMPLE.COM "` becoming `"alice@example.com"` and a
    category such as `" tech "` becoming `"TECH"`.
3.  Trigger at least three distinct validation errors, catch each with
    `catch (IllegalArgumentException e)`, and print `e.getMessage()`.
4.  Attempt to create an invalid object or otherwise demonstrate that
    invalid data cannot enter the model.
5.  Compare two separate objects with identical identity data using
    `==`, `equals()`, and `hashCode()`, and compare objects with
    different identity keys to show that `equals()` returns `false`.
6.  Call and display both computed methods: `eventDays(Event)` and
    `daysBeforeEvent(EventRegistration)`.
7.  Print all five entity types using their `toString()` methods.
8.  Include one commented-out line that intentionally would not compile
    because it accesses `ValidationHelper` outside its package or calls
    a private constructor; explain in an English comment why it is
    inaccessible.

Use dates that make the demonstration deterministic and valid relative
to the domain rules. Ensure each registration date is on or before its
event start date.

## Quality requirements

-   Follow the repository's Java version, naming conventions, and build
    system. Do not add dependencies unless necessary.
-   Inspect existing files before editing; preserve unrelated work and
    avoid overwriting files unnecessarily.
-   Keep the solution beginner-friendly and readable, without
    unnecessary frameworks or abstractions.
-   Ensure all five classes, utility classes, and `Main` compile
    together.
-   Run the project's available build/tests (or compile command if no
    build tool is configured). Fix all errors caused by your changes.
-   Do not claim that tests passed unless you actually ran them.
-   At the end, summarize the files created/changed, explain key
    implementation choices (especially
    `EventRegistration.equals/hashCode` and the inclusive `eventDays`
    calculation), and provide the exact command used to build/run the
    project.
-   Do not stop after giving advice: make the changes directly in the
    repository.
