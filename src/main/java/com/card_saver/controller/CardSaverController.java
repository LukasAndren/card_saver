package com.card_saver.controller;

import com.card_saver.model.Card;
import com.card_saver.model.User;

import com.card_saver.service.ICardService;
import com.card_saver.service.IUserService;

import com.card_saver.parser.Parser;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * Handles request mapping to and from the "frontend".
 */
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

        //If the User's id is -1, the handleLogin method (above) has deemed the login to be faulty and an error should be shown
        if(currentUser.getId() == -1){
            model.addAttribute("usernameNotUnique", currentUser.getUsername());
            return "login";
        }

        return prepareNonSearchedHomePage(model);
    }

    @GetMapping(value = "/logout")
    public String logo(){
        currentUser = null;
        currentUsersCards = null;

        return "login";
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
            model.addAttribute("sumOfPrices", cardService.getSumOfAllPrices(filteredCards));
            model.addAttribute("numberOfCards", filteredCards.size());
        }

        return "home";
    }

    @GetMapping(value = "/cards/{cardId}/edit")
    public String showEditCard(Model model, @PathVariable int cardId){
        Card card = cardService.findById(cardId);

        model.addAttribute("card", card);
        model.addAttribute("allSetNames", Parser.getAllSetNames());

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
    public String showCreateCards(Model model){

        prepareCreateCardPage(model);

        return "createCard";
    }

    @PostMapping(value = "cards/create/form")
    public String createCardThroughForm(@ModelAttribute(name = "cardForm") Card card, Model model){
        card.setUserId(currentUser.getId());

        cardService.createCard(card);

        prepareCreateCardPage(model);

        return "createCard";
    }

    @PostMapping(value = "/cards/create/string")
    public String createCardsThroughText(@ModelAttribute(name = "cardString") String cardString, Model model){
        cardService.createCardThroughString(cardString, currentUser);

        prepareCreateCardPage(model);

        return "createCard";
    }

    /**
     * Prepares the Model attributes needed by Thymeleaf to display the home page properly,
     * and then returns the home page String.
     *
     * @param model - The Model being used by Thymeleaf.
     * @return "home" (the String representing the HTML home page).
     */
    public String prepareNonSearchedHomePage(Model model){
        currentUsersCards = cardService.getAllCardsFromUser(currentUser);

        model.addAttribute("currentUser", currentUser);
        model.addAttribute("currentUsersCards", currentUsersCards);
        if(currentUsersCards.size() > 0){
            model.addAttribute("sumOfPrices", cardService.getSumOfAllPrices(currentUsersCards));
            model.addAttribute("numberOfCards", currentUsersCards.size());
        }

        return "home";
    }

    /**
     * Prepares the Model attributes needed by Thymeleaf to display the home page properly,
     * and then returns the create card page String.
     *
     * @param model - The Model being used by Thymeleaf.
     * @return "createCard" (the String representing the HTML create card page).
     */
    public String prepareCreateCardPage(Model model){
        model.addAttribute("allSetNames", Parser.getAllSetNames());
        model.addAttribute("allCardNames", Parser.getAllCardNames());

        return "createCard";
    }
}
