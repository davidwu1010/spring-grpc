package com.grpcdemo.aggregator.service;

import com.grpcdemo.aggregator.dto.RecommendedMovie;
import com.grpcdemo.aggregator.dto.UserGenre;
import com.grpcdemo.common.Genre;
import com.grpcdemo.movie.MovieDto;
import com.grpcdemo.movie.MovieSearchRequest;
import com.grpcdemo.movie.MovieServiceGrpc.MovieServiceBlockingStub;
import com.grpcdemo.user.UserGenreUpdateRequest;
import com.grpcdemo.user.UserResponse;
import com.grpcdemo.user.UserSearchRequest;
import com.grpcdemo.user.UserServiceGrpc.UserServiceBlockingStub;
import java.util.List;
import java.util.stream.Collectors;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springframework.stereotype.Service;

@Service
public class UserMovieService {

    @GrpcClient("user-service")
    private UserServiceBlockingStub userStub;

    @GrpcClient("movie-service")
    private MovieServiceBlockingStub movieStub;

    public List<RecommendedMovie> getUserMovieSuggestion(String loginId) {
        UserSearchRequest userSearchRequest = UserSearchRequest.newBuilder().setLoginId(loginId)
            .build();
        UserResponse userResponse = userStub.getUserGenre(userSearchRequest);
        MovieSearchRequest movieSearchRequest = MovieSearchRequest.newBuilder()
            .setGenre(userResponse.getGenre()).build();
        return movieStub.getMovies(movieSearchRequest).getMovieList()
            .stream()
            .map(
                movie -> new RecommendedMovie(movie.getTitle(), movie.getYear(), movie.getRating()))
            .collect(Collectors.toList());
    }

    public void setUserGenre(UserGenre userGenre) {
        UserGenreUpdateRequest userGenreUpdateRequest = UserGenreUpdateRequest.newBuilder()
            .setLoginId(userGenre.getLoginId())
            .setGenre(Genre.valueOf(userGenre.getGenre().toUpperCase()))
            .build();
        userStub.updateUserGenre(userGenreUpdateRequest);
    }
}
