package com.card_saver.controller;

import com.card_saver.model.Card;
import com.card_saver.model.User;
import com.card_saver.service.ICardService;
import com.card_saver.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class CardSaverController {

    @Autowired
    IUserService userService;
    @Autowired
    ICardService cardService;

    private User currentUser;
    private List<Card> currentUsersCards;

    @GetMapping(value = "/")
    public String redirectToLogin() {
        return "login";
    }

    @GetMapping(value = "/login")
    public String showLogin() {
        return "login";
    }

    @PostMapping(value = "/login")
    public String login(@ModelAttribute(name = "loginForm") User user, Model model) {

        String username = user.getUsername();
        String password = user.getPassword();

        if (username.equals("admin") && password.equals("admin")) {
            return "home";
        }

        currentUser = userService.handleLogin(user);
        currentUsersCards = cardService.getAllUsersCards(currentUser);

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("currentUsersCards", currentUsersCards);

        return "home";

    }

    @GetMapping(value = "/home")
    public String showHome(Model model){
        currentUsersCards = cardService.getAllUsersCards(currentUser);

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("currentUsersCards", currentUsersCards);
        return "home";
    }

    @GetMapping(value = "/cards/create")
    public String showCreateCards(){
        return "addCards";
    }

    @PostMapping(value = "/cards/create")
    public String createCards(@ModelAttribute(name = "cardForm") Card card, Model model){

        cardService.createCard(card, currentUser);

        return "addCards";
    }

    @GetMapping(value = "/cards/{cardId}/edit")
    public String showEditCard(Model model, @PathVariable int cardId){
        Card card = cardService.findById(cardId);

        model.addAttribute("card", card);

        return "editCard";
    }

    @PostMapping(value = "/cards/{cardId}/edit")
    public String updateCard(Model model, @PathVariable int cardId, @ModelAttribute("card") Card card){
        card.setId(cardId);

        cardService.updateCard(card);

        currentUsersCards = cardService.getAllUsersCards(currentUser);
        model.addAttribute("currentUsersCards", currentUsersCards);

        return "cards";
    }

    @PostMapping(value = "/cards/search")
    public String searchForCards(@ModelAttribute(name = "cardForm") Card card, Model model){

        List<Card> filteredCards = cardService.filterCards(currentUsersCards, card);

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("currentUsersCards", filteredCards);
        return "home";
    }
}
