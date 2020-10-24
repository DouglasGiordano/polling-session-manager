package br.com.softdesign.douglasgiordano.pollingsessionmanager.service;

import br.com.softdesign.douglasgiordano.pollingsessionmanager.model.entities.Agenda;
import br.com.softdesign.douglasgiordano.pollingsessionmanager.persistence.AgendaReactiveRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AgendaService {
    @Autowired
    private AgendaReactiveRepository repository;

    public void createAgenda(Agenda agenda){
        repository.save(agenda);
    }
}
