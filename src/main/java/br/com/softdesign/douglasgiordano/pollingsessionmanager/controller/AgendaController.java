package br.com.softdesign.douglasgiordano.pollingsessionmanager.controller;

import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.to.request.AgendaInsertTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.to.request.VoteTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.exception.EntityNotFoundException;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.exception.UnableVoteException;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.exception.VotingClosedException;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.exception.VotingOpenException;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.Path;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.Agenda;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.Vote;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.service.AgendaService;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.service.ToService;
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

    @Autowired
    private ToService toService;

    /**
     * Create new agenda
     * @param agendaTO
     * @return agenda
     * @throws Exception
     */
    @PostMapping()
    public ResponseEntity create(@RequestBody AgendaInsertTO agendaTO) {
        Agenda agenda = service.saveAgenda(this.toService.getAgenda(agendaTO));
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.toService.getAgendaResponseTO(agenda));
    }

    /**
     * Open votting agenda
     * @param idAgenda
     * @return Voting Status
     * @throws Exception
     */
    @PostMapping(Path.OPEN_VOTING_AGENDA)
    public ResponseEntity openVoting(@PathVariable("idAgenda") String idAgenda) throws EntityNotFoundException, VotingClosedException, VotingOpenException {
        Agenda agenda = service.openVotingAgenda(idAgenda);
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.toService.getVotingStatusTO(agenda));
    }

    /**
     * Associate voting
     * @param idAgenda
     * @return Vote
     * @throws Exception
     */
    @PostMapping(Path.VOTING_AGENDA)
    public ResponseEntity vote(@PathVariable("idAgenda") String idAgenda,
                               @RequestBody VoteTO voteTO) throws UnableVoteException, VotingClosedException, VotingOpenException, EntityNotFoundException {
        Vote vote = this.toService.getVote(voteTO);
        Agenda agenda = service.vote(idAgenda, vote);
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.toService.getVoteResponse(vote));
    }
}
