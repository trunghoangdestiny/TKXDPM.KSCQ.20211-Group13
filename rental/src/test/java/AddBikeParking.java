import eco.bike.rental.entity.BikeParking;
import eco.bike.rental.service.impl.BikeParkingService;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;

public class AddBikeParking {
    public static void main(String[] args) {
        ApplicationContext applicationContext = SpringApplication.run(AddBikeParking.class, args);

        BikeParking bikeParking = new BikeParking();
        bikeParking.setName("Bai xe number one");
        bikeParking.setAddress("Hai Ba Trung");
        bikeParking.setCurrentQuantity(500);
        bikeParking.setArea(100);

        BikeParkingService bikeParkingService = applicationContext.getBean(BikeParkingService.class);
        System.out.println(bikeParking);

        bikeParking = bikeParkingService.save(bikeParking);
    }
}
