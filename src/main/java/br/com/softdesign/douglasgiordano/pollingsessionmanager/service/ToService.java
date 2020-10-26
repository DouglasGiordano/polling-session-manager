package br.com.softdesign.douglasgiordano.pollingsessionmanager.service;

import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.to.request.AgendaInsertTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.to.request.VoteTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.to.response.AgendaResponseTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.to.response.VoteResponseTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.to.response.VotingStatusResponseTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.Agenda;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.Associate;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.Vote;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

@Service
public class ToService {

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

    /**
     *
     * @param voteTo
     * @return vote
     */
    public Vote getVote(VoteTO voteTo){
        return new Vote(new Associate(voteTo.getCpf()), voteTo.getVote());
    }

    /**
     *
     * @param vote
     * @return vote
     */
    public VoteResponseTO getVoteResponse(Vote vote){
        return new VoteResponseTO(vote.getAssociate().getCpf(), vote.getVote(), vote.getAssociate().getStatus());
    }
}
