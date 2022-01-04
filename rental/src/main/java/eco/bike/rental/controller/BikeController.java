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
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class BikeController {
    @Autowired
    private IBikeService<NormalSingleBike> normalSingleBikeService;

    @Autowired
    private IBikeService<NormalCoupleBike> normalCoupleBikeService;

    @Autowired
    private IBikeService<ElectricSingleBike> electricSingleBikeService;

    @Autowired
    private IBikeParkingService bikeParkingService;

    @GetMapping("/bike-parking/{bikeParkingId}/bike")
    public ModelAndView searchBike(
            @PathVariable("bikeParkingId") Long bikeParkingId,
            @RequestParam("bikeCode") String bikeCode,
            ModelMap model
    ) {
        BikeParking bikeParking = bikeParkingService.getById(bikeParkingId);
        model.addAttribute("bikeParking", bikeParking);

        List<NormalSingleBike> singleBikes = normalSingleBikeService.getByCodeBikeAndBikeParkingId(bikeCode, bikeParkingId);
        List<NormalCoupleBike> coupleBikes = normalCoupleBikeService.getByCodeBikeAndBikeParkingId(bikeCode, bikeParkingId);
        List<ElectricSingleBike> electricSingleBikes = electricSingleBikeService.getByCodeBikeAndBikeParkingId(bikeCode, bikeParkingId);

        if (!singleBikes.isEmpty()) {
            model.addAttribute("bike", singleBikes.get(0));
            return new ModelAndView("bikeDetails", model);
        }

        if (!coupleBikes.isEmpty()) {
            model.addAttribute("bike", coupleBikes.get(0));
            return new ModelAndView("bikeDetails", model);
        }

        if (!electricSingleBikes.isEmpty()) {
            model.addAttribute("bike", electricSingleBikes.get(0));
            return new ModelAndView("bikeDetails", model);
        }

        model.addAttribute("getBikeSuccess", false);
        return new ModelAndView("redirect:/bike-parking/" + bikeParkingId, model);
    }

    @GetMapping("/bike-parking/{bikeParkingId}/bike/{bikeId}")
    public ModelAndView getBikeDetails(
            @PathVariable("bikeParkingId") Long bikeParkingId,
            @PathVariable("bikeId") Long bikeId,
            ModelMap model
    ) {
        BikeParking bikeParking = bikeParkingService.getById(bikeParkingId);
        model.addAttribute("bikeParking", bikeParking);

        BaseBike bike = normalSingleBikeService.getByIdAndBikeParkingId(bikeId, bikeParkingId);
        if (bike != null) {
            model.addAttribute("bike", bike);
            return new ModelAndView("bikeDetails", model);
        }

        bike = normalCoupleBikeService.getByIdAndBikeParkingId(bikeId, bikeParkingId);
        if (bike != null) {
            model.addAttribute("bike", bike);
            return new ModelAndView("bikeDetails", model);
        }

        bike = electricSingleBikeService.getByIdAndBikeParkingId(bikeId, bikeParkingId);
        if (bike != null) {
            model.addAttribute("bike", bike);
            return new ModelAndView("bikeDetails", model);
        }

        model.addAttribute("getBikeSuccess", false);
        return new ModelAndView("redirect:/bike-parking/" + bikeParkingId, model);
    }
}
