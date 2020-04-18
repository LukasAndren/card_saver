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
        currentUser = userService.handleLogin(user);

        if(currentUser.getId() == -1){
            model.addAttribute("usernameNotUnique", currentUser.getUsername());
            return "login";
        }

        return prepareNonSearchedHomePage(model);
    }

    @GetMapping(value = "/home")
    public String showHome(Model model){
        return prepareNonSearchedHomePage(model);
    }

    @PostMapping(value = "/cards/search")
    public String searchForCards(@ModelAttribute(name = "cardForm") Card card, Model model){

        List<Card> filteredCards = cardService.filterCards(currentUsersCards, card);

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("currentUsersCards", filteredCards);
        if(filteredCards.size() > 0){
            model.addAttribute("sumOfPrices", cardService.getTotalPrice(filteredCards));
            model.addAttribute("numberOfCards", filteredCards.size());
        }

        return "home";
    }

    @GetMapping(value = "/cards/{cardId}/edit")
    public String showEditCard(Model model, @PathVariable int cardId){
        Card card = cardService.findById(cardId);

        model.addAttribute("card", card);
        //model.addAttribute("cardImageSource", cardService.decipherImageSource(card));

        return "editCard";
    }

    @PostMapping(value = "/cards/{cardId}/edit")
    public String updateCard(Model model, @PathVariable int cardId, @ModelAttribute("card") Card card){
        card.setId(cardId);

        cardService.updateCard(card);

        return prepareNonSearchedHomePage(model);
    }

    @GetMapping(value = "/cards/{cardId}/delete")
    public String deleteCard(Model model, @PathVariable int cardId){

        cardService.deleteCard(cardId);

        return prepareNonSearchedHomePage(model);
    }

    @GetMapping(value = "/cards/create")
    public String showCreateCards(){
        return "createCard";
    }

    @PostMapping(value = "cards//create/form")
    public String createCardThroughForm(@ModelAttribute(name = "cardForm") Card card, Model model){
        card.setUserId(currentUser.getId());

        cardService.createCard(card);

        return "createCard";
    }

    @PostMapping(value = "/cards/create/string")
    public String createCardsThroughText(@ModelAttribute(name = "cardString") String cardString, Model model){
        cardService.createCardThroughString(cardString, currentUser);

        return "createCard";
    }

    /**
     * Prepares the Model attributes needed by Thymeleaf to display the home page properly,
     * and then returns the home page String.
     * @param model - The Model being used by Thymeleaf
     * @return "home", the String representing the HTML home page.
     */
    public String prepareNonSearchedHomePage(Model model){
        currentUsersCards = cardService.getAllCardsFromUser(currentUser);

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("currentUsersCards", currentUsersCards);
        if(currentUsersCards.size() > 0){
            model.addAttribute("sumOfPrices", cardService.getTotalPrice(currentUsersCards));
            model.addAttribute("numberOfCards", currentUsersCards.size());
        }

        return "home";
    }
}
