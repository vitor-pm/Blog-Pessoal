package org.generation.blogPessoal.controller;

import java.util.List;
import java.util.Optional;

import org.generation.blogPessoal.dtos.UserCredentialsDTO;
import org.generation.blogPessoal.dtos.UserLoginDTO;
import org.generation.blogPessoal.dtos.UserRegisterDTO;
import org.generation.blogPessoal.model.UserModel;
import org.generation.blogPessoal.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/user")
@CrossOrigin(origins = "*", allowedHeaders = "*")
@Tag(name = "Controller de Usuarios", description = "Controller para logar/cadastrar/listar/deletar/atualizar usuarios")
public class UserController {

    @Autowired
    private UserService userService;

    @Operation(summary = "Logar com um usuario")
    @PostMapping("/login")
    public ResponseEntity<UserCredentialsDTO> autentication(@RequestBody Optional<UserLoginDTO> user) {
        return userService.login(user);
    }

    @Operation(summary = "Cadastrar um usuario")
    @PostMapping("/register")
    public ResponseEntity<UserModel> register(@RequestBody UserRegisterDTO user) {
        return userService.registerUser(user).map(resp -> ResponseEntity.status(201).body(resp))
                .orElse(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
    }

    @Operation(summary = "Atualizar dados de um usuario")
    @PutMapping("/update")
    public ResponseEntity<UserModel> update(@RequestBody UserModel user) {
        return userService.updateUser(user);
    }

    @Operation(summary = "Deletar um usuario pelo ID")
    @SuppressWarnings("rawtypes")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity deleteUserByID(@PathVariable Long id) {
        return userService.deleteUserByID(id);
    }

    @Operation(summary = "Listar todos usuarios")
    @GetMapping("/all")
    public ResponseEntity<List<UserModel>> listUser() {
        return userService.listUsers();
    }

}
