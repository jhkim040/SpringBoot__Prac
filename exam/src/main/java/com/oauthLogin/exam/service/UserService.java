package com.oauthLogin.exam.service;


import com.oauthLogin.exam.entity.User;
import com.oauthLogin.exam.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public void insert(User user) {

        userRepository.save(user);
    }

}
