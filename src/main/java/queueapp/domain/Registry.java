package queueapp.domain;

import lombok.Data;
import org.springframework.data.annotation.Id;

@Data
public class Registry {
    @Id
    private String registryId;
}
