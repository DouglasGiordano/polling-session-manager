package br.com.softdesign.douglasgiordano.pollingsessionmanager.controller;

import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.requestTO.AgendaInsertTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.responseTO.AgendaResponseTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.responseTO.VotingStatusResponseTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.exception.config.ApiError;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.Path;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.persistence.AgendaReactiveRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.junit.jupiter.api.*;
import org.modelmapper.internal.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AgendaControllerTest extends  SuperControllerTest{
    @Autowired
    private AgendaReactiveRepository repository;

    public AgendaResponseTO createAgendaValid(String description) throws Exception {
        String url = Path.AGENDA;
        AgendaInsertTO agendaTO = new AgendaInsertTO();
        agendaTO.setDescription(description);
        MvcResult mvcResult = mvc.perform(
                MockMvcRequestBuilders
                        .post(url)
                        .content(this.mapToJson(agendaTO))
                        .contentType("application/json")
                        .accept("application/json")
        ).andExpect(status().isOk()).andReturn();

        String content = mvcResult.getResponse().getContentAsString();
        AgendaResponseTO response = this.mapFromJson(content, AgendaResponseTO.class);
        return response;
    }

    public ApiError createAgendaInvalid(String description) throws Exception {
        String url = Path.AGENDA;
        AgendaInsertTO agendaTO = new AgendaInsertTO();
        agendaTO.setDescription(description);
        MvcResult mvcResult = mvc.perform(
                MockMvcRequestBuilders
                        .post(url)
                        .content(this.mapToJson(agendaTO))
                        .contentType("application/json")
                        .accept("application/json")
        ).andExpect(status().isBadRequest()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        ApiError response = this.mapFromJson(content, ApiError.class);
        return response;
    }

    @Test
    @Order(1)
    public void createAgendaStatusOkTest() throws Exception {
        String url = Path.AGENDA;
        System.out.println(url);
        AgendaInsertTO agendaTO = new AgendaInsertTO();
        agendaTO.setDescription("My Agenda");
        MvcResult mvcResult = mvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(this.mapToJson(agendaTO))
        ).andExpect(status().isOk()).andReturn();
        String content = mvcResult.getResponse().getContentAsString();
        AgendaResponseTO response = this.mapFromJson(content, AgendaResponseTO.class);
        repository.deleteById(response.getId());
    }

    @Test
    @Order(2)
    public void createAgendaSuperNameTest() throws Exception {
        String expected = "AHHHHHHHHHHHHHHHHHH!@)U()DJSNMdskfnm1203912ebdaskldasmd 1023u1908adnumsidgb1723f1iunhmaosduj9mq8yed192ew19dqasoidjq9w8dy19";
        AgendaResponseTO responseTO = this.createAgendaValid(expected);
        if(responseTO != null){
            Assertions.assertEquals(expected, responseTO.getDescription());
            Assertions.assertNotNull(responseTO.getId());
            repository.deleteById(responseTO.getId());
        } else {
            Assertions.fail("Object is null!");
        }
    }

    @Test
    @Order(3)
    public void openAgendaPollingTest() throws Exception {
        String expected = "My Agenda Polling";
        AgendaResponseTO responseTO = this.createAgendaValid(expected);

        String url = Path.AGENDA + Path.OPEN_VOTING_AGENDA;
        url.replace("{idAgenda}", responseTO.getId());

        MvcResult result = mvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();

        String content = result.getResponse().getContentAsString();
        VotingStatusResponseTO response = this.mapFromJson(content, VotingStatusResponseTO.class);
        if(response != null){
            Assertions.assertEquals("OPEN", response.getStatus());
            Assertions.assertNotNull("expected", response.getDescription());
            repository.deleteById(responseTO.getId());
        } else {
            Assertions.fail("Object is null!");
        }
    }
}
