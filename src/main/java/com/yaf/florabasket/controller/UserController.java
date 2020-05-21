package com.yaf.florabasket.controller;

import com.yaf.florabasket.model.User;
import com.yaf.florabasket.security.Roles;
import com.yaf.florabasket.service.FlowerService;
import com.yaf.florabasket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;

/**
 *
 * @date 15.05.2020
 */

@Controller
public class UserController {

    private final UserService userService;
    private final FlowerService flowerService;

    @Autowired
    public UserController(UserService userService, FlowerService flowerService) {
        this.userService = userService;
        this.flowerService = flowerService;
    }


    @RequestMapping(value = {"/login"}, method = RequestMethod.GET)
    public ModelAndView login() {
        ModelAndView model = new ModelAndView();
        model.addObject("userForMail", new User());
        model.setViewName("user/login");
        return model;
    }

    @RequestMapping(value = {"/signup"}, method = RequestMethod.GET)
    public ModelAndView signupClient() {
        ModelAndView model = new ModelAndView();
        User user = new User();
        model.addObject("user", user);
        model.addObject("userForMail", new User());
        model.setViewName("user/signup");
        return model;
    }

    @RequestMapping(value = {"/signup_seller"}, method = RequestMethod.GET)
    public ModelAndView signupSeller() {
        ModelAndView model = new ModelAndView();
        User user = new User();
        user.setSeller(true);
        model.addObject("user", user);
        model.addObject("userForMail", new User());
        model.setViewName("user/signup_seller");
        return model;
    }

    @RequestMapping(value = {"/signup"}, method = RequestMethod.POST)
    public ModelAndView createClientUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView model = new ModelAndView();
        User userExist = userService.findByEmail(user.getEmail());

        if (userExist != null) {
            bindingResult.rejectValue("email", "error.user", "E-mail already exist.");
        }

        if (!bindingResult.hasErrors()) {
            userService.saveClientUser(user);
            model.addObject("msg", "User has been registered successfully.");
            model.addObject("user", new User());
        }
        model.setViewName("user/signup");
        model.addObject("userForMail", new User());

        return model;
    }

    @RequestMapping(value = {"/signup_seller"}, method = RequestMethod.POST)
    public ModelAndView createSellerUser(@Valid User user, BindingResult bindingResult) {
        ModelAndView model = new ModelAndView();
        User userExist = userService.findByEmail(user.getEmail());

        if (userExist != null) {
            bindingResult.rejectValue("email", "error.user", "E-mail already exist.");
        }

        if (!bindingResult.hasErrors()) {
            if (user.isSeller()) {
                userService.saveSellerUser(user);
            } else {
                userService.saveCourierUser(user);
            }
            model.addObject("msg", "Application form has been sent successfully.");
            model.addObject("user", new User());
        }

        model.addObject("userForMail", new User());
        model.setViewName("user/signup_seller");
        model.addObject("userForMail", new User());

        return model;
    }

    @RequestMapping(value = {"/home", "/"}, method = RequestMethod.GET)
    public ModelAndView home() {

        String viewName = "home";
        ModelAndView model = new ModelAndView();
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findByEmail(auth.getName());
        if (user != null) {
            model.addObject("username", user.getFirstname() + " " + (user.getSurname() != null ? user.getSurname() : (user.isSeller() ? "(Seller)" : "(Courier)")));
            model.addObject("signout", "Sign out");

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication
                    .getAuthorities()
                    .stream()
                    .anyMatch(r -> r.getAuthority().equals(Roles.SELLER.toString()))) {
                viewName = "flowerSeller";
            } else if (authentication
                    .getAuthorities()
                    .stream()
                    .anyMatch(r -> r.getAuthority().equals(Roles.COURIER.toString()))){
                viewName = "redirect:/c_order_list";
            }
        }

        model.addObject("userForMail", new User());
        model.addObject("flowers", flowerService.listAll());
        model.setViewName(viewName);

        return model;
    }

    @RequestMapping(value = {"/access_denied"}, method = RequestMethod.GET)
    public String accessDenied() {
        return "errors/access_denied";
    }


}
