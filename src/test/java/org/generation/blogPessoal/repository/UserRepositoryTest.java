package org.generation.blogPessoal.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;

import org.generation.blogPessoal.model.UserModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @BeforeAll
    void start() {
        userRepository.deleteAll();
        userRepository.save(new UserModel("João da Silva", "joao@email.com", "123456"));
        userRepository.save(new UserModel("Manuela da Silva", "manuela@email.com", "987654"));
        userRepository.save(new UserModel("Adriana da Silva", "Adriana@email.com", "963258"));
        userRepository.save(new UserModel("Paulo Antunes", "paulo@email.com", "456789"));
    }

    @Test
    @DisplayName("Return one user")
    public void returnOneUser() {
        Optional<UserModel> user = userRepository.findByUsername("joao@email.com");
        assertTrue(user.get().getUsername().equals("joao@email.com"));
    }

    @Test
    @DisplayName("Return three user")
    public void returnThreeUser() {
        List<UserModel> users = userRepository.findAllByNameContainingIgnoreCase("silva");
        assertEquals(3, users.size());
        assertTrue(users.get(0).getName().equals("João da Silva"));
        assertTrue(users.get(1).getName().equals("Manuela da Silva"));
        assertTrue(users.get(2).getName().equals("Adriana da Silva"));
    }

}
