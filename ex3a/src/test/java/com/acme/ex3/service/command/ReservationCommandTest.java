package com.acme.ex3.service.command;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.time.LocalDate;

import com.acme.ex3.ApplicationConfig;
import org.junit.jupiter.api.Test;

import com.acme.common.service.impl.CommandProcessorImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

// sans Spring boot : @SpringJUnitConfig(classes = ApplicationConfig.class)
@SpringBootTest
public class ReservationCommandTest {

    @Autowired
    private CommandProcessorImpl processor;

    @Test
    @Sql(statements = "delete from reservation")
    void testReservationCommand() {
        ReservationCommand cmd = new ReservationCommand();
        cmd.setBookId(1);
        cmd.setUsername("jdoe");
        cmd.setPickupDate(LocalDate.now().plusDays(1));
        cmd.setReturnDate(LocalDate.now().plusDays(10));
        cmd = this.processor.process(cmd);
        assertNotNull(cmd.getReservation());
        System.out.println(cmd.getReservation());
    }
    
    @Test
    void testReservationCommandAsync() throws Exception {
        ReservationCommand cmd = new ReservationCommand();
        cmd.setBookId(1);
        cmd.setUsername("jdoe");
        cmd.setPickupDate(LocalDate.now().plusDays(1));
        cmd.setReturnDate(LocalDate.now().plusDays(10));
        System.out.println("before call to processAsync");
        
        this.processor.processAsync(cmd).thenAccept(c -> {
	        System.out.println(c.getReservation());
        });
        
        System.out.println("after call to processAsync");
        Thread.sleep(2500);
    }
}
