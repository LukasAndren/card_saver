package com.card_saver.service.impl;

import com.card_saver.model.Card;
import com.card_saver.model.User;
import com.card_saver.repository.CardRepository;
import com.card_saver.service.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CardServiceImpl implements ICardService {

    @Autowired
    CardRepository cardRepository;

    @Override
    public List<Card> getAllUsersCards(User user) {
        return null;
    }

    @Override
    public void createCard(Card card, User user) {
        cardRepository.createCard(card, user);
    }
}
