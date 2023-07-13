package com.deni.app.security.service;

import com.deni.app.module.user.User;
import com.deni.app.module.user.UserRepo;
import com.deni.app.security.dto.UserPrincipalDTO;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserPrincipalDetailsService implements UserDetailsService {
    private UserRepo userRepo;

    public UserPrincipalDetailsService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }


    @Override // call when login api hit
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = this.userRepo.findByUsername(s);
        UserPrincipalDTO userPrincipal = new UserPrincipalDTO(user);

        return userPrincipal;
    }
}
