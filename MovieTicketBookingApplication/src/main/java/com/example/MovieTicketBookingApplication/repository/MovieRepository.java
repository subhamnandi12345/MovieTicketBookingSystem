package com.example.MovieTicketBookingApplication.repository;



import com.example.MovieTicketBookingApplication.model.Movie;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<Movie, Long> {
}