package com.grpcdemo.movie.service;

import com.grpcdemo.movie.MovieDto;
import com.grpcdemo.movie.MovieSearchRequest;
import com.grpcdemo.movie.MovieSearchResponse;
import com.grpcdemo.movie.MovieServiceGrpc.MovieServiceImplBase;
import com.grpcdemo.movie.repository.MovieRepository;
import io.grpc.stub.StreamObserver;
import java.util.List;
import java.util.stream.Collectors;
import net.devh.boot.grpc.server.service.GrpcService;
import org.springframework.beans.factory.annotation.Autowired;

@GrpcService
public class MovieService extends MovieServiceImplBase {

    private final MovieRepository movieRepository;

    @Autowired
    public MovieService(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void getMovies(MovieSearchRequest request,
        StreamObserver<MovieSearchResponse> responseObserver) {
        List<MovieDto> movieDtoList = movieRepository
            .getMoviesByGenreOrderByYearDesc(request.getGenre().toString())
            .stream()
            .map(movie -> MovieDto
                .newBuilder()
                .setTitle(movie.getTitle())
                .setYear(movie.getYear())
                .setRating(movie.getRating())
                .build())
            .collect(Collectors.toList());
        MovieSearchResponse movieSearchResponse = MovieSearchResponse.newBuilder()
            .addAllMovie(movieDtoList).build();
        responseObserver.onNext(movieSearchResponse);
        responseObserver.onCompleted();
    }
}
