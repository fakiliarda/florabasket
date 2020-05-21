package com.yaf.florabasket.controller;

import com.yaf.florabasket.model.Cart;
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
public class ClientController {

    private final FlowerService flowerService;
    private final UserService userService;
    private final OrdersService ordersService;
    private final CartService cartService;

    @Autowired
    public ClientController(FlowerService flowerService, UserService userService, OrdersService ordersService, CartService cartService) {
        this.flowerService = flowerService;
        this.userService = userService;
        this.ordersService = ordersService;
        this.cartService = cartService;
    }

    @RequestMapping(value = {"/order"}, method = RequestMethod.GET)
    public ModelAndView order(@ModelAttribute("id") String orderId) {

        ModelAndView model = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        if (user != null) {
            model.addObject("username", user.getFirstname() + " " + (user.getSurname() != null ? user.getSurname() : (user.isSeller() ? "(Seller)" : "(Courier)")));
            model.addObject("signout", "Sign out");
        }
        List<Orders> orderList = ordersService.findAllByPaymentCompletedAndClient(user);

        Orders orderInfo = !orderId.isEmpty() ? ordersService.findById(Long.valueOf(orderId)) : new Orders();


        model.addObject("orders", orderList);
        model.addObject("user", user);
        model.addObject("orderInfo", orderInfo);
        model.addObject("userForMail", new User());

        model.setViewName("order");
        return model;
    }

    @RequestMapping(value = {"/order/{id}"}, method = RequestMethod.GET)
    public String orderItemSelected(RedirectAttributes redirectAttributes, @PathVariable( "id") Long orderId) {
        redirectAttributes.addFlashAttribute("id", orderId);
        return "redirect:/order";
    }

    @RequestMapping(value = {"/cart"}, method = RequestMethod.GET)
    public ModelAndView cart() {
        ModelAndView model = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        if (user != null) {
            model.addObject("username", user.getFirstname() + " " + (user.getSurname() != null ? user.getSurname() : (user.isSeller() ? "(Seller)" : "(Courier)")));
            model.addObject("signout", "Sign out");
        }

        Cart cart = cartService.findByUser(user);
        List<Orders> ordersList = ordersService.findAllByCartAndPaymentCompletedFalse(cart);

        Integer total = 0;
        for (Orders order: ordersList ) {
            total += (order.getQuantity() * Integer.parseInt(order.getFlower().getPrice().substring(1)));
        }
        cart.setTotal(total);

        model.addObject("user", user);
        model.addObject("orders", ordersList);
        model.addObject("cart", cart);
        model.addObject("userForMail", new User());

        model.setViewName("cart");
        return model;
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.GET)
    public ModelAndView checkout() {

        ModelAndView model = new ModelAndView();

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        if (user != null) {
            model.addObject("username", user.getFirstname() + " " + (user.getSurname() != null ? user.getSurname() : (user.isSeller() ? "(Seller)" : "(Courier)")));
            model.addObject("signout", "Sign out");
        }
        model.addObject("cart", cartService.getCartWithTotalPrice());
        model.addObject("userForMail", new User());

        model.setViewName("checkout");
        return model;
    }

    @RequestMapping(value = "/checkout", method = RequestMethod.POST)
    public ModelAndView checkedOut() {
        ModelAndView model = new ModelAndView();
        ordersService.updateOrdersPaymentCompletedAndDeliveryStatus();
        model.setViewName("redirect:/order");
        return model;
    }

    @RequestMapping(value = {"/product_details/save/{flowerId}"}, method = RequestMethod.POST)
    public String addOrderToCart(@Valid Orders orders, @PathVariable("flowerId") Long flowerId) {
        orders.setFlower(flowerService.findById(flowerId));
        ordersService.createOrder(orders);
        return "redirect:/cart";
    }

    @RequestMapping(value = {"/product_details/{flowerId}"}, method = RequestMethod.GET)
    public String productDetails(RedirectAttributes redirectAttributes, @PathVariable( "flowerId") Long flowerId) {
        redirectAttributes.addFlashAttribute("flowerId", flowerId);
        return "redirect:/product_details";
    }

    @RequestMapping(value = "/product_details", method = RequestMethod.GET)
    public ModelAndView product(@ModelAttribute("flowerId") String flowerId) {

        ModelAndView modelAndView;
        try {
            modelAndView = new ModelAndView();

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findByEmail(auth.getName());
            if (user != null) {
                modelAndView.addObject("username", user.getFirstname() + " " + (user.getSurname() != null ? user.getSurname() : (user.isSeller() ? "(Seller)" : "(Courier)")));
                modelAndView.addObject("signout", "Sign out");
            }

            Flower flowerExist = flowerService.findById(Long.valueOf(flowerId));
            List<User> sellers = userService.listAllSellersByFlower(flowerExist);


            Orders orders = new Orders();
            orders.setQuantity(1);

            modelAndView.addObject("flower", flowerExist);
            modelAndView.addObject("orders", orders);
            modelAndView.addObject("sellers", sellers);
            modelAndView.addObject("user", user);
            modelAndView.addObject("userForMail", new User());

        } catch (Exception exc) {
            return new ModelAndView("home");
        }
        return modelAndView;
    }

}
