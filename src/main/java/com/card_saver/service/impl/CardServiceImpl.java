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
    public void createCard(Card card, User user) {
        cardRepository.createCard(card, user);
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
