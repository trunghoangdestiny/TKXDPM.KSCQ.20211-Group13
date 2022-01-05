package eco.bike.rental.service;

import java.util.List;

public interface IBikeService<T>{
    T save(T t); // use to save or update
    List<T> getAllBikeOfBikeParking(Long bikeParkingId);
    List<T> getByCodeBikeAndBikeParkingId(String bikeCode, Long bikeParkingId);
    T getByIdAndBikeParkingId(Long id, Long bikeParkingId);
    T getByCodeBike(String bikeCode);
}
