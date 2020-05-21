package com.yaf.florabasket.controller;

import com.yaf.florabasket.model.Flower;
import com.yaf.florabasket.model.Orders;
import com.yaf.florabasket.model.User;
import com.yaf.florabasket.service.CartService;
import com.yaf.florabasket.service.FlowerService;
import com.yaf.florabasket.service.OrdersService;
import com.yaf.florabasket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.List;

/**
 *
 * @date 11.05.2020
 */

@Controller
public class SellerController {

    private final FlowerService flowerService;
    private final UserService userService;
    private final OrdersService ordersService;
    private final CartService cartService;

    @Autowired
    public SellerController(FlowerService flowerService, UserService userService, OrdersService ordersService, CartService cartService) {
        this.flowerService = flowerService;
        this.userService = userService;
        this.ordersService = ordersService;
        this.cartService = cartService;
    }

    @RequestMapping(value = {"/flowerSeller"}, method = RequestMethod.GET)
    public ModelAndView flowerSellerIndex() {
        ModelAndView model = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        if (user != null) {
            model.addObject("username", user.getFirstname() + " " + (user.getSurname() != null ? user.getSurname() : (user.isSeller() ? "(Seller)" : "(Courier)")));
            model.addObject("signout", "Sign out");
        }
        model.addObject("flowers", flowerService.listAll());

        model.setViewName("flowerSeller");
        return model;
    }

    @RequestMapping(value = {"/release"})
    public ModelAndView release() {

        ModelAndView model = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        if (user != null) {
            model.addObject("username", user.getFirstname() + " " + (user.getSurname() != null ? user.getSurname() : (user.isSeller() ? "(Seller)" : "(Courier)")));
            model.addObject("signout", "Sign out");
        }

        Flower flowerToRelease = new Flower();
        List<Flower> flowerList = flowerService.listAll();
        List<Flower> sellingFlowerList = flowerService.listAllBySeller(user);
        flowerList.removeAll(sellingFlowerList);

        model.addObject("flowers", flowerList);
        model.addObject("flowerToRelease", flowerToRelease);
        model.addObject("sellingFlowers", sellingFlowerList);

        model.setViewName("release");
        return model;
    }

    @RequestMapping(value = {"/release"}, method = RequestMethod.POST)
    public String releaseItem(@Valid Flower flower) {
        if (flower.getId() != null) {
            flowerService.addNewSeller(flower);
        }
        return "redirect:/release";
    }


    @RequestMapping(value = {"/sellerOrder"}, method = RequestMethod.GET)
    public ModelAndView sellerOrder(@ModelAttribute("id") String orderId) {

        ModelAndView model = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        if (user != null) {
            model.addObject("username", user.getFirstname() + " " + (user.getSurname() != null ? user.getSurname() : (user.isSeller() ? "(Seller)" : "(Courier)")));
            model.addObject("signout", "Sign out");
        }

        User seller = userService.findByEmail(auth.getName());
        List<Orders> orderList = ordersService.findAllByPaymentCompletedAndSeller(seller);
        List<User> courierList = userService.listAllCouriers();
        Orders order = !orderId.isEmpty() ? ordersService.findById(Long.valueOf(orderId)) : new Orders();

        model.addObject("couriers", courierList);
        model.addObject("orders", orderList);
        model.addObject("user", seller);
        model.addObject("orderToAssign", order);
        model.setViewName("sellerOrder");
        return model;
    }

    @RequestMapping(value = {"/sellerOrder/{id}"}, method = RequestMethod.GET)
    public String sellerOrderItemSelected(RedirectAttributes redirectAttributes, @PathVariable("id") Long orderId) {
        redirectAttributes.addFlashAttribute("id", orderId);
        return "redirect:/sellerOrder";
    }

    @RequestMapping(value = {"/sellerOrder/assign/{id}"}, method = RequestMethod.POST)
    public String sellerOrderAssign(RedirectAttributes redirectAttributes, @Valid Orders orders, @PathVariable("id") Long orderId) {
        Orders order = ordersService.findById(orderId);
        order.setCourier(orders.getCourier());
        ordersService.updateOrderAssigned(order);
        redirectAttributes.addFlashAttribute("id", orders.getId());
        return "redirect:/sellerOrder";
    }

}
