package br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author Douglas Montanha Giordano
 * Agenda for vote (Pauta)
 */
@Data
@Document
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class Agenda {
    @Id
    private String id;

    @NonNull
    private String description;

    private VotingAgenda voting;

    public Agenda() {

    }
}
