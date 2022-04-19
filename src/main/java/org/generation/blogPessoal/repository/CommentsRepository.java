package org.generation.blogPessoal.repository;

import org.generation.blogPessoal.model.Comments;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentsRepository extends JpaRepository<Comments, Long> {
    
}
