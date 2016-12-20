package com.ramesh.security;

import com.ramesh.domain.User;
import com.ramesh.repository.UserRepository;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

@Component(value = "userDetailsService")
public class QuickPollUserDetailsService implements UserDetailsService {
    @Inject
    private UserRepository userRepository;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User with the username %s doesnt " +
                    "exit", username));
        }

        List<GrantedAuthority> authorities = new ArrayList<>();
        if (user.isAdmin()) {
            authorities = AuthorityUtils.createAuthorityList("ROLE_ADMIN");
        }
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(user
                .getUsername(), user.getPassword(), authorities);
        return userDetails;
    }
}
