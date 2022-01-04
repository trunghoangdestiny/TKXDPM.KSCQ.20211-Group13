package eco.bike.rental.repository.bike;

import java.util.List;

public interface IBaseBikeRepository<T> {
    List<T> findByBikeParkingId(Long bikeParkingId);
    List<T> findByCodeBikeAndBikeParkingId(String bikeCode, Long bikeParkingId);
    T findByIdAndBikeParkingId(Long id, Long bikeParkingId);
}
