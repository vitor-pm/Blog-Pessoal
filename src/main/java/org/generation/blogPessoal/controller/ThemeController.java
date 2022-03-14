package org.generation.blogPessoal.controller;

import java.util.List;

import org.generation.blogPessoal.model.Theme;
import org.generation.blogPessoal.repository.ThemeRepository;
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
@CrossOrigin(origins = "*", allowedHeaders = "*")
@RequestMapping("/theme")
@Tag(name = "Controller de Temas", description = "Controller para cadastrar/listar/deletar/atualizar temas")
public class ThemeController {

    @Autowired
    private ThemeRepository repository;

    @Operation(summary = "Listar todo os temas")
    @GetMapping
    public ResponseEntity<List<Theme>> getAll() {
        return ResponseEntity.ok(repository.findAll());
    }

    @Operation(summary = "Buscar um tema pelo ID")
    @GetMapping("/{id}")
    public ResponseEntity<Theme> getById(@PathVariable long id) {
        return repository.findById(id).map(resp -> ResponseEntity.ok(resp))
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Buscar um tema pelo nome")
    @GetMapping("/name/{name}")
    public ResponseEntity<List<Theme>> getByName(@PathVariable String name) {
        return ResponseEntity.ok(repository.findAllByDescriptionContainingIgnoreCase(name));
    }

    @Operation(summary = "Criar um novo tema")
    @PostMapping
    public ResponseEntity<Theme> post(@RequestBody Theme theme) {
        return ResponseEntity.status(HttpStatus.CREATED).body(repository.save(theme));
    }

    @Operation(summary = "Editar um tema")
    @PutMapping
    public ResponseEntity<Theme> put(@RequestBody Theme theme) {
        return ResponseEntity.ok(repository.save(theme));
    }

    @Operation(summary = "Deletar um tema pelo Id")
    @DeleteMapping("/{id}")
    public void delete(@PathVariable long id) {
        repository.deleteById(id);
    }

}
