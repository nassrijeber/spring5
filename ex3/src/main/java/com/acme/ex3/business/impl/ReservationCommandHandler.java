package com.acme.ex3.business.impl;

import java.util.function.Predicate;

import com.acme.common.business.CommandException;
import com.acme.common.business.CommandHandler;
import com.acme.common.service.AbstractCommand;
import com.acme.ex3.model.Book;
import com.acme.ex3.model.Member;
import com.acme.ex3.model.Reservation;
import com.acme.ex3.repository.BookRepository;
import com.acme.ex3.repository.MemberRepository;
import com.acme.ex3.repository.ReservationRepository;
import com.acme.ex3.service.command.ReservationCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component // added
@CommandHandler.Handler // soit ça soit bas 1/
public class ReservationCommandHandler implements CommandHandler {

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    BookRepository bookRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Override
    //  @Transactional(propagation= Propagation.MANDATORY) // added soit ça soit haut 1/
    public void handle(AbstractCommand command, HandlingContext handlingContext) {
        if(!(command instanceof ReservationCommand)){
            return;
        }

        ReservationCommand cmd= (ReservationCommand)command;

        /* TODO : 
		Remplacer null ci dessous par un appel au repository pour obtenir le Member dont le username est cmd.getUsername()
        S'il n'y en pas : new CommandException("reservation-member.not.found", true)
        Suggestion : utiliser la méthode orElseThrow de la classe Optional
        */        
        Member member = this.memberRepository.findByAccountUsername(cmd.getUsername())
                .orElseThrow(() -> new CommandException("reservation-member.not.found", true)); //added

        Predicate<Reservation> conflict = r -> r.getPickupDate().isBefore(cmd.getReturnDate()) && r.getReturnDate().isAfter(cmd.getPickupDate());
        
        if(member.getReservations().stream().anyMatch(conflict))
        {
            throw new CommandException("reservation-member.quota.exceeded", true);
        }

        /* TODO : 
		Remplacer null ci dessous par un appel au repository pour obtenir le livre dont l'id est cmd.getBookId()
        S'il n'y en pas : new CommandException("reservation-book.unavailable", true);
        Suggestion : utiliser la méthode orElseThrow de la classe Optional
        */
        Book book = this.bookRepository.findById(cmd.getBookId())
                .orElseThrow(() -> new CommandException("reservation-book.unavailable", true)); //added

        if(book.getReservations().stream().anyMatch(conflict))
        {
            throw new CommandException("reservation-book.unavailable", true);
        }

        Reservation reservation = new Reservation();
        reservation.setMember(member);
        reservation.setBook(book);
        reservation.setPickupDate(cmd.getPickupDate());
        reservation.setReturnDate(cmd.getReturnDate());
        // TODO : sauvegarde de la réservation
        this.reservationRepository.save(reservation);
        // TODO : valorisation de la propriété reservation de la commande avec l'objet rerversation qui vient d'être sauvegardée.
        cmd.setReservation(reservation);
    }
}
