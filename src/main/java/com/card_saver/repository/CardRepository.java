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

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

@Repository
public class CardRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Transactional
    public Card createCard(Card card, User user){
        String sql = "insert into cards (cardName, cardSet, cardGrade, cardAltered, cardManaCost, cardType, cardDescription, cardUserId) " +
                "values (?, ?, ?, ?, ?, ?, ?, ?)";

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

                return ps;
            }
        }, holder);

        card.setId(holder.getKey().intValue());
        return card;
    }
}
