package br.com.softdesign.douglasgiordano.pollingsessionmanager.service;

import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.to.request.StatusAssociateTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.exception.EntityNotFoundException;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.exception.UnableVoteException;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.exception.VotingClosedException;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.exception.VotingOpenException;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.*;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.persistence.AgendaReactiveRepository;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

@Service
@Log
@EnableAsync
public class AgendaService {
    @Autowired
    private AgendaReactiveRepository repository;

    @Autowired
    private AsyncTimeVoting asyncTimeVoting;

    /**
     * Save Agenda
     * @param agenda
     * @return Agenda With Id
     */
    public Agenda saveAgenda(Agenda agenda){
        log.info("Creating new agenda..");
        return repository.save(agenda).block();
    }

    /**
     * Open Agenda Voting
     * @param idAgenda
     * @return Agenda With Id
     */
    public Agenda openVotingAgenda(String idAgenda) throws EntityNotFoundException, VotingOpenException, VotingClosedException {
        log.info("Opening agenda poll..");
        Agenda agenda = findAgendaById(idAgenda);
        VotingAgenda votingAgenda = this.getAgendaStatusSessionVoting(agenda);
        agenda.setVoting(votingAgenda);
        agenda = repository.save(agenda).block();
        this.asyncTimeVoting.asyncMethodTimeVoting(idAgenda);
        return agenda;
    }

    /**
     * Vote
     * @param idAgenda
     * @return Agenda With Id
     */
    public Agenda vote(String idAgenda, Vote vote) throws EntityNotFoundException, VotingOpenException, VotingClosedException, UnableVoteException {
        Agenda agenda = findAgendaById(idAgenda);
        VotingAgenda votingAgenda = this.getAgendaStatusSessionVote(agenda);
        if(this.checkEnableToVote(vote.getAssociate())){
            log.info("Voting agenda '"+agenda.getDescription()+"'..");
            if(agenda.getVoting().getVotes() == null){
                agenda.getVoting().setVotes(new ArrayList<Vote>());
            }
            if(!agenda.getVoting().getVotes().contains(vote)){
                agenda.getVoting().getVotes().add(vote);
                agenda = repository.save(agenda).block();
            } else {
                throw new UnableVoteException("Associate has already voted.");
            }
        }
        return agenda;
    }

    /**
     * Get agenda e check status voting
     * @param agenda
     * @return voting agenda
     * @throws VotingOpenException
     * @throws VotingClosedException
     */
    private VotingAgenda getAgendaStatusSessionVoting(Agenda agenda) throws VotingOpenException, VotingClosedException {
        VotingAgenda votingAgenda = agenda.getVoting();
        if(votingAgenda == null) {
            votingAgenda = new VotingAgenda();
            votingAgenda.setStatus(EnumVotingStatus.OPEN);
        } else if(votingAgenda.getStatus() == EnumVotingStatus.OPEN){
            throw new VotingOpenException("The voting session is now open.");
        } else if(votingAgenda.getStatus() == EnumVotingStatus.CLOSED){
            throw new VotingClosedException("The voting session is closed.");
        }

        return votingAgenda;
    }

    /**
     * Get agenda e check status voting to vote
     * @param agenda
     * @return voting agenda
     * @throws VotingOpenException
     * @throws VotingClosedException
     */
    private VotingAgenda getAgendaStatusSessionVote(Agenda agenda) throws VotingOpenException, VotingClosedException {
        VotingAgenda votingAgenda = agenda.getVoting();
        if(votingAgenda == null) {
            votingAgenda = new VotingAgenda();
            votingAgenda.setStatus(EnumVotingStatus.OPEN);
        } if(votingAgenda.getStatus() == EnumVotingStatus.CLOSED){
            throw new VotingClosedException("The voting session is closed.");
        }

        return votingAgenda;
    }

    /**
     * Check status to associate vote
     * @param associate
     * @return voting agenda
     * @throws VotingOpenException
     * @throws VotingClosedException
     */
    private boolean checkEnableToVote(Associate associate) throws UnableVoteException {
        String cpf = associate.getCpf();
        String url = "https://user-info.herokuapp.com/users/{cpf}";
        url = url.replace("{cpf}", cpf);
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<StatusAssociateTO> response
                = restTemplate.getForEntity(url, StatusAssociateTO.class);
        if(response.getStatusCode() != HttpStatus.OK){
            throw new UnableVoteException("An error occurred while consulting the CPF.");
        }
        EnumStatusAssociate status = response.getBody().getStatus();
        associate.setStatus(status);
        if(status == EnumStatusAssociate.UNABLE_TO_VOTE){
            throw new UnableVoteException("Associate not eligible to vote.");
        }
        return true;
    }


    /**
     * Find Agenda by Id
     * @param id
     * @return Agenda
     */
    public Agenda findAgendaById(String id) throws EntityNotFoundException {
        Agenda agenda = repository.findById(id).block();
        if(agenda == null){
            throw new EntityNotFoundException("No records found.");
        }
        return agenda;
    }
}
