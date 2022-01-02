package eco.bike.rental.service.impl;

import eco.bike.rental.entity.bike.ElectricSingleBike;
import eco.bike.rental.repository.bike.IElectricSingleBikeRepository;
import eco.bike.rental.service.IBikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ElectricSingleBikeService implements IBikeService<ElectricSingleBike> {
    @Autowired
    private IElectricSingleBikeRepository electricSingleBikeRepository;

    @Override
    public ElectricSingleBike save(ElectricSingleBike electricSingleBike) {
        return electricSingleBikeRepository.save(electricSingleBike);
    }

    @Override
    public List<ElectricSingleBike> getAllBikeOfBikeParking(Long bikeParkingId) {
        return electricSingleBikeRepository.findByBikeParkingId(bikeParkingId);
    }

    @Override
    public List<ElectricSingleBike> getByCodeBike(String bikeCode) {
        return electricSingleBikeRepository.findByCodeBike(bikeCode).stream().filter(
                bike -> bike.getInUsed() == null || !bike.getInUsed()
        ).collect(Collectors.toList());
    }
}
