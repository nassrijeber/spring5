package com.acme.ex3.service.command;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.List;

import com.acme.ex3.ApplicationConfig;
import org.junit.jupiter.api.Test;

import com.acme.common.business.CommandException;
import com.acme.common.service.impl.CommandProcessorImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

//@ExtendWith(SpringExtension.class) 1
//@ContextConfiguration(classes = ApplicationConfig.class) 1
//@SpringJUnitConfig(classes = ApplicationConfig.class) 2 ça remplace 1
@SpringBootTest //ça remplace 2 avec spring boot
@TestPropertySource(locations = "classpath:application-for-tests.properties")
public class MemberRegistrationCommandTest {

    @Autowired
    private CommandProcessorImpl processor;
    
    @Test
    void testMemberRegistrationCommandOnError() {
        MemberRegistrationCommand cmd = new MemberRegistrationCommand();
        cmd.setFirstname("Jane");
        cmd.setLastname("Doe");
        cmd.setUsername("jdoe");
        cmd.setPassword("azerty");
        cmd.setPreferences(List.of(1,2));
        assertThrows(CommandException.class, () -> this.processor.process(cmd));
    }
    
    @Test
    @Sql(statements = {
            "delete from Member_category",
            "delete from Member where id>1"})
    void testMemberRegistrationCommand() {
    	MemberRegistrationCommand cmd = new MemberRegistrationCommand();
        cmd.setFirstname("Jane");
        cmd.setLastname("Doe");
        cmd.setUsername("jane.doe");
        cmd.setPassword("azerty");     
        cmd.setPreferences(List.of(2,3));
        cmd= this.processor.process(cmd);
        assertNotNull(cmd.getMember());
    }

}
