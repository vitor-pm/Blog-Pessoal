package org.generation.blogPessoal.controller;

import java.util.List;

import javax.security.auth.message.callback.SecretKeyCallback.Request;

import org.generation.blogPessoal.model.Post;
import org.generation.blogPessoal.repository.PostRepository;
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
@RequestMapping("/posts")
@CrossOrigin("*")
@Tag(name = "Controller de Posts", description = "Controller para cadastrar/listar/deletar/atualizar postagens")
public class PostController {

    @Autowired
    private PostRepository repository;

    @Operation(summary = "Buscar todas as postagem")
    @GetMapping
    public ResponseEntity<List<Post>> findAllPosts() {
        return ResponseEntity.ok(repository.findAll());
    }

    @Operation(summary = "Buscando uma postagem pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<Post> findByIdPost(@PathVariable Long id, Request a) {
        return repository.findById(id).map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.notFound().build());

    }

    @Operation(summary = "Buscando uma postagem pelo Titulo")
    @GetMapping("/title/{title}")
    public ResponseEntity<List<Post>> getByTitle(@PathVariable String title) {
        return ResponseEntity.ok(repository.findAllByTitleContainingIgnoreCase(title));
    }

    @Operation(summary = "Criar uma nova postagem")
    @PostMapping
    public ResponseEntity<Post> postPost(@RequestBody Post post) {
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(post));
    }

    @Operation(summary = "Editar uma postagem")
    @PutMapping
    public ResponseEntity<Post> putPost(@RequestBody Post post) {
        return ResponseEntity.status(HttpStatus.OK).body(repository.save(post));
    }

    @Operation(summary = "Deletar uma postagem pelo ID")
    @DeleteMapping("/{id}")
    public void deletePost(@PathVariable Long id) {
        repository.deleteById(id);
    }

}
