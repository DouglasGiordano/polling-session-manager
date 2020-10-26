package br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.to.response;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Data;

/**
 * @author Douglas Giordano
 * Transfer Object to Status Voting.
 */
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Data
public class VotingStatusResponseTO {
    @NonNull
    private String description;
    @NonNull
    private String status;

    public VotingStatusResponseTO(){

    }
}
