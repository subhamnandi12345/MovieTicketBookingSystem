package com.example.MovieTicketBookingApplication.repository;


import com.example.MovieTicketBookingApplication.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
}