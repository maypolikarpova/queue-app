package queueapp.component;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.DateTimeParseException;

import static queueapp.web.util.ApiDateConstants.DATE_TIME_PATTERN;
import static queueapp.web.util.ApiDateConstants.DATE_TIME_PATTERN_LOCAL;
import static queueapp.web.util.ApiDateConstants.DATE_TIME_PATTERN_OFFSET;

@Slf4j
public class DateTimeDeserializer extends StdDeserializer<LocalDateTime> {
    private final DateTimeFormatter formatter;

    {
        formatter = new DateTimeFormatterBuilder()
                            .appendOptional(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN_OFFSET))
                            .appendOptional(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN_LOCAL))
                            .appendOptional(DateTimeFormatter.ofPattern(DATE_TIME_PATTERN))
                            .toFormatter();
    }

    public DateTimeDeserializer() {
        super(LocalDateTime.class);
    }

    @Override
    public LocalDateTime deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) {
        try {
            String date = jsonParser.getText();
            return LocalDateTime.parse(date, formatter);
        } catch (IOException | DateTimeParseException e) {
            log.error("Unsupported dateTime format!");
        }
        return null;
    }

}