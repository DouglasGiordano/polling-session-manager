package br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.to.response;

import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.VotingResult;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author Douglas Giordano
 * Transfer Object to Voting Result.
 */
@Data
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class VotingResultTO {
    @NonNull
    private String idAgenda;
    @NonNull
    private String result;

    public VotingResultTO(){

    }
}
