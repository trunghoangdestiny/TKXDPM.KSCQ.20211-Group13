package eco.bike.rental.controller;

import eco.bike.rental.entity.bike.BaseBike;
import eco.bike.rental.entity.bike.ElectricSingleBike;
import eco.bike.rental.entity.bike.NormalCoupleBike;
import eco.bike.rental.entity.bike.NormalSingleBike;
import eco.bike.rental.service.IBikeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.stream.Collectors;

@Controller
public class BikeController {
    @Autowired
    private IBikeService<NormalSingleBike> normalSingleBikeService;

    @Autowired
    private IBikeService<NormalCoupleBike> normalCoupleBikeService;

    @Autowired
    private IBikeService<ElectricSingleBike> electricSingleBikeService;

    @GetMapping("/bike-parking/{bikeParkingId}/bike")
    public ModelAndView getBikeDetails(
            @PathVariable("bikeParkingId") Long bikeParkingId,
            @RequestParam("bikeCode") String bikeCode,
            ModelMap model
    ) {
        List<NormalSingleBike> singleBikes = normalSingleBikeService.getByCodeBike(bikeCode);
        List<NormalCoupleBike> coupleBikes = normalCoupleBikeService.getByCodeBike(bikeCode);
        List<ElectricSingleBike> electricSingleBikes = electricSingleBikeService.getByCodeBike(bikeCode);

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
}
