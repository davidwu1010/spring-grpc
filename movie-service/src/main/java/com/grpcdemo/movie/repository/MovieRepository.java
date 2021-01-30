package com.grpcdemo.movie.repository;

import com.grpcdemo.movie.entity.Movie;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MovieRepository extends JpaRepository<Movie, Integer> {
    List<Movie> getMoviesByGenreOrderByYearDesc(String genre);
}
