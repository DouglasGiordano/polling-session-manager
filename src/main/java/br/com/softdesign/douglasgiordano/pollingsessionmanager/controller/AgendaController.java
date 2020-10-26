package br.com.softdesign.douglasgiordano.pollingsessionmanager.controller;

import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.requestTO.AgendaInsertTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.Path;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.Agenda;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.service.AgendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity create(@RequestBody AgendaInsertTO agendaTO) throws Exception {
        Agenda agenda = service.createAgenda(this.service.getAgenda(agendaTO));
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.service.getAgendaResponseTO(agenda));
    }

    /**
     *
     * @param idAgenda
     * @return Voting Status
     * @throws Exception
     */
    @PostMapping(Path.OPEN_VOTING_AGENDA)
    public ResponseEntity openVoting(@PathVariable("idAgenda") String idAgenda) throws Exception {
        Agenda agenda = service.openVotingAgenda(idAgenda);
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.service.getVotingStatusTO(agenda));
    }
}
