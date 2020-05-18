package com.yaf.florabasket.controller;

import com.yaf.florabasket.model.*;
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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ardafakili
 * @date 11.05.2020
 */

@Controller
public class PublicController {

    private final FlowerService flowerService;
    private final UserService userService;
    private final OrdersService ordersService;
    private final CartService cartService;

    @Autowired
    public PublicController(FlowerService flowerService, UserService userService, OrdersService ordersService, CartService cartService) {
        this.flowerService = flowerService;
        this.userService = userService;
        this.ordersService = ordersService;
        this.cartService = cartService;
    }

    @RequestMapping(value = "/checkout")
    public String checkout() {
        return "checkout";
    }


    @RequestMapping(value = {"/shop"}, method = RequestMethod.GET)
    public ModelAndView shop() {
        ModelAndView model = new ModelAndView();
        model.addObject("flowers", flowerService.listAll());
        model.setViewName("/shop");
        return model;
    }


    @RequestMapping(value = "/product_details", method = RequestMethod.GET)
    public ModelAndView product(@ModelAttribute("flowerId") String flowerId) {

        ModelAndView modelAndView;
        try {
            modelAndView = new ModelAndView();
            Flower flowerExist = flowerService.findById(Long.valueOf(flowerId));
            modelAndView.addObject("flower", flowerExist);
            modelAndView.addObject("orders", new Orders());
        } catch (Exception exc) {
            return new ModelAndView("/home");
        }
        return modelAndView;
    }

    @RequestMapping(value = {"/cart"}, method = RequestMethod.GET)
    public ModelAndView cart() {
        ModelAndView model = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());

        Cart cart = cartService.findByUser(user);
        List<Orders> ordersList = ordersService.findAllByCart(cart);
        List<Flower> flowerList = new ArrayList<>();

        for (Orders orders : ordersList) {
            flowerList.add(orders.getFlower());
        }

        model.addObject("flowers", flowerList);
        model.addObject("user", user);
        model.addObject("orders", Arrays.asList(cart.getOrders()));

        model.setViewName("/cart");
        return model;
    }

    @RequestMapping(value = {"/product_details/save/{flowerId}"}, method = RequestMethod.POST)
    public String addOrderToCart(@Valid Orders orders, @PathVariable("flowerId") Long flowerId) {
        orders.setFlower(flowerService.findById(flowerId));
        ordersService.createOrder(orders);
        return "redirect:/cart";
    }



    @RequestMapping(value = {"/product_details/{flowerId}"}, method = RequestMethod.GET)
    public String productDetails(RedirectAttributes redirectAttributes, @PathVariable("flowerId") Long flowerId) {
        redirectAttributes.addFlashAttribute("flowerId", flowerId);
        return "redirect:/product_details";
    }

}
