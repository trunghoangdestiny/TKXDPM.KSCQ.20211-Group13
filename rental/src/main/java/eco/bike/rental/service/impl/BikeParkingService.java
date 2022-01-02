package eco.bike.rental.service.impl;

import eco.bike.rental.entity.BikeParking;
import eco.bike.rental.repository.IBikeParkingRepository;
import eco.bike.rental.service.IBikeParkingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BikeParkingService implements IBikeParkingService {
    @Autowired
    private IBikeParkingRepository bikeParkingRepository;

    @Override
    public BikeParking save(BikeParking bikeParking) {
        return bikeParkingRepository.save(bikeParking);
    }

    @Override
    public BikeParking getById(Long id) {
        return bikeParkingRepository.getById(id);
    }

    @Override
    public List<BikeParking> findAll() {
        return bikeParkingRepository.findAll();
    }
}
