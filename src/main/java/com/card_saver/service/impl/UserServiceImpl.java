package com.card_saver.service.impl;

import com.card_saver.model.User;
import com.card_saver.repository.UserRepository;
import com.card_saver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User createUser(User user) {
        return userRepository.createUser(user);
    }

    @Override
    public void validateUser(User user) {

    }
}
