package com.card_saver.service.impl;

import com.card_saver.model.Card;
import com.card_saver.model.User;
import com.card_saver.repository.CardRepository;
import com.card_saver.service.ICardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles services/logic that results in/require Cards.
 */
@Service
public class CardService implements ICardService {

    @Autowired
    CardRepository cardRepository;

    /**
     * Fetches a list of Cards that belong to the parameter User from the database.
     * It uses the parameter User's id to find the right Cards.
     *
     * @param user - The User whose Cards are to be fetched.
     * @return The list of Cards belonging to the parameter User.
     */
    @Override
    public List<Card> getAllCardsFromUser(User user) {
        List<Card> currentUsersCards = cardRepository.getAllCardsFromUser(user);

        return currentUsersCards;
    }

    /**
     * Inserts a row into the database's table "cards" with the values
     * of the parameter Card.
     *
     * @param card
     */
    @Override
    public void createCard(Card card) {
        cardRepository.saveCard(card);
    }

    /**
     * Creates a Card based on the parameter String using substrings to single out the Card's variables.
     * The Card is also saved to the database and will belong to the parameter User.
     *
     * @param cardString - The string that contains the Card's variables.
     * @param user - The user that the Card is linked to.
     */
    @Override
    public void createCardThroughString(String cardString, User user) {
        try {
            cardString = cardString.trim();

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

            String description = cardString.substring(0, cardString.indexOf(";")).trim();
            cardString = cardString.substring(cardString.indexOf(";") + 1).trim();

            String price = cardString.substring(0, cardString.indexOf(";")).trim();
            cardString = cardString.substring(cardString.indexOf(";") + 1).trim();

            Card card = new Card(0, name, set, grade, altered, manaCost, type, description, user.getId(), price);

            for (int i = 0; i < quantity; i++) {
                cardRepository.saveCard(card);
            }

            if (cardString.length() > 0) {
                createCardThroughString(cardString, user);
            }

        } catch (Exception e) {
            System.out.println("Could not create card through string: '" + cardString + "'. With exception: ");
            e.printStackTrace();
        }
    }

    /**
     * Returns a Card object representing the Card with the matching id
     * in the database.
     *
     * @param cardId - The id of the Card that is to be fetched.
     * @return The Card object representing the row in the database.
     */
    @Override
    public Card findById(int cardId) {
        Card card = cardRepository.findById(cardId);

        return card;
    }

    /**
     * Updates a Card in the database (a row in the Card table) to the
     * parameter Card's variables/values. It finds the right Card in the
     * database by using the parameter Card's id.
     *
     * @param card - The Card that contains the updated variables/values.
     */
    @Override
    public void updateCard(Card card) {
        cardRepository.updateCard(card);
    }

    /**
     * Returns a List of Cards whose parameters match the parameter Card's variables.
     * If none of the Cards in the List match, a empty List is returned.
     *
     * @param allCards   - The List of Cards that are to be filtered.
     * @param filterCard - The Card used as the filter to match Cards from the List.
     * @return The List of filtered Cards that matched the input Card.
     */
    @Override
    public List<Card> filterCards(List<Card> allCards, Card filterCard) {
        List<Card> filteredCards = new ArrayList<>();

        for (Card card : allCards) {
            if (card.getName().contains(filterCard.getName()) && card.getSet().contains(filterCard.getSet())
                    && card.getGrade().contains(filterCard.getGrade()) && card.getAltered().contains(filterCard.getAltered())
                    && card.getManaCost().contains(filterCard.getManaCost()) && card.getType().contains(filterCard.getType())
                    && card.getDescription().contains(filterCard.getDescription()) && card.getPrice().contains(filterCard.getPrice())) {
                filteredCards.add(card);
            }
        }
        return filteredCards;
    }

    /**
     * Returns the sum of all prices from Cards contained in the parameter List.
     * If the parameter List's Cards have no prices or the List contains no Cards,
     * the value of the return will be 0.
     *
     * @param cards - The List of Cards whose prices are to be summed up.
     * @return The total sum of prices.
     */
    @Override
    public int getSumOfAllPrices(List<Card> cards) {
        int sum = 0;

        for (Card card : cards) {
            if (card.getPrice() != null && card.getPrice().trim().length() > 0) {
                try {
                    sum += Integer.parseInt(card.getPrice());
                } catch (Exception e) {
                    System.out.println("Could not parse the value of card with price: " + card.getPrice() + ". Exception: " + e.toString());
                }
            }

        }

        return sum;
    }

    /**
     * Deletes the Card with the parameter id from the database.
     *
     * @param cardId - The id of the Card to be deleted
     */
    @Override
    public void deleteCard(int cardId) {
        cardRepository.deleteCard(cardId);
    }
}
