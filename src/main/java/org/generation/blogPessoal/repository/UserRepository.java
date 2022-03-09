package org.generation.blogPessoal.repository;

import java.util.List;
import java.util.Optional;

import org.generation.blogPessoal.model.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserModel, Long> {

    public Optional<UserModel> findByUsername(String user);

    public List<UserModel> findAllByNameContainingIgnoreCase(String name);

}
