package com.card_saver.service;

import com.card_saver.model.Card;
import com.card_saver.model.User;

import java.util.List;

public interface ICardService {
    public List<Card> getAllUsersCards(User user);
    public void createCard(Card card, User user);
    public Card findById(int cardId);
    public void updateCard(Card card);
    public List<Card> filterCards(List<Card> allCards, Card filterCard);
}
