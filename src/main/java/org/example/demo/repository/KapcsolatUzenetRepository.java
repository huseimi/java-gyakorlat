package org.example.demo.repository;

import org.example.demo.entity.KapcsolatUzenet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface KapcsolatUzenetRepository
        extends JpaRepository<KapcsolatUzenet, Long> {
}