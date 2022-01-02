package eco.bike.rental.service;

import eco.bike.rental.entity.BikeParking;

import java.util.List;

public interface IBikeParkingService {
    BikeParking save(BikeParking bikeParking);
    BikeParking getById(Long id);
    List<BikeParking> findAll();
}
