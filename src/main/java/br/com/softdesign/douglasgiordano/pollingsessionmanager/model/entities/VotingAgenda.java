package br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities;

import lombok.Data;

import java.util.List;

@Data
public class VotingAgenda {
    private List<Vote> votes;
    private int timeSeconds = 60; //60 seconds
    private EnumVotingStatus status;
}