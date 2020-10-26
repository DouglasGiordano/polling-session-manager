package br.com.softdesign.douglasgiordano.pollingsessionmanager.service;

import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.requestTO.AgendaInsertTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.responseTO.AgendaResponseTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.responseTO.VotingStatusResponseTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.exception.EntityNotFoundException;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.exception.VotingClosedException;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.exception.VotingOpenException;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.Agenda;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.VotingAgenda;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.VotingStatus;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.persistence.AgendaReactiveRepository;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import java.util.logging.Level;

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
        VotingAgenda votingAgenda = this.getStatusVoting(agenda);
        agenda.setVoting(votingAgenda);
        agenda = repository.save(agenda).block();
        this.asyncTimeVoting.asyncMethodTimeVoting(idAgenda);
        return agenda;
    }

    public VotingAgenda getStatusVoting(Agenda agenda) throws VotingOpenException, VotingClosedException {
        VotingAgenda votingAgenda = agenda.getVoting();
        if(votingAgenda == null) {
            votingAgenda = new VotingAgenda();
            votingAgenda.setStatus(VotingStatus.OPEN);
        } else if(votingAgenda.getStatus() == VotingStatus.OPEN){
            throw new VotingOpenException("The voting session is now open.");
        } else if(votingAgenda.getStatus() == VotingStatus.CLOSED){
            throw new VotingClosedException("The voting session is now open.");
        }

        return votingAgenda;
    }


    /**
     * Find Agenda by Id
     * @param id
     * @return Agenda
     */
    public Agenda findAgendaById(String id) throws EntityNotFoundException {
        log.info("Searching agenda by id..");
        Agenda agenda = repository.findById(id).block();
        if(agenda == null){
            throw new EntityNotFoundException("No records found.");
        }
        return agenda;
    }

    /**
     * Map Agenda to AgendaResponseTO
     * @param agenda
     * @return Agenda Response
     */
    public AgendaResponseTO getAgendaResponseTO(Agenda agenda){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(agenda, AgendaResponseTO.class);
    }

    /**
     * Map Agenda to VotingStatus
     * @param agenda
     * @return Voting Status Response
     */
    public VotingStatusResponseTO getVotingStatusTO(Agenda agenda){
        return new VotingStatusResponseTO(agenda.getDescription(), agenda.getVoting().getStatus().name());
    }

    /**
     * Map AgendaInsertTO to Agenda
     * @param agendaTO
     * @return Agenda
     */
    public Agenda getAgenda(AgendaInsertTO agendaTO){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(agendaTO, Agenda.class);
    }


}
