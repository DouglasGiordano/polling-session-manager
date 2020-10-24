package br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities;

import lombok.Data;

import java.util.List;

@Data
public class Agenda {
    private Integer id;
    private List<Associate> associates;
    private VotingAgenda voting;
}
