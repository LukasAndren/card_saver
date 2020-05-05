package com.card_saver.service;

import com.card_saver.model.User;

public interface IUserService {
    public User handleLogin(User user);
    public User createAccount(User user);
}
