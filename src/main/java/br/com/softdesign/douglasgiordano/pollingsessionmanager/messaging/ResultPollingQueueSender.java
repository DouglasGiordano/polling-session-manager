package br.com.softdesign.douglasgiordano.pollingsessionmanager.messaging;

import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.Agenda;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.service.ToService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.java.Log;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author Douglas Giordano
 * Class to send the voting result to a queue.
 */
@Component
@Log
public class ResultPollingQueueSender {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private ToService toService;

    @Autowired
    private Queue queue;

    /**
     * Send result polling.
     *
     * @param agenda
     * @throws JsonProcessingException
     */
    public void send(Agenda agenda) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        rabbitTemplate.convertAndSend(this.queue.getName(), mapper.writeValueAsString(toService.getVotingResult(agenda)));
        log.info("Send voting result agenda " + agenda.getId() + " ...");
    }
}