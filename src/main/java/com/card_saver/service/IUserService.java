package com.card_saver.service;

import com.card_saver.model.User;

public interface IUserService {
    void handleLogin(User user);
    void createAccount(User user);
    void deleteUser(User user);
}
