package br.com.softdesign.douglasgiordano.pollingsessionmanager.service;

import br.com.softdesign.douglasgiordano.pollingsessionmanager.exception.EntityNotFoundException;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.Agenda;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.EnumVotingStatus;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.logging.Level;

@Component
@EnableAsync
@Log
public class AsyncTimeVoting {

    @Autowired
    private AgendaService service;

    @Async
    public void asyncMethodTimeVoting(String idAgenda) throws EntityNotFoundException {
        Agenda agenda = service.findAgendaById(idAgenda);
        int time = agenda.getVoting().getTimeSeconds();
        log.info("Execute method asynchronously..");
        log.info("Session "+agenda.getId()+ " open for " + time + " seconds.");
        try {
            Thread.sleep(agenda.getVoting().getTimeSeconds()*1000);
            agenda.getVoting().setStatus(EnumVotingStatus.CLOSED);
            this.service.saveAgenda(agenda);
            log.info("Session "+agenda.getId()+ " closed.");
        } catch (InterruptedException e) {
            log.log(Level.SEVERE, "Error thread wait voting.." +e.getMessage());
        }
    }
}