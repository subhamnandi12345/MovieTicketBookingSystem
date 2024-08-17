package com.example.MovieTicketBookingApplication.controller;

import com.example.MovieTicketBookingApplication.model.Movie;

import com.example.MovieTicketBookingApplication.model.Ticket;
import com.example.MovieTicketBookingApplication.model.User;
import com.example.MovieTicketBookingApplication.service.MovieService;
import com.example.MovieTicketBookingApplication.service.TicketService;
import com.example.MovieTicketBookingApplication.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/tickets")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @Autowired
    private MovieService movieService;

    @Autowired
    private UserService userService;

    @PostMapping("/book")
    public ResponseEntity<Ticket> bookTicket(@RequestParam Long movieId,
                                             @RequestParam Long userId,
                                             @RequestParam int seatNumber) {
        Optional<Movie> movie = movieService.getMovieById(movieId);
        Optional<User> user = userService.getUserById(userId);

        if (movie.isPresent() && user.isPresent()) {
            try {
                Ticket ticket = ticketService.bookTicket(movie.get(), user.get(), seatNumber);
                return ResponseEntity.ok(ticket);
            } catch (RuntimeException e) {
                return ResponseEntity.badRequest().body(null);
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/cancel/{ticketId}")
    public ResponseEntity<Void> cancelTicket(@PathVariable Long ticketId) {
        boolean isCancelled = ticketService.cancelTicket(ticketId);

        if (isCancelled) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @GetMapping("/available-seats")
    public ResponseEntity<List<Integer>> getAvailableSeats(@RequestParam Long movieId) {
        Optional<Movie> movie = movieService.getMovieById(movieId);

        if (movie.isPresent()) {
            List<Integer> availableSeats = ticketService.getAvailableSeats(movie.get());
            return ResponseEntity.ok(availableSeats);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}