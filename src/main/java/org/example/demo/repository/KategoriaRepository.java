package org.example.demo.repository;

import org.example.demo.entity.Kategoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KategoriaRepository extends JpaRepository<Kategoria, String> {
}