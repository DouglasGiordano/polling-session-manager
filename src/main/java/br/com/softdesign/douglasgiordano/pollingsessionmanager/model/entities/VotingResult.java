package br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities;

import lombok.Data;

/**
 * @author Douglas Montanha Giordano
 * Result polling
 */
@Data
public class VotingResult {
    private int numYes;
    private int numNo;
    private EnumVote result;
}
