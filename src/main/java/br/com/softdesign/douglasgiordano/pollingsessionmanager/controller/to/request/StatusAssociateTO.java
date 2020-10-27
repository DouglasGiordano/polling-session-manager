package br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.to.request;

import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.EnumStatusAssociate;
import lombok.Data;

@Data
public class StatusAssociateTO {
    private EnumStatusAssociate status;
}
