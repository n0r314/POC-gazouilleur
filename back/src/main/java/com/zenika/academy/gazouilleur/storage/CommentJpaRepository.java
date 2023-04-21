package com.zenika.academy.gazouilleur.storage;

import com.zenika.academy.gazouilleur.model.comment.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CommentJpaRepository extends JpaRepository<Comment, UUID> {

  // pour trouver les commentaires d'un gazouilli donné
  List<Comment> findAllByGazouilli_IdOrderByCreatedAtDesc(UUID gazouilliId);
}
