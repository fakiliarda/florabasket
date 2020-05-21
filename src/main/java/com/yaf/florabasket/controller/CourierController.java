package com.yaf.florabasket.controller;

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
 * @date 20.05.2020
 */

@Controller
public class CourierController {

    private final FlowerService flowerService;
    private final UserService userService;
    private final OrdersService ordersService;
    private final CartService cartService;

    @Autowired
    public CourierController(FlowerService flowerService, UserService userService, OrdersService ordersService, CartService cartService) {
        this.flowerService = flowerService;
        this.userService = userService;
        this.ordersService = ordersService;
        this.cartService = cartService;
    }

    @RequestMapping(value = {"/c_order_list"}, method = RequestMethod.GET)
    public ModelAndView courierAssignedOrder(@ModelAttribute("id") String orderId) {

        ModelAndView model = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        if (user != null) {
            model.addObject("username", user.getFirstname() + " " + (user.getSurname() != null ? user.getSurname() : (user.isSeller() ? "(Seller)" : "(Courier)")));
            model.addObject("signout", "Sign out");
        }

        List<Orders> orderList = ordersService.findAllByCourier(user);
        Orders order = !orderId.isEmpty() ? ordersService.findById(Long.valueOf(orderId)) : new Orders();

//        boolean delivered = false;
//        if (order != null && order.getDeliveryStatus() != null) {
//            delivered = order.getDeliveryStatus().equals(DeliveryStatus.DELIVERED.toString());
//        }
//        model.addObject("delivered", delivered);
        model.addObject("orders", orderList);
        model.addObject("orderToResponse", order);
        model.setViewName("c_order_list");
        return model;
    }

    @RequestMapping(value = {"/c_order_list/{id}"}, method = RequestMethod.GET)
    public String courierOrderItemSelected(RedirectAttributes redirectAttributes, @PathVariable( "id") Long orderId) {
        redirectAttributes.addFlashAttribute("id", orderId);
        return "redirect:/c_order_list";
    }

    @RequestMapping(value = {"/c_order_list/response/{id}"}, method = RequestMethod.POST, params="action=REJECTED")
    public String courierResponseReject(RedirectAttributes redirectAttributes, @Valid Orders orders, @PathVariable( "id") Long orderId) {

        Orders order = ordersService.findById(orderId);
        ordersService.updateOrderRejected(order);
        redirectAttributes.addFlashAttribute("id", orders.getId());
        System.out.println("hello");

        return "redirect:/c_order_list";
    }

    @RequestMapping(value = {"/c_order_list/response/{id}"}, method = RequestMethod.POST, params="action=ACCEPTED")
    public String courierResponseAccept(RedirectAttributes redirectAttributes, @Valid Orders orders, @PathVariable( "id") Long orderId) {

        Orders order = ordersService.findById(orderId);
        ordersService.updateOrderAccepted(order);
        redirectAttributes.addFlashAttribute("id", orders.getId());
        System.out.println("hello");

        return "redirect:/c_order_list";
    }

    @RequestMapping(value = {"/c_order_list/response/{id}"}, method = RequestMethod.POST, params="action=DELIVERED")
    public String courierResponseDelivered(RedirectAttributes redirectAttributes, @Valid Orders orders, @PathVariable( "id") Long orderId) {

        Orders order = ordersService.findById(orderId);
        ordersService.updateOrderDelivered(order);
        redirectAttributes.addFlashAttribute("id", orders.getId());
        System.out.println("hello");

        return "redirect:/c_order_list";
    }


}
