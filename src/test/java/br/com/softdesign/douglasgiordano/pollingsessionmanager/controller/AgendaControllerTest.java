package br.com.softdesign.douglasgiordano.pollingsessionmanager.controller;

import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.to.request.AgendaInsertTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.to.response.AgendaTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.exception.config.ApiError;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.Path;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.persistence.AgendaReactiveRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Douglas Montanha Giordano
 * Testing the creation of the agenda.
 */
@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AgendaControllerTest extends SuperControllerTest {
    @Autowired
    private AgendaReactiveRepository repository;

    public MvcResult requestCreateAgenda(String description, ResultMatcher status) throws Exception {
        String url = Path.AGENDA;
        AgendaInsertTO agendaTO = new AgendaInsertTO();
        agendaTO.setDescription(description);
        MvcResult mvcResult = mvc.perform(
                MockMvcRequestBuilders
                        .post(url)
                        .content(this.mapToJson(agendaTO))
                        .contentType("application/json")
                        .accept("application/json")
        ).andExpect(status).andReturn();
        return mvcResult;
    }

    public AgendaTO createAgendaValid(String description) throws Exception {
        String content = this.requestCreateAgenda(description, status().isOk()).getResponse().getContentAsString();
        AgendaTO response = this.mapFromJson(content, AgendaTO.class);
        return response;
    }

    public ApiError createAgendaInvalid(String description) throws Exception {
        String content = this.requestCreateAgenda(description, status().isBadRequest()).getResponse().getContentAsString();
        ApiError response = this.mapFromJson(content, ApiError.class);
        return response;
    }

    public void checkAttrAgenda(AgendaTO responseTO, String expected) {
        if (responseTO != null) {
            Assertions.assertEquals(expected, responseTO.getDescription());
            Assertions.assertNotNull(responseTO.getId());
            repository.deleteById(responseTO.getId());
        } else {
            Assertions.fail("Object is null!");
        }
    }

    @Test
    @Order(1)
    public void createAgendaStatusOkValidTest() throws Exception {
        AgendaTO response = this.createAgendaValid("My Agenda");
        repository.deleteById(response.getId());
    }

    @ParameterizedTest
    @ValueSource(strings = {"AHHHHHHHHHHHHHHHHHH!@)U()DJSNMdskfnm1203912ebdaskldasmd 1023u1908adnumsidgb1723f1iunhmaosduj9mq8yed192ew19dqasoidjq9w8dy19",
            "My Agenda", "My Agenda  ", "My Agenda*(!@&$*@$_)!(@", "MY MY", "q23412394743269", "9213901298310238912"})
    @Order(2)
    public void createAgendaSuperNameValidTest(String description) throws Exception {
        String expected = description;
        AgendaTO responseTO = this.createAgendaValid(expected);
        this.checkAttrAgenda(responseTO, expected);
    }

    @ParameterizedTest
    //@NullSource
    //@EmptySource
    @ValueSource(strings = {" ", "   ", "\t", "\n"})
    @Order(3)
    public void createAgendaSuperNameInvalidTest(String description) throws Exception {
        String expected = description;
        this.requestCreateAgenda(description, status().isOk());
    }
}
