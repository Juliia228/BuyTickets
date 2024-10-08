package test.task.stm.BuyTickets.services;

import org.springframework.stereotype.Service;
import test.task.stm.BuyTickets.exception.DataNotFoundException;
import test.task.stm.BuyTickets.models.Route;
import test.task.stm.BuyTickets.models.DTO.RouteRequest;
import test.task.stm.BuyTickets.repositories.RouteRepository;

import java.util.List;

@Service
public class RouteService {
    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public Route find(int id) {
        return routeRepository.get(id);
    }

    public List<Route> findAll() {
        return routeRepository.getAll();
    }

    public Route add(RouteRequest route) {
        return routeRepository.save(route);
    }

    public Route edit(Route new_route) {
        return routeRepository.update(new_route);
    }

    public void delete(int id) {
        if (routeRepository.delete(id) == 0) {
            throw new DataNotFoundException("No data was found for deletion");
        }
    }
}
