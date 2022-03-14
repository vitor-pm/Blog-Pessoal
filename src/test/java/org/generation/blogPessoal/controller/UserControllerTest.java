package org.generation.blogPessoal.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Optional;

import org.generation.blogPessoal.dtos.UserRegisterDTO;
import org.generation.blogPessoal.model.UserModel;
import org.generation.blogPessoal.repository.UserRepository;
import org.generation.blogPessoal.service.UserService;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserControllerTest {

        @Autowired
        private TestRestTemplate testRestTemplate;

        @Autowired
        private UserService userService;

        @Autowired
        private UserRepository userRepository;

        @BeforeAll
        void start() {
                userRepository.deleteAll();
        }

        @Test
        @Order(1)
        @DisplayName("Register a User")
        public void createUserTest() {

                HttpEntity<UserModel> requisition = new HttpEntity<UserModel>(
                                new UserModel("Paulo Antunes", "paulo@email.com", "456789"));

                ResponseEntity<UserModel> response = testRestTemplate
                                .exchange("/user/register", HttpMethod.POST, requisition, UserModel.class);

                assertEquals(HttpStatus.CREATED, response.getStatusCode());
                assertEquals(requisition.getBody().getName(), response.getBody().getName());
                assertEquals(requisition.getBody().getUsername(), response.getBody().getUsername());
        }

        @Test
        @Order(2)
        @DisplayName("Dont duplicate user")
        public void dontDuplicateUser() {

                userService.registerUser(new UserRegisterDTO("Maria da Silva", "maria_silva@email.com", "123456"));

                HttpEntity<UserModel> requisition = new HttpEntity<UserModel>(
                                new UserModel("Maria da Silva", "maria_silva@email.com", "123456"));

                ResponseEntity<UserModel> response = testRestTemplate.exchange("/user/register", HttpMethod.POST,
                                requisition,
                                UserModel.class);

                assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        }

        @Test
        @Order(3)
        @DisplayName("Att User")
        public void attUser() {
                userRepository.deleteAll();
                Optional<UserModel> userCreated = userService
                                .registerUser(new UserRegisterDTO("Maria da Silva", "maria_silva@email.com", "123456"));

                UserModel userUpdate = new UserModel(userCreated.get().getId(), "Maria da Silva",
                                "maria_silva@email.com",
                                "123456");

                HttpEntity<UserModel> requisition = new HttpEntity<UserModel>(userUpdate);

                ResponseEntity<UserModel> response = testRestTemplate
                                .withBasicAuth("maria_silva@email.com", "123456")
                                .exchange("/user/update", HttpMethod.PUT, requisition, UserModel.class);

                assertEquals(HttpStatus.OK, response.getStatusCode());
                assertEquals(userUpdate.getName(), response.getBody().getName());
                assertEquals(userUpdate.getUsername(), response.getBody().getUsername());
        }

        @Test
        @Order(4)
        @DisplayName("List Users")
        public void listUsers() {

                userService.registerUser(
                                new UserRegisterDTO("Sabrina Sanches", "sabrina_sanches@email.com", "sabrina123"));
                userService.registerUser(
                                new UserRegisterDTO("Ricardo Marques", "ricardo_marques@email.com", "ricardo123"));

                ResponseEntity<String> response = testRestTemplate
                                .withBasicAuth("sabrina_sanches@email.com", "sabrina123")
                                .exchange("/user/all", HttpMethod.GET, null, String.class);

                assertEquals(HttpStatus.OK, response.getStatusCode());
        }

}
