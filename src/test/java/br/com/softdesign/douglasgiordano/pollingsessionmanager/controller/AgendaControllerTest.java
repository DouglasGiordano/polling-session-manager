package br.com.softdesign.douglasgiordano.pollingsessionmanager.controller;

import br.com.softdesign.douglasgiordano.pollingsessionmanager.controller.requestTO.AgendaInsertTO;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.Path;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AgendaControllerTest extends  SuperControllerTest{
    @Test
    @Order(1)
    public void createAgendaStatusOkTest() throws Exception {
        String url = Path.AGENDA;
        AgendaInsertTO agendaTO = new AgendaInsertTO();
        agendaTO.setDescription("My Agenda");
        mvc.perform(
                MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(this.mapToJson(agendaTO))
        ).andExpect(status().isOk());
    }
}
