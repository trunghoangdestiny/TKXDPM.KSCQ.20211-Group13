package eco.bike.rental.controller;

import eco.bike.rental.entity.BikeParking;
import eco.bike.rental.entity.OrderHistory;
import eco.bike.rental.entity.bike.BaseBike;
import eco.bike.rental.entity.bike.ElectricSingleBike;
import eco.bike.rental.entity.bike.NormalCoupleBike;
import eco.bike.rental.entity.bike.NormalSingleBike;
import eco.bike.rental.service.IBikeParkingService;
import eco.bike.rental.service.IBikeService;
import eco.bike.rental.service.IOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

@Controller
public class BikeRentingController {
    @Autowired
    private IBikeService<NormalSingleBike> normalSingleBikeService;

    @Autowired
    private IBikeService<NormalCoupleBike> normalCoupleBikeService;

    @Autowired
    private IBikeService<ElectricSingleBike> electricSingleBikeService;

    @Autowired
    private IBikeParkingService bikeParkingService;

    @Autowired
    private IOrderService orderService;

    @GetMapping("/bike-renting")
    public ModelAndView getRentingInfo(
            @RequestParam("bikeParkingId") Long bikeParkingId,
            @RequestParam("bikeId") Long bikeId,
            ModelMap model
    ) {
        BikeParking bikeParking = bikeParkingService.getById(bikeParkingId);
        model.addAttribute("bikeParking", bikeParking);

        BaseBike bike = normalSingleBikeService.getByIdAndBikeParkingId(bikeId, bikeParkingId);
        if (bike != null) {
            model.addAttribute("bike", bike);
            return new ModelAndView("bikeRentingPayment", model);
        }

        bike = normalCoupleBikeService.getByIdAndBikeParkingId(bikeId, bikeParkingId);
        if (bike != null) {
            model.addAttribute("bike", bike);
            return new ModelAndView("bikeRentingPayment", model);
        }

        bike = electricSingleBikeService.getByIdAndBikeParkingId(bikeId, bikeParkingId);
        if (bike != null) {
            model.addAttribute("bike", bike);
            return new ModelAndView("bikeRentingPayment", model);
        }

        model.addAttribute("getRentingInfo", false);
        return new ModelAndView("redirect:/bike-parking/" + bikeParkingId + "/bike/" + bikeId, model);
    }

    @GetMapping("/rental-bike-list")
    public String getRentalBikeList(Model model) {
        Map<OrderHistory, BaseBike> orderMap = orderService.getAllOrder();
        model.addAttribute("orderAndBikeMap", orderMap);
        return "rentingBikeList";
    }
}
