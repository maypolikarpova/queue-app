package queueapp.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Request {
    @Id
    private String requestId;
}
