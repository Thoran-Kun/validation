package com.salvatore.validation.repositories;

import com.salvatore.validation.entities.Autore;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AutoriRepository extends JpaRepository<Autore, Long> {
    Optional<Autore> findByEmail(String email);

    boolean existsByEmail(String email);

}
