package br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author Douglas Giordano
 * Vote chosen by an associate
 */
@Data
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class Vote {
    @NonNull
    private Associate associate;
    @NonNull
    private EnumVote vote;

    public Vote() {

    }
}
