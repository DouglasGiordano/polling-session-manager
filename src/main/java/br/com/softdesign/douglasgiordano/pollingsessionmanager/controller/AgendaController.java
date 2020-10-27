package br.com.softdesign.douglasgiordano.pollingsessionmanager.controller;

import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.to.request.AgendaInsertTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.to.request.VoteTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.to.response.AgendaTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.to.response.SessionPollingStatusTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.to.response.VoteStatusTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.exception.EntityNotFoundException;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.exception.UnableVoteException;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.exception.VotingClosedException;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.exception.VotingOpenException;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.exception.config.ApiError;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.Path;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.Agenda;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.Vote;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.VotingResult;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.service.AgendaService;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.service.ToService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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
    @Operation(summary = "Create new agenda.", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json", schema = @Schema(implementation = AgendaTO.class)))})
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
    @Operation(summary = "Open votting agenda for 60 seconds.", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = SessionPollingStatusTO.class))),
            @ApiResponse(responseCode = "404", description = "Agenda Not Found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "400", description = "The voting session is closed or The voting session now open.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
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
    @Operation(summary = "Single vote of an associate for an agenda, sending yes or no.", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = VoteStatusTO.class))),
            @ApiResponse(responseCode = "404", description = "Agenda Not Found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "403", description = "Associate not eligible to vote or An error occurred while consulting the CPF.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "400", description = "The voting session is closed.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @PostMapping(Path.VOTING_AGENDA)
    public ResponseEntity vote(@PathVariable("idAgenda") String idAgenda,
                               @RequestBody VoteTO voteTO) throws UnableVoteException, VotingClosedException, VotingOpenException, EntityNotFoundException {
        Vote vote = this.toService.getVote(voteTO);
        Agenda agenda = service.vote(idAgenda, vote);
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.toService.getVoteResponse(vote));
    }
    /**
     * Get voting result
     * @param idAgenda
     * @return Vote
     * @throws Exception
     */
    @Operation(summary = "Get voting result agenda.", responses = {
            @ApiResponse(description = "Successful Operation", responseCode = "200", content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = VotingResult.class))),
            @ApiResponse(responseCode = "404", description = "Agenda Not Found.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class))),
            @ApiResponse(responseCode = "400", description = "The voting session is open.",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = ApiError.class)))})
    @GetMapping(Path.RESULT_VOTING_AGENDA)
    public ResponseEntity vote(@PathVariable("idAgenda") String idAgenda) throws EntityNotFoundException {
        Agenda agenda = service.findAgendaById(idAgenda);
        return ResponseEntity.status(HttpStatus.OK)
                .body(this.toService.getVotingResult(agenda));
    }
}
