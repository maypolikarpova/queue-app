package queueapp.domain.queue;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Range {
    private LocalDateTime dateTimeFrom;
    private LocalDateTime dateTimeTo;
    private int period;
}
