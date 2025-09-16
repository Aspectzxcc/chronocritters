package com.chronocritters.user.auth.service;

import org.springframework.stereotype.Service;

import com.chronocritters.lib.model.domain.Player;
import com.chronocritters.lib.model.domain.User;
import com.chronocritters.lib.model.domain.PlayerStats;
import com.chronocritters.lib.util.JwtUtil;
import com.chronocritters.lib.util.PasswordUtil;

import com.chronocritters.user.auth.dto.LoginResponse;
import com.chronocritters.user.player.repository.PlayerRepository;
import com.chronocritters.user.player.repository.UserRepository;

import io.leangen.graphql.annotations.GraphQLArgument;
import io.leangen.graphql.annotations.GraphQLMutation;
import io.leangen.graphql.spqr.spring.annotations.GraphQLApi;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;

@Service
@RequiredArgsConstructor
@GraphQLApi
public class AuthService {
    private final UserRepository userRepository;
    private final PlayerRepository playerRepository;

    @GraphQLMutation(name = "register")
    public LoginResponse register(
        @GraphQLArgument(name = "username") String username,
        @GraphQLArgument(name = "password") String password
    ) {
        if (userRepository.findByUsername(username.trim()).isPresent()) {
            throw new IllegalArgumentException("Username already taken");
        }

        User newUser = new User();
        newUser.setUsername(username.trim());
        newUser.setPassword(PasswordUtil.hashPassword(password));
        userRepository.save(newUser);
        
        Player newPlayer = Player.builder()
                .userId(newUser.getId())
                .stats(PlayerStats.builder().build())
                .roster(new ArrayList<>())
                .build();
        playerRepository.save(newPlayer);

        com.chronocritters.user.auth.dto.User userDto = new com.chronocritters.user.auth.dto.User(newUser.getId(), newUser.getUsername());
        String token = JwtUtil.generateToken(newUser.getId(), newUser.getUsername());
        
        return new LoginResponse(userDto, token);
    }

    @GraphQLMutation(name = "login")
    public LoginResponse login(
        @GraphQLArgument(name = "username") String username,
        @GraphQLArgument(name = "password") String password
    ) {
        User user = userRepository.findByUsername(username.trim())
                .orElseThrow(() -> new IllegalArgumentException("Invalid username or password"));

        if (!PasswordUtil.checkPassword(password, user.getPassword())) {
            throw new IllegalArgumentException("Invalid username or password");
        }

        com.chronocritters.user.auth.dto.User userDto = new com.chronocritters.user.auth.dto.User(user.getId(), user.getUsername());
        String token = JwtUtil.generateToken(user.getId(), user.getUsername());
        return new LoginResponse(userDto, token);
    }
}