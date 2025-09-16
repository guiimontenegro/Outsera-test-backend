package com.example.goldenraspberry.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.goldenraspberry.model.Movie;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Long> {

    // Buscar apenas os vencedores ordenados por produtor e ano
    List<Movie> findByWinnerTrueOrderByProducersAscYearAsc();
}
