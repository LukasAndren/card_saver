package com.card_saver;

import com.card_saver.model.Card;
import com.card_saver.model.User;
import com.card_saver.repository.CardRepository;
import com.card_saver.service.impl.CardService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.Assert.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.verify;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;


public class CardServiceTest {

    @InjectMocks
    CardService cardService;

    @Mock
    CardRepository cardRepository;

    @Mock
    JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testCreateCardThroughString(){
        String cardString = "1; TestName; TestSet; TestGrade; TestAltered; TestManaCost; TestType; TestDescription; TestPrice;";

        User user = new User();
        user.setId(1);
        user.setUsername("TestUsername");
        user.setPassword("TestPassword");

        ArgumentCaptor<Card> argument = ArgumentCaptor.forClass(Card.class);

        cardService.createCardThroughString(cardString, user);

        verify(cardRepository).saveCard(argument.capture());
        assertEquals(argument.getValue().getName(), "TestName");
        assertEquals(argument.getValue().getSet(), "TestSet");
        assertEquals(argument.getValue().getGrade(), "TestGrade");
        assertEquals(argument.getValue().getAltered(), "TestAltered");
        assertEquals(argument.getValue().getManaCost(), "TestManaCost");
        assertEquals(argument.getValue().getType(), "TestType");
        assertEquals(argument.getValue().getDescription(), "TestDescription");
        assertEquals(argument.getValue().getPrice(), "TestPrice");
    }

    @Test
    void testDecipherImageSource(){
        Card cardA = new Card(1, "Scryb Sprites", "", "", "", "", "", "", 1, "");
        Card cardB = new Card(1, "TestName", "Alpha", "", "", "", "", "", 1, "");

        cardService.decipherImageSource(cardA);
        cardService.decipherImageSource(cardB);

        assertTrue(cardA.getImageSource().endsWith("scrybsprites.jpg"));
        assertTrue(cardB.getImageSource().endsWith("cardback.jpg"));

    }

    @Test
    void testFilterCards(){
        Card filterCard = new Card(1, "matchName", "matchSet", "matchGrade", "matchAltered",
                "matchManaCost", "matchType", "matchDescription", 1, "100");

        Card matchCard = new Card(1, "matchName", "matchSet", "matchGrade", "matchAltered",
                "matchManaCost", "matchType", "matchDescription", 1, "100");

        Card nonMatchCard = new Card(2, "nonMatchName", "nonMatchSet", "nonMatchGrade", "nonMatchAltered",
                "nonMatchManaCost", "nonMatchType", "nonMatchDescription", 1, "50");

        List<Card> allCards = new ArrayList<>();
        allCards.add(matchCard);
        allCards.add(nonMatchCard);

        List<Card> filteredCards = cardService.filterCards(allCards, filterCard);

        assertTrue(filteredCards.contains(matchCard));
        assertFalse(filteredCards.contains(nonMatchCard));
    }

    @Test
    void testGetSumOfAllPrices(){
        Card cardOne = new Card(1, "Name", "Set", "Grade", "Altered",
                "Mana cost", "Type", "Description", 1, "300");

        Card cardTwo = new Card(2, "Name", "Set", "Grade", "Altered",
                "Mana cost", "Type", "Description", 1, "275");

        Card cardThree = new Card(3, "Name", "Set", "Grade", "Altered",
                "Mana cost", "Type", "Description", 1, "120");

        List<Card> allCards = new ArrayList<>();
        allCards.add(cardOne);
        allCards.add(cardTwo);
        allCards.add(cardThree);

        int sum = Integer.parseInt(cardOne.getPrice()) + Integer.parseInt(cardTwo.getPrice()) + Integer.parseInt(cardThree.getPrice());

        assertEquals(sum, cardService.getSumOfAllPrices(allCards));
    }

    @Test
    void testSortCards(){
        Card cardA = new Card(1, "Aaa", "Alpha", "", "", "", "", "", 1, "");
        Card cardB = new Card(2, "Bbb", "Alpha", "", "", "", "", "", 1, "");
        Card cardC = new Card(3, "Aaa", "Beta", "", "", "", "", "", 1, "");
        Card cardD = new Card(4, "Bbb", "Beta", "", "", "", "", "", 1, "");

        List<Card> unsortedCards = new ArrayList<>();

        unsortedCards.add(cardC);
        unsortedCards.add(cardA);
        unsortedCards.add(cardB);
        unsortedCards.add(cardD);

        assertEquals(unsortedCards.get(0), cardC);
        assertEquals(unsortedCards.get(1), cardA);
        assertEquals(unsortedCards.get(2), cardB);
        assertEquals(unsortedCards.get(3), cardD);

        List<Card> sortedCards = cardService.sortCards(unsortedCards);

        assertEquals(sortedCards.get(0), cardA);
        assertEquals(sortedCards.get(1), cardB);
        assertEquals(sortedCards.get(2), cardC);
        assertEquals(sortedCards.get(3), cardD);
    }
}
