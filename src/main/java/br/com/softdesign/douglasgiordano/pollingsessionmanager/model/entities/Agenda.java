package br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class Agenda {
    @Id
    private String id;
    @NonNull
    private String description;
    private List<Associate> associates;
    private VotingAgenda voting;

    public Agenda(){

    }
}
