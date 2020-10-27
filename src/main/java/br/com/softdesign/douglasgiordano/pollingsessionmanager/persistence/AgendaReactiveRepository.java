package br.com.softdesign.douglasgiordano.pollingsessionmanager.persistence;

import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.Agenda;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Douglas Montanha Giordano
 * Repository for the Agenda
 */
@Repository
public interface AgendaReactiveRepository
        extends ReactiveMongoRepository<Agenda, String> {
}
