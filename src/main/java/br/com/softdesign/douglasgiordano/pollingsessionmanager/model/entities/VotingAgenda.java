package br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities;

import lombok.Data;

import java.util.List;

/**
 * @author Douglas Montanha Giordano
 * Session Voting
 */
@Data
public class VotingAgenda {
    private List<Vote> votes;
    private int timeSeconds = 60; //60 seconds
    private EnumVotingStatus status;
    private VotingResult result;
}