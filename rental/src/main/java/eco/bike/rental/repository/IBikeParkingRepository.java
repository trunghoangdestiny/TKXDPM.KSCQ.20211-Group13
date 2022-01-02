package eco.bike.rental.repository;

import eco.bike.rental.entity.BikeParking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IBikeParkingRepository extends JpaRepository<BikeParking, Long> {
}
