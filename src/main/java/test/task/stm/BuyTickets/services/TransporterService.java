package test.task.stm.BuyTickets.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import test.task.stm.BuyTickets.exception.DataNotFoundException;
import test.task.stm.BuyTickets.models.Transporter;
import test.task.stm.BuyTickets.repositories.TransporterRepository;

import java.util.List;

@Service
public class TransporterService {
    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private final TransporterRepository transporterRepository;

    public TransporterService(TransporterRepository transporterRepository) {
        this.transporterRepository = transporterRepository;
    }

    public Transporter find(String name) {
        return transporterRepository.get(name);
    }

    public List<Transporter> findAll()  {
        List<Transporter> transporters = transporterRepository.getAll();
        if (transporters.isEmpty()) {
            throw new DataNotFoundException("0 objects was found");
        }
        return transporters;
    }

    public Transporter add(Transporter transporter) {
        return transporterRepository.save(transporter);
    }

    public Transporter edit(Transporter new_transporter) {
        return transporterRepository.update(new_transporter);
    }

    public void delete(String name) {
        if (transporterRepository.delete(name) == 0) {
            throw new DataNotFoundException("No data was found for deletion");
        };
    }
}
