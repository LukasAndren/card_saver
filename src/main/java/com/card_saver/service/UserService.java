package com.card_saver.service;

import com.card_saver.model.User;

public interface UserService {

    public User createUser(User user);
    public void validateUser(User user);
}
