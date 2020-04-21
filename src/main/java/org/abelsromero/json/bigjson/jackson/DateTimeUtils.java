package org.abelsromero.json.bigjson.jackson;

import java.time.*;

public class DateTimeUtils {

    private static final ZoneOffset UTC_ZONE_OFFSET = ZoneOffset.UTC;

    public static long toMillisecondsAsUtc(LocalDate localDate) {
        return localDate.atStartOfDay()
                .atOffset(UTC_ZONE_OFFSET)
                .toInstant()
                .toEpochMilli();
    }

    public static long toMillisecondsAsUtc(LocalDateTime localDateTime) {
        return localDateTime.atOffset(UTC_ZONE_OFFSET)
                .toInstant()
                .toEpochMilli();
    }

    public static LocalDate toLocalDate(long value) {
        return getUTCZonedDateTime(value).toLocalDate();
    }

    public static LocalDateTime toLocalDateTime(long value) {
        return getUTCZonedDateTime(value).toLocalDateTime();
    }

    private static ZonedDateTime getUTCZonedDateTime(long value) {
        return Instant.ofEpochMilli(value).atZone(UTC_ZONE_OFFSET);
    }

}
