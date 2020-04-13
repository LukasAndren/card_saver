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

@Repository
public class CardRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Transactional
    public Card createCard(Card card, User user){
        String sql = "insert into cards (cardName, cardSet, cardGrade, cardAltered, cardManaCost, cardType, cardDescription, cardUserId, cardPrice) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        KeyHolder holder = new GeneratedKeyHolder();
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
                ps.setString(8, String.valueOf(user.getId()));
                ps.setString(9, card.getPrice());

                return ps;
            }
        }, holder);

        card.setId(holder.getKey().intValue());
        return card;
    }

    @Transactional
    public List<Card> getCurrentUsersCards(User user){
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

    @Transactional
    public void updateCard(Card card){
        String sql = "UPDATE CARDS SET CARDNAME = ?, CARDSET = ?, CARDGRADE = ?, CARDALTERED = ?, CARDMANACOST = ?, " +
                "CARDTYPE = ?, CARDDESCRIPTION = ?, CARDUSERID = ?, CARDPRICE = ? WHERE CARDID = ?";

        jdbcTemplate.update(sql, card.getName(), card.getSet(), card.getGrade(), card.getAltered(), card.getManaCost(),
                card.getType(), card.getDescription(), card.getUserId(), card.getPrice(), card.getId());

    }
}
