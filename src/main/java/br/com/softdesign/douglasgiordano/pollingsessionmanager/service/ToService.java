package br.com.softdesign.douglasgiordano.pollingsessionmanager.service;

import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.to.request.AgendaInsertTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.to.request.VoteTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.to.response.AgendaTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.to.response.SessionPollingStatusTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.to.response.VoteStatusTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.to.response.VotingResultTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.Agenda;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.Associate;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.Vote;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

/**
 * @author Douglas Montanha Giordano
 * Class to map transfer objects.
 */
@Service
public class ToService {

    /**
     * Map Agenda to AgendaResponseTO
     *
     * @param agenda
     * @return Agenda Response
     */
    public AgendaTO getAgendaResponseTO(Agenda agenda) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(agenda, AgendaTO.class);
    }

    /**
     * Map Agenda to VotingStatus
     *
     * @param agenda
     * @return Voting Status Response
     */
    public SessionPollingStatusTO getVotingStatusTO(Agenda agenda) {
        return new SessionPollingStatusTO(agenda.getDescription(), agenda.getVoting().getStatus().name());
    }

    /**
     * Map AgendaInsertTO to Agenda
     *
     * @param agendaTO
     * @return Agenda
     */
    public Agenda getAgenda(AgendaInsertTO agendaTO) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(agendaTO, Agenda.class);
    }

    /**
     * Map VoteTO to Vote
     *
     * @param voteTo
     * @return vote
     */
    public Vote getVote(VoteTO voteTo) {
        return new Vote(new Associate(voteTo.getCpf()), voteTo.getVote());
    }

    /**
     * Map Vote to VoteStatusTO
     *
     * @param vote
     * @return vote
     */
    public VoteStatusTO getVoteResponse(Vote vote) {
        return new VoteStatusTO(vote.getAssociate().getCpf(), vote.getVote(), vote.getAssociate().getStatus());
    }

    /**
     * Map VotingResult to VotingResultTO
     *
     * @param agenda
     * @return vote
     */
    public VotingResultTO getVotingResult(Agenda agenda) {
        return new VotingResultTO(agenda.getId(), agenda.getVoting().getResult().getResult().name());
    }
}
