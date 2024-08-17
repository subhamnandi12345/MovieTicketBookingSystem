package com.example.MovieTicketBookingApplication.service;

import com.example.MovieTicketBookingApplication.model.Movie;
import com.example.MovieTicketBookingApplication.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;


    public Optional<Movie> getMovieById(Long id) {
        return movieRepository.findById(id);
    }


    public Movie saveMovie(Movie movie) {
        return movieRepository.save(movie);
    }
    public boolean deleteMovie(Long id) {
        return movieRepository.findById(id)
                .map(movie -> {
                    movieRepository.delete(movie);
                    return true;
                })
                .orElse(false);
    }
}
