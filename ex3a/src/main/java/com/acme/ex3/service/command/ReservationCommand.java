package com.acme.ex3.service.command;

import java.time.LocalDate;

import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.acme.common.service.AbstractCommand;
import com.acme.ex3.business.impl.ReservationCommandHandler;
import com.acme.ex3.model.Reservation;

@AbstractCommand.Usecase(handlers = {ReservationCommandHandler.class})
public class ReservationCommand extends AbstractCommand {

    // inputs
    private int bookId;
    @NotBlank
    private String username;
    @NotNull @FutureOrPresent
    private LocalDate pickupDate;
    @NotNull @Future
    private LocalDate returnDate;
    // output
    private Reservation reservation;

    public ReservationCommand() {
    	/*
    	Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    	if(authentication!=null) {
    		this.username = authentication.getName();
    	}
    	*/
	}
    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public LocalDate getPickupDate() {
        return pickupDate;
    }

    public void setPickupDate(LocalDate pickupDate) {
        this.pickupDate = pickupDate;
    }

    public LocalDate getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(LocalDate returnDate) {
        this.returnDate = returnDate;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }
}
