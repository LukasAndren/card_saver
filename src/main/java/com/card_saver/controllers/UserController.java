package com.card_saver.controllers;

import com.card_saver.model.User;
import com.card_saver.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/login", method= RequestMethod.GET)
    public String getLoginForm(){
        return "login";
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(@ModelAttribute(name="loginForm") User user, Model model){

        String username = user.getUsername();
        String password = user.getPassword();

        if(username.equals("admin") && password.equals("admin")){
            return "home";
        }

        User tempUser = new User();
        tempUser.setUsername(username);
        tempUser.setPassword(password);

        userService.createUser(tempUser);

        return "login";

    }
}
