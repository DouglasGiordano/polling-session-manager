package br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.to.request;

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
public class VoteTO {
    @NonNull
    private String cpf;
    @NonNull
    private EnumVote vote;

    public VoteTO(){

    }
}
