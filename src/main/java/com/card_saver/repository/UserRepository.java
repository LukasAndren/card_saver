package com.card_saver.repository;

import com.card_saver.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.transaction.annotation.Transactional;

import java.sql.*;

@Repository
public class UserRepository {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Transactional
    public void createUser(User user){
        String sql = "INSERT INTO USERS (USERNAME, PASSWORD) VALUES ('" + user.getUsername() + "', '" + user.getPassword() + "')";

        KeyHolder holder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            @Override
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

                return ps;
            }
        }, holder);

        user.setId(holder.getKey().intValue());
    }

    @Transactional
    public Boolean validateUserExists(User user){
        String sql = "SELECT COUNT(*) FROM USERS WHERE USERNAME = '" + user.getUsername() + "' AND PASSWORD = '" + user.getPassword() + "'";

        int count = jdbcTemplate.queryForObject(sql, int.class);

        if(count > 0){
           return true;
        } else {
            return false;
        }
    }

    @Transactional
    public int getUserId(User user){
        String userIdSql = "SELECT USERID FROM USERS WHERE USERNAME = '" + user.getUsername() + "' AND PASSWORD = '" + user.getPassword() + "'";
        return jdbcTemplate.queryForObject(userIdSql, int.class);

    }
}
