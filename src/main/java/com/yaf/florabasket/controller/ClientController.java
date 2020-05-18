package com.yaf.florabasket.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author ardafakili
 * @date 11.05.2020
 */

@Controller
public class ClientController {

    @RequestMapping(value = {"/order"})
    public String index() {
        return "/order";
    }

    @RequestMapping(value = "/cart")
    public String cart() {
        return "cart";
    }

}
