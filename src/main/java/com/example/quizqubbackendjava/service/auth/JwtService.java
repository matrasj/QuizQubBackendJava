package com.example.quizqubbackendjava.service.auth;

import com.example.quizqubbackendjava.exception.UserNotFoundException;
import com.example.quizqubbackendjava.mapper.UserPayloadResponseMapper;
import com.example.quizqubbackendjava.model.entity.User;
import com.example.quizqubbackendjava.model.payload.auth.login.LoginPayloadRequest;
import com.example.quizqubbackendjava.model.payload.auth.login.LoginPayloadResponse;
import com.example.quizqubbackendjava.model.payload.user.UserPayloadResponse;
import com.example.quizqubbackendjava.repository.UserRepository;
import com.example.quizqubbackendjava.util.JwtUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;

@Service
@RequiredArgsConstructor
@Slf4j
public class JwtService implements UserDetailsService {
    private static final String USER_NOT_FOUND_MESSAGE = "Not found user with username %s";
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username does not exist"));

        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                getAuthorities(user)
        );

    }

    private Collection<? extends GrantedAuthority> getAuthorities(User user) {
        return Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRole().getName()));
    }

    public LoginPayloadResponse createJwtToken(LoginPayloadRequest jwtRequest) {
        String username = jwtRequest.getUsername();
        String password = jwtRequest.getPassword();
        authenticate(username, password);

        final UserDetails userDetails = loadUserByUsername(username);
        String generatedToken = jwtUtil.generateToken(userDetails);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(String.format(USER_NOT_FOUND_MESSAGE, username)));


        return LoginPayloadResponse.builder()
                .jwtToken(generatedToken)
                .userPayloadResponse(UserPayloadResponseMapper.mapToUserPayloadResponse(user))
                .build();
    }



    public void authenticate(String username, String password) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException exception) {
            log.error("User is disabled");
        } catch (BadCredentialsException badCredentialsException) {
            log.error("Bard credentials from user");
        }
    }


}
