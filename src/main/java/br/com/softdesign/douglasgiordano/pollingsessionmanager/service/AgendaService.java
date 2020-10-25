package br.com.softdesign.douglasgiordano.pollingsessionmanager.service;

import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.requestTO.AgendaInsertTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.responseTO.AgendaResponseTo;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.Agenda;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.persistence.AgendaReactiveRepository;
import lombok.extern.java.Log;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public AgendaResponseTo getAgendaResponseTO(Agenda agenda){
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(agenda, AgendaResponseTo.class);
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
