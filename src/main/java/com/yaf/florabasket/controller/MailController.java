package com.yaf.florabasket.controller;

import com.yaf.florabasket.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @date 21.05.2020
 */

@Controller
public class MailController {

    @Autowired
    private MailService mailService;


    @RequestMapping(value = {"/sendmail"}, method = RequestMethod.POST)
    private String sendMail(HttpServletRequest request, @RequestParam(name = "email") String email) {
        String covere = "Congratulations! You have %25 discount on your account!";

        try {
            mailService.sendMail(covere, email);
            return "redirect:/home";
        } catch (MailException e) {
            e.printStackTrace();
        }
        return "redirect:/home";
    }
}
