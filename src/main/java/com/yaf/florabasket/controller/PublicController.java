package com.yaf.florabasket.controller;

import com.yaf.florabasket.model.Flower;
import com.yaf.florabasket.model.User;
import com.yaf.florabasket.model.flower.FilterProperties;
import com.yaf.florabasket.model.flower.FlowerCategory;
import com.yaf.florabasket.model.flower.FlowerColor;
import com.yaf.florabasket.model.flower.FlowerName;
import com.yaf.florabasket.service.CartService;
import com.yaf.florabasket.service.FlowerService;
import com.yaf.florabasket.service.OrdersService;
import com.yaf.florabasket.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.ArrayList;
import java.util.List;

/**
 *
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


    @RequestMapping(value = {"/shop"}, method = RequestMethod.GET)
    public ModelAndView shop(@ModelAttribute("value") String value, @ModelAttribute("filter") String filter) {

        ModelAndView model = new ModelAndView();
        List<Flower> flowerList = new ArrayList<>();
        String activeElement = "";

        if (filter.isEmpty()) {

            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            User user = userService.findByEmail(auth.getName());
            if (user != null) {
                model.addObject("username", user.getFirstname() + " " + (user.getSurname() != null ? user.getSurname() : (user.isSeller() ? "(Seller)" : "(Courier)")));
                model.addObject("signout", "Sign out");
            }
            flowerList = flowerService.listAll();

        } else {
            switch (filter) {
                case FilterProperties.COLOR:
                    flowerList = flowerService.findAllByColor(value);
                    break;
                case FilterProperties.CATEGORY:
                    flowerList = flowerService.findAllByCategory(value);
                    break;
                case FilterProperties.NAME:
                    flowerList = flowerService.findAllByName(value);
                    break;
            }
            activeElement = value;
        }

        model.addObject("activeElement", activeElement);
        model.addObject("flowers", flowerList);
        model.setViewName("shop");

        return model;
    }

    @RequestMapping(value = {"/shop/filter/white"}, method = RequestMethod.GET)
    public String shopFilterWhite(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("filter", FilterProperties.COLOR);
        redirectAttributes.addFlashAttribute("value", FlowerColor.WHITE.getText());
        return "redirect:/shop";
    }

    @RequestMapping(value = {"/shop/filter/gray"}, method = RequestMethod.GET)
    public String shopFilterGray(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("filter", FilterProperties.COLOR);
        redirectAttributes.addFlashAttribute("value", FlowerColor.GRAY.getText());
        return "redirect:/shop";
    }

    @RequestMapping(value = {"/shop/filter/black"}, method = RequestMethod.GET)
    public String shopFilterBlack(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("filter", FilterProperties.COLOR);
        redirectAttributes.addFlashAttribute("value", FlowerColor.BLACK.getText());
        return "redirect:/shop";
    }

    @RequestMapping(value = {"/shop/filter/blue"}, method = RequestMethod.GET)
    public String shopFilterBlue(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("filter", FilterProperties.COLOR);
        redirectAttributes.addFlashAttribute("value", FlowerColor.BLUE.getText());
        return "redirect:/shop";
    }

    @RequestMapping(value = {"/shop/filter/red"}, method = RequestMethod.GET)
    public String shopFilterRed(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("filter", FilterProperties.COLOR);
        redirectAttributes.addFlashAttribute("value", FlowerColor.RED.getText());
        return "redirect:/shop";
    }

    @RequestMapping(value = {"/shop/filter/yellow"}, method = RequestMethod.GET)
    public String shopFilterYellow(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("filter", FilterProperties.COLOR);
        redirectAttributes.addFlashAttribute("value", FlowerColor.YELLOW.getText());
        return "redirect:/shop";
    }

    @RequestMapping(value = {"/shop/filter/orange"}, method = RequestMethod.GET)
    public String shopFilterOrange(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("filter", FilterProperties.COLOR);
        redirectAttributes.addFlashAttribute("value", FlowerColor.ORANGE.getText());
        return "redirect:/shop";
    }

    @RequestMapping(value = {"/shop/filter/brown"}, method = RequestMethod.GET)
    public String shopFilterBrown(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("filter", FilterProperties.COLOR);
        redirectAttributes.addFlashAttribute("value", FlowerColor.BROWN.getText());
        return "redirect:/shop";
    }

    @RequestMapping(value = {"/shop/filter/bonsai"}, method = RequestMethod.GET)
    public String shopFilterBonsai(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("filter", FilterProperties.CATEGORY);
        redirectAttributes.addFlashAttribute("value", FlowerCategory.BONSAI.getText());
        return "redirect:/shop";
    }

    @RequestMapping(value = {"/shop/filter/bouqet"}, method = RequestMethod.GET)
    public String shopFilterBouqet(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("filter", FilterProperties.CATEGORY);
        redirectAttributes.addFlashAttribute("value", FlowerCategory.BOUQUET.getText());
        return "redirect:/shop";
    }

    @RequestMapping(value = {"/shop/filter/giftbox"}, method = RequestMethod.GET)
    public String shopFilterGiftbox(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("filter", FilterProperties.CATEGORY);
        redirectAttributes.addFlashAttribute("value", FlowerCategory.GIFT_BOX.getText());
        return "redirect:/shop";
    }

    @RequestMapping(value = {"/shop/filter/homedeco"}, method = RequestMethod.GET)
    public String shopFilterHomeDeco(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("filter", FilterProperties.CATEGORY);
        redirectAttributes.addFlashAttribute("value", FlowerCategory.HOME_DECO.getText());
        return "redirect:/shop";
    }

    @RequestMapping(value = {"/shop/filter/plantswithpot"}, method = RequestMethod.GET)
    public String shopFilterPlantsWithPot(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("filter", FilterProperties.CATEGORY);
        redirectAttributes.addFlashAttribute("value", FlowerCategory.PLANTS_WITH_POT.getText());
        return "redirect:/shop";
    }

    @RequestMapping(value = {"/shop/filter/orkid"}, method = RequestMethod.GET)
    public String shopFilterOrkid(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("filter", FilterProperties.NAME);
        redirectAttributes.addFlashAttribute("value", FlowerName.ORKID.getText());
        return "redirect:/shop";
    }

    @RequestMapping(value = {"/shop/filter/acer"}, method = RequestMethod.GET)
    public String shopFilterAcer(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("filter", FilterProperties.NAME);
        redirectAttributes.addFlashAttribute("value", FlowerName.ACER_BOMSAI.getText());
        return "redirect:/shop";
    }

    @RequestMapping(value = {"/shop/filter/krizantem"}, method = RequestMethod.GET)
    public String shopFilterKrizantem(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("filter", FilterProperties.NAME);
        redirectAttributes.addFlashAttribute("value", FlowerName.KRIZANTEM.getText());
        return "redirect:/shop";
    }


    @RequestMapping(value = {"/shop/filter/daisy"}, method = RequestMethod.GET)
    public String shopFilterDaisy(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("filter", FilterProperties.NAME);
        redirectAttributes.addFlashAttribute("value", FlowerName.DAISY.getText());
        return "redirect:/shop";
    }

    @RequestMapping(value = {"/shop/filter/guzmania"}, method = RequestMethod.GET)
    public String shopFilterGuzmania(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("filter", FilterProperties.NAME);
        redirectAttributes.addFlashAttribute("value", FlowerName.GUZMANIA.getText());
        return "redirect:/shop";
    }

}