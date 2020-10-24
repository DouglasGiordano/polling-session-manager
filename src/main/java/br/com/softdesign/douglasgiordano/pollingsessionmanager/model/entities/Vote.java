package br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities;

import lombok.Data;

@Data
public class Vote {
    private Associate associate;
    private boolean vote;
}
