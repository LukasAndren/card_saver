package com.card_saver.service.impl;

import com.card_saver.model.User;
import com.card_saver.repository.UserRepository;
import com.card_saver.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public User handleLogin(User user) {
        if(userRepository.validateUserExists(user)){
            user.setId(userRepository.getUserId(user));
        } else {
            userRepository.createUser(user);
        }
        return user;
    }
}
