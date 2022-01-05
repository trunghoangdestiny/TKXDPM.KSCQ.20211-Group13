package eco.bike.rental.service.impl;

import eco.bike.rental.entity.bike.NormalSingleBike;
import eco.bike.rental.repository.bike.INormalSingleBikeRepository;
import eco.bike.rental.service.IBikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NormalSingleBikeService implements IBikeService<NormalSingleBike> {
    @Autowired
    private INormalSingleBikeRepository normalSingleBikeRepository;

    @Override
    public NormalSingleBike save(NormalSingleBike normalSingleBike) {
        return normalSingleBikeRepository.save(normalSingleBike);
    }

    @Override
    public List<NormalSingleBike> getAllBikeOfBikeParking(Long bikeParkingId) {
        return normalSingleBikeRepository.findByBikeParkingId(bikeParkingId);
    }

    @Override
    public List<NormalSingleBike> getByCodeBikeAndBikeParkingId(String bikeCode, Long bikeParkingId) {
        return normalSingleBikeRepository.findByCodeBikeAndBikeParkingId(bikeCode, bikeParkingId);
    }

    @Override
    public NormalSingleBike getByIdAndBikeParkingId(Long id, Long bikeParkingId) {
        return normalSingleBikeRepository.findByIdAndBikeParkingId(id, bikeParkingId);
    }

    @Override
    public NormalSingleBike getByCodeBike(String bikeCode) {
        return normalSingleBikeRepository.findByCodeBike(bikeCode);
    }
}
