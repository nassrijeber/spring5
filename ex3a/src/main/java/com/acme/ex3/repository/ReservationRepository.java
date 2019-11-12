package com.acme.ex3.repository;

import com.acme.ex3.model.Member;
import com.acme.ex3.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
}
