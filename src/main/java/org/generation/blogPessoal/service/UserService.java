package org.generation.blogPessoal.service;

import java.nio.charset.Charset;
import org.apache.commons.codec.binary.Base64;
import java.util.Optional;

import org.generation.blogPessoal.dtos.UserLoginDTO;
import org.generation.blogPessoal.model.UserModel;
import org.generation.blogPessoal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public Optional<UserModel> registerUser(UserModel user) {

        Optional<UserModel> receive = userRepository.findByUsername(user.getUsername());

        if (receive.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username already exists");
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

            String passwordEncoder = encoder.encode(user.getPassword());
            user.setPassword(passwordEncoder);

            return Optional.ofNullable(userRepository.save(user));
        }
    }

    public Optional<UserLoginDTO> login(Optional<UserLoginDTO> user) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        Optional<UserModel> us = userRepository.findByUsername(user.get().getUsername());

        if (us.isPresent()) {
            if (encoder.matches(user.get().getPassword(), us.get().getPassword())) {

                String auth = user.get().getUsername() + ":" + user.get().getPassword();
                byte[] encodeAuth = Base64.encodeBase64(auth.getBytes(Charset.forName("US-ASCII")));
                String authHeader = "Basic " + new String(encodeAuth);

                user.get().setToken(authHeader);
                user.get().setName(us.get().getName());

                return user;
            }
        }
        return null;
    }

}
