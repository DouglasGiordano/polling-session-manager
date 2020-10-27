package br.com.softdesign.douglasgiordano.pollingsessionmanager.controller;

import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.to.request.VoteTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.to.response.VoteStatusTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.exception.config.ApiError;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.Path;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.*;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.persistence.AgendaReactiveRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Douglas Montanha Giordano
 * Testing member voting.
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class VoteControllerTest extends SuperControllerTest {

    @Autowired
    private AgendaReactiveRepository repository;

    private Agenda agenda;

    @BeforeEach
    public void beforeEach() {
        this.agenda = new Agenda();
        this.agenda.setDescription("My Agenda Vote!");
        this.agenda.setVoting(new VotingAgenda());
        this.agenda.getVoting().setStatus(EnumVotingStatus.OPEN);
        this.agenda = this.repository.save(this.agenda).block();
    }

    @AfterEach
    public void afterEach() {
        this.repository.deleteById(this.agenda.getId());
        this.agenda = null;
    }


    /**
     * Request open session polling
     *
     * @param status
     * @return
     * @throws Exception
     */
    public MvcResult requestOpenSession(ResultMatcher status, VoteTO vote) throws Exception {
        Agenda agenda = new Agenda();
        String url = Path.AGENDA + Path.VOTING_AGENDA;
        url = url.replace("{idAgenda}", this.agenda.getId());

        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(this.mapToJson(vote))
        ).andExpect(status).andReturn();
        return result;
    }

    /**
     * Check result vote
     *
     * @param voteStatusTO
     * @param expectedCpf
     * @param expectedStatus
     */
    public void checkAttrVoteStatusTo(VoteStatusTO voteStatusTO, String expectedCpf, EnumStatusAssociate expectedStatus) {
        if (voteStatusTO != null) {
            Assertions.assertEquals(expectedCpf, voteStatusTO.getCpf());
            Assertions.assertEquals(expectedStatus, voteStatusTO.getStatus());
        } else {
            Assertions.fail("Object is null!");
        }
    }

    @Order(1)
    @ParameterizedTest
    @ValueSource(strings = {"03048651098",
            "36611464166", "53774296057", "11315580578", "52717812245", "97558222591", "57318932500"})
    public void voteValidAssociateYesTest(String cpf) throws Exception {
        VoteTO vote = new VoteTO(cpf, EnumVote.YES);
        String content = this.requestOpenSession(status().isOk(), vote).getResponse().getContentAsString();
        VoteStatusTO response = this.mapFromJson(content, VoteStatusTO.class);
        this.checkAttrVoteStatusTo(response, cpf, EnumStatusAssociate.ABLE_TO_VOTE);
    }

    @Order(2)
    @ParameterizedTest
    @ValueSource(strings = {"03048651098",
            "36611464166", "53774296057", "11315580578", "52717812245", "97558222591", "57318932500"})
    public void voteValidAssociateNoTest(String cpf) throws Exception {
        VoteTO vote = new VoteTO(cpf, EnumVote.NO);
        String content = this.requestOpenSession(status().isOk(), vote).getResponse().getContentAsString();
        VoteStatusTO response = this.mapFromJson(content, VoteStatusTO.class);
        this.checkAttrVoteStatusTo(response, cpf, EnumStatusAssociate.ABLE_TO_VOTE);
    }

    @Order(3)
    @ParameterizedTest
    @ValueSource(strings = {"asdasd1",
            "123123", "5377429asdasd6057", "qeqwe", "123123", " ", "asdasd"})
    public void voteInvalidAssociateTest(String cpf) throws Exception {
        VoteTO vote = new VoteTO(cpf, EnumVote.NO);
        String content = this.requestOpenSession(status().isForbidden(), vote).getResponse().getContentAsString();
        ApiError response = this.mapFromJson(content, ApiError.class);
        this.checkAttrApiErrorOr(response, new String[]{"Associate not eligible to vote.", "An error occurred while consulting the CPF."});
    }

    @Order(4)
    @ParameterizedTest
    @ValueSource(strings = {"03048651098",
            "36611464166", "53774296057", "11315580578", "52717812245", "97558222591", "57318932500"})
    public void voteInValidAssociateSessionClosedTest(String cpf) throws Exception {
        VoteTO vote = new VoteTO(cpf, EnumVote.YES);
        this.agenda.getVoting().setStatus(EnumVotingStatus.CLOSED);
        this.repository.save(this.agenda).block();
        String content = this.requestOpenSession(status().isBadRequest(), vote).getResponse().getContentAsString();
        ApiError response = this.mapFromJson(content, ApiError.class);
        this.checkAttrApiError(response, "The voting session is closed.");
    }

}
