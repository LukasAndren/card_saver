package com.card_saver.service.impl;

import com.card_saver.model.Card;
import com.card_saver.model.User;
import com.card_saver.repository.CardRepository;
import com.card_saver.service.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CardServiceImpl implements ICardService {

    @Autowired
    CardRepository cardRepository;

    private List<Card> currentUsersCards;

    @Override
    public List<Card> getAllUsersCards(User user) {
        currentUsersCards = cardRepository.getCurrentUsersCards(user);

        return currentUsersCards;
    }

    @Override
    public void createCardThroughForm(Card card, User user) {
        cardRepository.createCard(card, user);
    }

    @Override
    public Boolean createCardThroughString(String cardString, User user){
        Boolean success = true;

        cardString = cardString.trim();

        try{
            int quantity = Integer.parseInt(cardString.substring(0, cardString.indexOf(";")).trim());
            cardString = cardString.substring(cardString.indexOf(";") + 1).trim();

            String name = cardString.substring(0, cardString.indexOf(";")).trim();
            cardString = cardString.substring(cardString.indexOf(";") + 1).trim();

            String set = cardString.substring(0, cardString.indexOf(";")).trim();
            cardString = cardString.substring(cardString.indexOf(";") + 1).trim();

            String grade = cardString.substring(0, cardString.indexOf(";")).trim();
            cardString = cardString.substring(cardString.indexOf(";") + 1).trim();

            String altered = cardString.substring(0, cardString.indexOf(";")).trim();
            cardString = cardString.substring(cardString.indexOf(";") + 1).trim();

            String manaCost = cardString.substring(0, cardString.indexOf(";")).trim();
            cardString = cardString.substring(cardString.indexOf(";") + 1).trim();

            String type = cardString.substring(0, cardString.indexOf(";")).trim();
            cardString = cardString.substring(cardString.indexOf(";") + 1).trim();

            String description = cardString.trim();

            Card card = new Card(name, set, grade, altered, manaCost, type, description, user.getId());

            for(int i = 0; i < quantity; i++){
                cardRepository.createCard(card, user);
            }

        } catch (Exception e) {
            success = false;
            System.out.println("Could not create card through string: " + cardString + " Exception: " + e.toString());
        }

    return success;
    }

    @Override
    public Card findById(int cardId) {
        return cardRepository.findById(cardId);
    }

    @Override
    public void updateCard(Card card) {
        cardRepository.updateCard(card);
    }

    @Override
    public List<Card> filterCards(List<Card> allCards, Card filterCard) {
        List<Card> filteredCards = new ArrayList<>();

        for (Card card : allCards) {
            if(card.getName().contains(filterCard.getName()) && card.getSet().contains(filterCard.getSet())
            && card.getGrade().contains(filterCard.getGrade()) && card.getAltered().contains(filterCard.getAltered())
            && card.getManaCost().contains(filterCard.getManaCost()) && card.getType().contains(filterCard.getType())
            && card.getDescription().contains(filterCard.getDescription())){
                filteredCards.add(card);
            }
        }
        return filteredCards;
    }
}
