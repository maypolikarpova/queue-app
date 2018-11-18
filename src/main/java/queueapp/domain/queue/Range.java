package queueapp.domain.queue;

import lombok.Value;

import java.time.LocalDateTime;

@Value
public class Range {
    private LocalDateTime dateTimeFrom;
    private LocalDateTime dateTimeTo;
    private int period;
}
