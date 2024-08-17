package com.example.MovieTicketBookingApplication.repository;

import com.example.MovieTicketBookingApplication.model.Movie;
import com.example.MovieTicketBookingApplication.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
    Optional<Ticket> findByMovieAndSeatNumber(Movie movie, int seatNumber);

    List<Ticket> findAllByMovie(Movie movie);
}
