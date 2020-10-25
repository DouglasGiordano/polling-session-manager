package br.com.softdesign.douglasgiordano.pollingsessionmanager.persistence;

import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.Agenda;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;
import org.junit.jupiter.api.Order;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@SpringBootTest
@AutoConfigureMockMvc
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AgendaReactiveRepositoryTest {
    @Autowired
    private AgendaReactiveRepository repository;

    @Test
    @Order(1)
    public void saveNewAgenda() {
        Agenda a = repository.save(new Agenda("Agenda Soft Design")).block();
        Mono<Agenda> accountFlux = repository.findById(a.getId());

        StepVerifier
                .create(accountFlux)
                .assertNext(agenda -> {
                    Assertions.assertEquals("Agenda Soft Design", agenda.getDescription());
                    assertNotNull(agenda.getId());
                })
                .expectComplete()
                .verify();
    }

    @Test
    @Order(2)
    public void deleteAgenda() {
        Agenda a = repository.save(new Agenda("Agenda Soft Design")).block();
        String id = a.getId();
        repository.delete(a).block();
        Agenda aFind = repository.findById(id).block();
        assertNull(aFind);
    }
}
