package br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.to.request;

import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.EnumStatusAssociate;
import lombok.Data;

/**
 * @author Douglas Giordano
 * Transfer Object to Associate.
 */
@Data
public class StatusAssociateTO {
    private EnumStatusAssociate status;
}
