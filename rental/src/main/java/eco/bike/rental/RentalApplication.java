package eco.bike.rental;

import eco.bike.rental.entity.BikeParking;
import eco.bike.rental.entity.bike.ElectricSingleBike;
import eco.bike.rental.entity.bike.NormalCoupleBike;
import eco.bike.rental.entity.bike.NormalSingleBike;
import eco.bike.rental.service.impl.BikeParkingService;
import eco.bike.rental.service.impl.ElectricSingleBikeService;
import eco.bike.rental.service.impl.NormalCoupleBikeService;
import eco.bike.rental.service.impl.NormalSingleBikeService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

import java.util.Random;

@SpringBootApplication
public class RentalApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(RentalApplication.class, args);

        BikeParkingService bikeParkingService = context.getBean(BikeParkingService.class);
        ElectricSingleBikeService electricSingleBikeService = context.getBean(ElectricSingleBikeService.class);
        NormalSingleBikeService normalSingleBikeService = context.getBean(NormalSingleBikeService.class);
        NormalCoupleBikeService normalCoupleBikeService = context.getBean(NormalCoupleBikeService.class);

        Random numberGenerator = new Random();

        String[] address = {"Hai ba trung", "Hoang mai", "thanh nhan", "Minh khai", "dong da", "ba dinh"};

//        for (int i = 0; i < 10; i++) {
//            BikeParking bikeParking = new BikeParking();
//            bikeParking.setName("Bai xe so " + i);
//            bikeParking.setAddress(address[numberGenerator.nextInt(address.length)] + " " + i);
//            bikeParking.setCurrentQuantity(numberGenerator.nextInt(500) + 500);
//            bikeParking.setArea(numberGenerator.nextInt(500) + 500);
//
//            bikeParking = bikeParkingService.save(bikeParking);
//        }

//        for (int i = 1; i < 11; i++) {
//            for (int j = 0; j < numberGenerator.nextInt(4) + 2; j++) {
////                NormalSingleBike bike = new NormalSingleBike();
////                ElectricSingleBike bike = new ElectricSingleBike();
//                NormalCoupleBike bike = new NormalCoupleBike();
//                bike.setName("Bike " + j);
//                bike.setBikeNumber(
//                        numberGenerator.nextInt(90) + 10 + "-X"
//                                + numberGenerator.nextInt(10) + " "
//                                + (numberGenerator.nextInt(9000) + 1000)
//                );
//                bike.setCodeBike((numberGenerator.nextInt(90000000) + 10000000) + "");
//                bike.setBikePrice(1750000f);
//
//                //for electric bike
////                bike.setCurrentBattery((long) numberGenerator.nextInt(60) + 41);
////                bike.setEstimatedTime((long) numberGenerator.nextInt(80) + 121);
//
//                BikeParking bikeParking = bikeParkingService.getById((long) i);
//                bike.setBikeParking(bikeParking);
//
////                bike = electricSingleBikeService.save(bike);
////                bike = normalSingleBikeService.save(bike);
//                bike = normalCoupleBikeService.save(bike);
//            }
//        }
    }
}
