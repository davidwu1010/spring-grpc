package com.grpcdemo.aggregator.controller;

import com.grpcdemo.aggregator.dto.RecommendedMovie;
import com.grpcdemo.aggregator.dto.UserGenre;
import com.grpcdemo.aggregator.service.UserMovieService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AggregatorController {

    private final UserMovieService userMovieService;

    @Autowired
    public AggregatorController(UserMovieService userMovieService) {
        this.userMovieService = userMovieService;
    }

    @GetMapping("/user/{loginId}")
    public List<RecommendedMovie> getMovies(@PathVariable String loginId) {
        return userMovieService.getUserMovieSuggestion(loginId);
    }

    @PutMapping("/user/")
    public void setUserGenre(@RequestBody UserGenre userGenre) {
        userMovieService.setUserGenre(userGenre);
    }
}
