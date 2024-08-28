package com.vgipl.service.impl;

import com.vgipl.model.User;
import com.vgipl.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        System.out.println("user name received : "+username);
        User dbUser = userRepository.findByEmail(username);

        if(dbUser == null){
            throw new UsernameNotFoundException("User with "+username+" not found");
        }
        return new CustomUserDetails(dbUser);
    }

}
