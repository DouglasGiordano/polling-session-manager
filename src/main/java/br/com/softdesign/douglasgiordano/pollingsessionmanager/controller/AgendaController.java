package br.com.softdesign.douglasgiordano.pollingsessionmanager.controller;

import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.requestTO.AgendaInsertTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.Path;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.Agenda;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Douglas Giordano
 * Services Agenda.
 */
@RestController
@RequestMapping(Path.AGENDA)
public class AgendaController {

    @Autowired
    private AgendaService service;

    /**
     * Create new agenda
     * @param agendaTO
     * @return agenda
     * @throws Exception
     */
    @PostMapping(Path.CREATE_AGENDA)
    public ResponseEntity create(@RequestBody AgendaInsertTO agendaTO) throws Exception {
        Agenda agenda = service.createAgenda(this.service.getAgenda(agendaTO));
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.service.getAgendaResponseTO(agenda));
    }
}
