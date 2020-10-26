package br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.responseTO;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Data;
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
