package br.com.softdesign.douglasgiordano.pollingsessionmanager.service;

import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.requestTO.AgendaInsertTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.responseTO.AgendaResponseTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.responseTO.VotingStatusResponseTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.Agenda;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.VotingAgenda;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.VotingStatus;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.persistence.AgendaReactiveRepository;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.logging.Level;

@Service
@Log
public class AgendaService {
    @Autowired
    private AgendaReactiveRepository repository;


    /**
     * Save Agenda
     * @param agenda
     * @return Agenda With Id
     */
    public Agenda createAgenda(Agenda agenda){
        log.info("Creating new agenda..");
        return repository.save(agenda).block();
    }

    /**
     * Open Agenda Voting
     * @param id agenda
     * @return Agenda With Id
     */
    public Agenda openVotingAgenda(String idAgenda){
        log.info("Opening agenda poll..");
        Agenda agenda = findAgendaById(idAgenda);
        VotingAgenda votingAgenda = agenda.getVoting();
        if(votingAgenda == null){
            votingAgenda = new VotingAgenda();
            votingAgenda.setStatus(VotingStatus.OPEN);
        }
        agenda.setVoting(votingAgenda);
        agenda = repository.save(agenda).block();
        this.asyncMethodTimeVoting(agenda);
        return agenda;
    }


    /**
     * Find Agenda by Id
     * @param id
     * @return Agenda
     */
    public Agenda findAgendaById(String id){
        log.info("Searching agenda by id..");
        return repository.findById(id).block();
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

    @Async
    public void asyncMethodTimeVoting(Agenda agenda) {
        int time = agenda.getVoting().getTimeSeconds();
        log.info("Execute method asynchronously..");
        log.info("Session "+agenda.getId()+ " open for " + time + " seconds.");
        try {
            Thread.sleep(agenda.getVoting().getTimeSeconds()*1000);
        } catch (InterruptedException e) {
            log.log(Level.SEVERE, "Error thread wait voting.." +e.getMessage());
        }
        agenda.getVoting().setStatus(VotingStatus.CLOSED);
        this.repository.save(agenda);
    }
}
