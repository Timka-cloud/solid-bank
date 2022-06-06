package com.timka.solid.bank.services;

import com.timka.solid.bank.dto.JwtRequest;
import com.timka.solid.bank.entities.Role;
import com.timka.solid.bank.entities.User;
import com.timka.solid.bank.repositories.RoleRepository;
import com.timka.solid.bank.repositories.UserRepository;
import com.timka.solid.bank.response.BodyResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }


    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findByUsername(username).orElseThrow(() -> new UsernameNotFoundException(String.format("User '%s' not found", username)));
        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), mapRolesToAuthorities(user.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }

    public BodyResponse register(JwtRequest jwtRequest) {
        Optional<User> user = userRepository.findByUsername(jwtRequest.getUsername());
        if (user.isPresent()) {
            return new BodyResponse("User exists", Response.Status.CONFLICT, null);
        }

        User savedUser = new User();
        savedUser.setUsername(jwtRequest.getUsername());
        savedUser.setPassword(passwordEncoder.encode(jwtRequest.getPassword()));
        Collection<Role> collection = new ArrayList<>();
        collection.add(roleRepository.findById(1L).get());
        savedUser.setRoles(collection);

        return new BodyResponse("User created", Response.Status.OK, userRepository.save(savedUser));
    }
}
