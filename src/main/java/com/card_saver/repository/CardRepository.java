package com.card_saver.repository;

import com.card_saver.model.Card;
import com.card_saver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;
import java.util.List;

/**
 * Handles database services that results in/requires Cards.
 */
@Repository
public class CardRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    /**
     * Saves the Card to the database.
     * @param card - The Card to be saved
     */
    @Transactional
    public void saveCard(Card card){
        String sql = "insert into cards (cardName, cardSet, cardGrade, cardAltered, cardManaCost, cardType, cardDescription, cardPrice, cardUserId) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                ps.setString(1, card.getName());
                ps.setString(2, card.getSet());
                ps.setString(3, card.getGrade());
                ps.setString(4, card.getAltered());
                ps.setString(5, card.getManaCost());
                ps.setString(6, card.getType());
                ps.setString(7, card.getDescription());
                ps.setString(8, card.getPrice());
                ps.setInt(9, card.getUserId());


                return ps;
            }
        });
    }

    /**
     * Fetches all Cards belonging to the parameter User from the database.
     * It does this by checking for Cards with the userId matching the input User's id.
     * @param user - The User whose cards are to be fetched.
     * @return All Cards belonging to the User.
     */
    @Transactional
    public List<Card> getAllCardsFromUser(User user){
        String sql = "SELECT * FROM CARDS WHERE CARDUSERID = " + user.getId();

        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Card(
                        rs.getInt("cardId"),
                        rs.getString("cardName"),
                        rs.getString("cardSet"),
                        rs.getString("cardGrade"),
                        rs.getString("cardAltered"),
                        rs.getString("cardManaCost"),
                        rs.getString("cardType"),
                        rs.getString("cardDescription"),
                        rs.getInt("cardUserId"),
                        rs.getString("cardPrice"))
                );
    }

    /**
     * Fetches a Card based on the cardId.
     * @param cardId - The id of the Card to be fetched.
     * @return A Card object representing the row that matched.
     */
    @Transactional
    public Card findById(int cardId){
        String sql = "SELECT * FROM CARDS WHERE CARDID = " + cardId;
        return jdbcTemplate.queryForObject(sql, (rs, rowNum) ->
                new Card(
                        rs.getInt("cardId"),
                        rs.getString("cardName"),
                        rs.getString("cardSet"),
                        rs.getString("cardGrade"),
                        rs.getString("cardAltered"),
                        rs.getString("cardManaCost"),
                        rs.getString("cardType"),
                        rs.getString("cardDescription"),
                        rs.getInt("cardUserId"),
                        rs.getString("cardPrice"))

                );
    }

    /**
     * Updates a row in the Card table to the input Card's variables.
     * It finds the Card based on it's id.
     * @param card - The Card containing the new values.
     */
    @Transactional
    public void updateCard(Card card){
        String sql = "UPDATE CARDS SET CARDNAME = ?, CARDSET = ?, CARDGRADE = ?, CARDALTERED = ?, CARDMANACOST = ?, " +
                "CARDTYPE = ?, CARDDESCRIPTION = ?, CARDUSERID = ?, CARDPRICE = ? WHERE CARDID = ?";

        jdbcTemplate.update(sql, card.getName(), card.getSet(), card.getGrade(), card.getAltered(), card.getManaCost(),
                card.getType(), card.getDescription(), card.getUserId(), card.getPrice(), card.getId());

    }

    @Transactional
    public void deleteCard(int cardId){
        String sql = "DELETE FROM CARDS WHERE CARDID = " + cardId;

        jdbcTemplate.execute(sql);
    }
}
