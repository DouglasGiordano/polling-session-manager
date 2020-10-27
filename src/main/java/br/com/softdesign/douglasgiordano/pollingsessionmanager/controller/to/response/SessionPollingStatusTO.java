package br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.to.response;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author Douglas Giordano
 * Transfer Object to Status Voting.
 */
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@Data
public class SessionPollingStatusTO {
    @NonNull
    private String description;
    @NonNull
    private String status;

    public SessionPollingStatusTO() {

    }
}
