package org.example.demo.repository;

import org.example.demo.entity.Rendeles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RendelesRepository extends JpaRepository<Rendeles, Long> {

    @Query(value = "SELECT pizzanev, SUM(darab) AS osszes_darab " +
            "FROM rendeles " +
            "GROUP BY pizzanev " +
            "ORDER BY osszes_darab DESC " +
            "LIMIT 10", nativeQuery = true)
    List<Object[]> findTop10Pizzak();
}