package com.bulk.kyro.bll.services;

import com.bulk.kyro.dl.entities.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface AuthService extends UserDetailsService {

    UserEntity register(UserEntity user);
    UserEntity login(String username, String password);
}
