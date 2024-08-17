package com.example.MovieTicketBookingApplication.service;


import com.example.MovieTicketBookingApplication.model.Movie;
import com.example.MovieTicketBookingApplication.model.Ticket;
import com.example.MovieTicketBookingApplication.model.User;
import com.example.MovieTicketBookingApplication.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class TicketService {

    @Autowired
    private TicketRepository ticketRepository;

    private static final int TOTAL_SEATS = 100;


    public Ticket bookTicket(Movie movie, User user, int seatNumber) {
        boolean isSeatAvailable = ticketRepository.findByMovieAndSeatNumber(movie, seatNumber).isEmpty();

        if (!isSeatAvailable) {
            throw new RuntimeException("Seat is already booked");
        }

        Ticket ticket = new Ticket();
        ticket.setMovie(movie);
        ticket.setUser(user);
        ticket.setSeatNumber(seatNumber);
        ticket.setBookingTime(LocalDateTime.now());

        return ticketRepository.save(ticket);
    }


    public boolean cancelTicket(Long ticketId) {
        Optional<Ticket> ticket = ticketRepository.findById(ticketId);

        if (ticket.isPresent()) {
            ticketRepository.delete(ticket.get());
            return true;
        } else {
            return false;
        }
    }


    public List<Integer> getAvailableSeats(Movie movie) {
        List<Ticket> bookedTickets = ticketRepository.findAllByMovie(movie);
        List<Integer> bookedSeatNumbers = bookedTickets.stream()
                .map(Ticket::getSeatNumber)
                .collect(Collectors.toList());

        return IntStream.rangeClosed(1, TOTAL_SEATS)
                .filter(seat -> !bookedSeatNumbers.contains(seat))
                .boxed()
                .collect(Collectors.toList());
    }
}