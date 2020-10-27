package br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.to.response;

import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.EnumStatusAssociate;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.EnumVote;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author Douglas Giordano
 * Transfer Object to Vote.
 */
@Data
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class VoteStatusTO {
    @NonNull
    private String cpf;
    @NonNull
    private EnumVote vote;
    @NonNull
    private EnumStatusAssociate status;

    public VoteStatusTO() {

    }
}