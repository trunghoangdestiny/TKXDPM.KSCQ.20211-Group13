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

        return "redirect:/bike-parking-list";
    }

    @GetMapping("/bike-parking-list")
    public String getBikeParkingList(Model model) {
        List<BikeParking> bikeParkingList = bikeParkingService.findAll();
        model.addAttribute("bikeParkingList", bikeParkingList);

        return "bikeParkingList";
    }

    @GetMapping("/bike-parking-list/{id}")
    public String getBikeParkingDetails(@PathVariable("id") Long id, Model model) {
        BikeParking bikeParking = bikeParkingService.getById(id);
        List<BaseBike> bikes = new ArrayList<>();
        bikes.addAll(normalSingleBikeService.getAllBikeOfBikeParking(bikeParking.getId()));
        bikes.addAll(normalCoupleBikeIBikeService.getAllBikeOfBikeParking(bikeParking.getId()));
        bikes.addAll(electricSingleBikeIBikeService.getAllBikeOfBikeParking(bikeParking.getId()));
        model.addAttribute("normalSingleBike", bikes); //get all bike in table
        model.addAttribute("bikeParking", bikeParking);

        return "bikeParkingDetails";
    }
}
