package br.com.softdesign.douglasgiordano.pollingsessionmanager.controller;

import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.requestTO.AgendaInsertTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.Path;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(Path.AGENDA)
public class AgendaController {

    /**
     * Create new agenda
     * @param agendaTO
     * @return agenda
     * @throws Exception
     */
    @PostMapping(Path.CREATE_AGENDA)
    public ResponseEntity create(@RequestBody AgendaInsertTO agendaTO) throws Exception {
        System.out.println(agendaTO.getDescription());
        return new ResponseEntity(HttpStatus.OK);
    }
}
