package br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

/**
 * @author Douglas Montanha Giordano
 * Voting Agenda Associate (Associado)
 */
@Data
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class Associate {
    @NonNull
    private String cpf;
    private EnumStatusAssociate status;

    public Associate() {

    }
}