package org.generation.blogPessoal.service;

import java.nio.charset.Charset;
import org.apache.commons.codec.binary.Base64;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.generation.blogPessoal.dtos.UserCredentialsDTO;
import org.generation.blogPessoal.dtos.UserLoginDTO;
import org.generation.blogPessoal.dtos.UserRegisterDTO;
import org.generation.blogPessoal.model.UserModel;
import org.generation.blogPessoal.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class UserService {

    private @Autowired UserRepository userRepository;
    private UserCredentialsDTO userLog;

    public Optional<UserModel> registerUser(@Valid UserRegisterDTO user) {

        Optional<UserModel> receive = userRepository.findByUsername(user.getUsername());

        if (receive.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nome de usuario ja cadastrado");
        } else {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));

            UserModel userModel = new UserModel(
                    user.getName(),
                    user.getUsername(),
                    user.getPassword(),
                    user.getPicture(),
                    user.getRole());

            return Optional.ofNullable(userRepository.save(userModel));
        }
    }

    public ResponseEntity<UserCredentialsDTO> login(Optional<UserLoginDTO> user) {
        Optional<UserModel> us = userRepository.findByUsername(user.get().getUsername());

        if (us.isPresent()) {
            if (comparePassword(user.get().getPassword(), us.get().getPassword())) {

                userLog = new UserCredentialsDTO(
                        generatorBasicToken(user.get().getUsername(), user.get().getPassword()),
                        us.get().getId(),
                        us.get().getName(),
                        us.get().getUsername(),
                        us.get().getPassword(),
                        us.get().getPicture(),
                        us.get().getRole());

                return ResponseEntity.status(HttpStatus.OK).body(userLog);
            }
        }
        throw new ResponseStatusException(
                HttpStatus.UNAUTHORIZED, "Usuário ou senha inválidos!", null);

    }

    public ResponseEntity<UserModel> updateUser(UserModel user) {
        Optional<UserModel> optional = userRepository.findById(user.getId());

        if (optional.isPresent()) {
            BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
            user.setPassword(encoder.encode(user.getPassword()));

            return ResponseEntity.status(200).body(userRepository.save(user));
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID NÂO ENCONTRADO");
        }
    }

    @SuppressWarnings("rawtypes")
    public ResponseEntity deleteUserByID(Long id) {
        Optional<UserModel> user = userRepository.findById(id);

        if (user.isPresent()) {
            userRepository.deleteById(id);
            return ResponseEntity.status(200).build();
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ID NÂO ENCONTRADO");
        }
    }

    public ResponseEntity<List<UserModel>> listUsers() {
        List<UserModel> users = userRepository.findAll();

        if (users.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } else {
            return ResponseEntity.status(HttpStatus.OK).body(users);
        }
    }

    public ResponseEntity<UserModel> getUserByID(Long id) {
        Optional<UserModel> user = userRepository.findById(id);
        if (user.isPresent()) {
            return ResponseEntity.status(HttpStatus.OK).body(user.get());
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ID não encontrado");
        }
    }

    private boolean comparePassword(String senhaDigitada, String senhaBanco) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        return encoder.matches(senhaDigitada, senhaBanco);
    }

    private String generatorBasicToken(String email, String password) {
        String structure = email + ":" + password;
        byte[] structureBase64 = Base64.encodeBase64(structure.getBytes(Charset.forName("US-ASCII")));
        return "Basic " + new String(structureBase64);
    }
}
