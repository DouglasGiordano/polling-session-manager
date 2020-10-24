package br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Data
@Document
public class Agenda {
    @Id
    private Integer id;
    private String description;
    private List<Associate> associates;
    private VotingAgenda voting;
}
