package br.com.softdesign.douglasgiordano.pollingsessionmanager.service;

import br.com.softdesign.douglasgiordano.pollingsessionmanager.exception.EntityNotFoundException;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.messaging.ResultPollingQueueSender;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.Agenda;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.EnumVotingStatus;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.VotingResult;
import com.fasterxml.jackson.core.JsonProcessingException;
import lombok.extern.java.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import java.util.logging.Level;

/**
 * @author Douglas Montanha Giordano
 * Class to close the voting session and send the result to a queue.
 */
@Component
@EnableAsync
@Log
public class AsyncTimeVoting {

    @Autowired
    private AgendaService service;

    @Autowired
    private ToService toService;

    @Autowired
    private ResultPollingQueueSender resultSender;

    @Async
    public void asyncMethodTimeVoting(String idAgenda) throws EntityNotFoundException {
        Agenda agenda = service.findAgendaById(idAgenda);
        int time = agenda.getVoting().getTimeSeconds();
        log.info("Execute method asynchronously..");
        log.info("Session "+agenda.getId()+ " open for " + time + " seconds.");
        try {
            Thread.sleep(agenda.getVoting().getTimeSeconds()*1000);
            agenda = service.findAgendaById(idAgenda);
            agenda.getVoting().setStatus(EnumVotingStatus.CLOSED);
            VotingResult result = this.service.getResultPolling(agenda.getId());
            agenda.getVoting().setResult(result);
            this.service.saveAgenda(agenda);
            log.info("Session "+agenda.getId()+ " closed.");
            this.resultSender.send(agenda);
        } catch (InterruptedException | JsonProcessingException e) {
            log.log(Level.SEVERE, "Error thread wait voting.." +e.getMessage());
        }
    }

}