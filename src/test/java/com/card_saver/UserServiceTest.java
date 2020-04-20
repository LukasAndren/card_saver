package com.card_saver;

import com.card_saver.model.User;
import com.card_saver.repository.UserRepository;
import com.card_saver.service.impl.UserService;

import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.Assert.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.when;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class UserServiceTest {

    @InjectMocks
    UserService userService;

    @Mock
    UserRepository userRepository;

    @Mock
    JdbcTemplate jdbcTemplate;

    User user;

    @BeforeEach
    void setup(){
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testHandleLoginExistsTrue(){
    user = new User();
    user.setUsername("TestUsername");
    user.setPassword("TestPassword");

    when(userRepository.userExists(user)).thenReturn(true);
    when(userRepository.getUserId(user)).thenReturn(1);

    userService.handleLogin(user);

    assertEquals(user.getId(), 1);

    }

    @Test
    void testHandleLoginUsernameNotUnique(){
        user = new User();
        user.setUsername("TestUsername");
        user.setPassword("TestPassword");

        when(userRepository.userExists(user)).thenReturn(false);
        when(userRepository.usernameIsUnique(user)).thenReturn(false);

        userService.handleLogin(user);

        assertEquals(user.getId(), -1);

    }
}
