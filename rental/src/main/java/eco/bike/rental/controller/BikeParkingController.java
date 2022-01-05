package eco.bike.rental.controller;

import eco.bike.rental.entity.BikeParking;
import eco.bike.rental.entity.bike.BaseBike;
import eco.bike.rental.entity.bike.ElectricSingleBike;
import eco.bike.rental.entity.bike.NormalCoupleBike;
import eco.bike.rental.entity.bike.NormalSingleBike;
import eco.bike.rental.service.IBikeParkingService;
import eco.bike.rental.service.IBikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.ArrayList;
import java.util.List;

@Controller
public class BikeParkingController {
    @Autowired
    private IBikeParkingService bikeParkingService;

    @Autowired
    private IBikeService<NormalSingleBike> normalSingleBikeService;

    @Autowired
    private IBikeService<NormalCoupleBike> normalCoupleBikeIBikeService;

    @Autowired
    private IBikeService<ElectricSingleBike> electricSingleBikeIBikeService;

    @GetMapping("/")
    public String getHome(Model model) {
        List<BikeParking> bikeParkingList = bikeParkingService.findAll();
        model.addAttribute("bikeParkingList", bikeParkingList);

        return "redirect:/bike-parking";
    }

    @GetMapping("/bike-parking")
    public String getBikeParkingList(Model model) {
        List<BikeParking> bikeParkingList = bikeParkingService.findAll();
        model.addAttribute("bikeParkingList", bikeParkingList);

        return "bikeParkingList";
    }

    @GetMapping("/bike-parking/{id}")
    public String getBikeParkingDetails(@PathVariable("id") Long id, Model model) {
        BikeParking bikeParking = bikeParkingService.getById(id);

        List<BaseBike> bikes = new ArrayList<>();

        List<NormalSingleBike> normalSingleBikes = normalSingleBikeService.getAllBikeOfBikeParking(id);
        List<NormalCoupleBike> normalCoupleBikes = normalCoupleBikeIBikeService.getAllBikeOfBikeParking(id);
        List<ElectricSingleBike> electricSingleBikes = electricSingleBikeIBikeService.getAllBikeOfBikeParking(id);

        bikes.addAll(normalSingleBikes);
        bikes.addAll(normalCoupleBikes);
        bikes.addAll(electricSingleBikes);
        model.addAttribute("bikes", bikes); //get all bike in table

        model.addAttribute("normalSingleBikeQuantity", 10 - normalSingleBikes.size());
        model.addAttribute("normalCoupleBikeQuantity", 10 - normalCoupleBikes.size());
        model.addAttribute("electricSingleBikeQuantity", 10 - electricSingleBikes.size());

        model.addAttribute("totalQuantity", bikes.size());

        model.addAttribute("bikeParking", bikeParking);

        return "bikeParkingDetails";
    }

}
