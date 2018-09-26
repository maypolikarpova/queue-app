package queueapp.domain;

import lombok.Data;

import java.util.List;

@Data
public class Provider {

    private String id;
    private String name;
    private String occupation; //LIST?
    private String adress;
    private String phoneNumber;
    private String email;
    private List<String> createdQueues;


}
