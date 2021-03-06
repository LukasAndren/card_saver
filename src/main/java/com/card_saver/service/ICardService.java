package com.card_saver.service;

import com.card_saver.model.Card;
import com.card_saver.model.User;

import java.util.List;

public interface ICardService {
    List<Card> getAllCardsFromUser(User user);
    void createCard(Card card);
    void createCardThroughString(String cardString, User user);
    Card findById(int cardId);
    void updateCard(Card card);
    List<Card> filterCards(List<Card> allCards, Card filterCard);
    int getSumOfAllPrices(List<Card> cards);
    void deleteCard(int cardId);
    void decipherImageSource(Card card);
    List<Card> sortCards(List<Card> unsortedCards);
}
