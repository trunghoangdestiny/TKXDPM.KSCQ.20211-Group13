package eco.bike.rental.service.impl;

import eco.bike.rental.entity.bike.NormalCoupleBike;
import eco.bike.rental.repository.bike.INormalCoupleBikeRepository;
import eco.bike.rental.service.IBikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NormalCoupleBikeService implements IBikeService<NormalCoupleBike> {
    @Autowired
    private INormalCoupleBikeRepository normalCoupleBikeRepository;

    @Override
    public NormalCoupleBike save(NormalCoupleBike normalCoupleBike) {
        return normalCoupleBikeRepository.save(normalCoupleBike);
    }

    @Override
    public List<NormalCoupleBike> getAllBikeOfBikeParking(Long bikeParkingId) {
        return normalCoupleBikeRepository.findByBikeParkingId(bikeParkingId);
    }
}
