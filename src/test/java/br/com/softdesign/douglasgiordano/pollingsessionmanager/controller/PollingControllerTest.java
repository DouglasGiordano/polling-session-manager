package br.com.softdesign.douglasgiordano.pollingsessionmanager.controller;

import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.to.response.SessionPollingStatusTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.exception.config.ApiError;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.Path;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.Agenda;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.EnumVotingStatus;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.VotingAgenda;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.persistence.AgendaReactiveRepository;
import org.junit.jupiter.api.*;
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
 * Testing the opening of voting sessions.
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PollingControllerTest extends SuperControllerTest {
    @Autowired
    private AgendaReactiveRepository repository;

    private Agenda agenda;

    @BeforeEach
    public void beforeEach() {
        this.agenda = new Agenda();
        this.agenda.setDescription("My Agenda Polling!");
        this.agenda = repository.save(agenda).block();
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
    public MvcResult requestOpenSession(ResultMatcher status) throws Exception {
        Agenda agenda = new Agenda();
        String url = Path.AGENDA + Path.OPEN_VOTING_AGENDA;
        url = url.replace("{idAgenda}", this.agenda.getId());

        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status).andReturn();
        return result;
    }

    /**
     * check result open polling
     *
     * @param sessionTo
     * @param expectedDescription
     * @param expectedStatus
     */
    public void checkAttrSessionPollingStatusTo(SessionPollingStatusTO sessionTo, String expectedDescription, String expectedStatus) {
        if (sessionTo != null) {
            Assertions.assertEquals(expectedDescription, sessionTo.getDescription());
            Assertions.assertEquals(expectedStatus, sessionTo.getStatus());
        } else {
            Assertions.fail("Object is null!");
        }
    }

    /**
     * Opening a valid session
     *
     * @throws Exception
     */
    @Test
    @Order(1)
    public void openAgendaPollingValidTest() throws Exception {
        String content = this.requestOpenSession(status().isOk()).getResponse().getContentAsString();
        SessionPollingStatusTO response = this.mapFromJson(content, SessionPollingStatusTO.class);
        this.checkAttrSessionPollingStatusTo(response, this.agenda.getDescription(), EnumVotingStatus.OPEN.name());
    }

    /**
     * Opening a session of an agenda that does not exist.
     *
     * @throws Exception
     */
    @Test
    @Order(2)
    public void openAgendaPollingAgendaNotFoundInvalidTest() throws Exception {
        String idAgenda = this.agenda.getId();
        this.agenda.setId("NotFound");
        String content = this.requestOpenSession(status().isNotFound()).getResponse().getContentAsString();
        ApiError response = this.mapFromJson(content, ApiError.class);
        this.checkAttrApiError(response, "No records found.");
        this.agenda.setId(idAgenda);
    }

    /**
     * Trying to open an already open voting session.
     *
     * @throws Exception
     */
    @Test
    @Order(3)
    public void openAgendaPollingAgendaOpenInvalidTest() throws Exception {
        String idAgenda = this.agenda.getId();
        this.requestOpenSession(status().isOk()).getResponse().getContentAsString();
        String content = this.requestOpenSession(status().isBadRequest()).getResponse().getContentAsString();//request second
        ApiError response = this.mapFromJson(content, ApiError.class);
        this.checkAttrApiError(response, "The voting session is now open.");
        this.agenda.setId(idAgenda);
    }

    /**
     * Trying to open a closed voting session.
     *
     * @throws Exception
     */
    @Test
    @Order(4)
    public void openAgendaPollingAgendaClosedInvalidTest() throws Exception {
        this.agenda.setVoting(new VotingAgenda());
        this.agenda.getVoting().setStatus(EnumVotingStatus.CLOSED);
        Agenda agenda = this.repository.save(this.agenda).block();
        String content = this.requestOpenSession(status().isBadRequest()).getResponse().getContentAsString();//request second
        ApiError response = this.mapFromJson(content, ApiError.class);
        this.checkAttrApiError(response, "The voting session is closed.");
    }
}
