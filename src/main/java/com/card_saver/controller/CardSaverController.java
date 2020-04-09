package com.card_saver.controller;

import com.card_saver.model.Card;
import com.card_saver.model.User;
import com.card_saver.service.ICardService;
import com.card_saver.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class CardSaverController {

    @Autowired
    IUserService userService;
    @Autowired
    ICardService cardService;

    private User currentUser;

    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public String getLoginForm() {
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@ModelAttribute(name = "loginForm") User user, Model model) {

        String username = user.getUsername();
        String password = user.getPassword();

        if (username.equals("admin") && password.equals("admin")) {
            return "home";
        }

        currentUser = userService.handleLogin(user);
        model.addAttribute("currentUser", currentUser);

        return "home";

    }

    @RequestMapping(value = "/cards/add", method = RequestMethod.GET)
    public String getAddCardsForm(){
        return "addCards";
    }

    @RequestMapping(value = "/cards/add", method = RequestMethod.POST)
    public String addCards(@ModelAttribute(name = "cardForm") Card card, Model model){

        cardService.createCard(card, currentUser);


        return "addCards";
    }
}
