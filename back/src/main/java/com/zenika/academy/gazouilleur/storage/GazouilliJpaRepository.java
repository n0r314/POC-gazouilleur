package com.zenika.academy.gazouilleur.storage;

import com.zenika.academy.gazouilleur.model.gazouilli.Gazouilli;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface GazouilliJpaRepository extends JpaRepository<Gazouilli, UUID> {

  // pour filter par auteur de gazouilli
  List<Gazouilli> findAllByAuthor_UsernameOrderByCreatedAtDesc(String username);

  // pour filtrer par hashtag
  List<Gazouilli> findAllByContentContainingIgnoreCaseOrderByCreatedAtDesc(String hashtag);
}
