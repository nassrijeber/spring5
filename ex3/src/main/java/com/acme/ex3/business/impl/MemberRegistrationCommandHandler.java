package com.acme.ex3.business.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.acme.common.business.CommandException;
import com.acme.common.business.CommandHandler;
import com.acme.common.service.AbstractCommand;
import com.acme.ex3.model.Account;
import com.acme.ex3.model.Category;
import com.acme.ex3.model.Member;
import com.acme.ex3.repository.CategoryRepository;
import com.acme.ex3.repository.MemberRepository;
import com.acme.ex3.service.command.MemberRegistrationCommand;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Component // added
@CommandHandler.Handler // soit ça soit bas 1/
public class MemberRegistrationCommandHandler implements CommandHandler {

	//private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Autowired
    MemberRepository memberRepository;

    @Autowired
    CategoryRepository categoryRepository;

	@Override
  //  @Transactional(propagation= Propagation.MANDATORY) // added soit ça soit haut 1/
    public void handle(AbstractCommand command, HandlingContext handlingContext) {
        if(!(command instanceof MemberRegistrationCommand)){
            return;
        }

        MemberRegistrationCommand cmd = (MemberRegistrationCommand)command;

        /* TODO : 
        remplacer null ci dessous par un appel au repository pour obtenir le Member dont le username est cmd.getUsername()
        */
        Optional<Member> _member = this.memberRepository.findByAccountUsername(cmd.getUsername()); // added
        if(_member.isPresent()){
            throw new CommandException("memberRegistration-account.already.exists", true);
        }

        Member member = new Member();
        member.setFirstname(cmd.getFirstname());
        member.setLastname(cmd.getLastname());

        Account account = new Account();
        // si nous stockons les mots de passe : 
        //account.setPassword(passwordEncoder.encode(cmd.getPassword()));
        account.setUsername(cmd.getUsername());
        member.setAccount(account);

        if(cmd.getPreferences()!=null) {
        	// TODO: comprendre et décommenter les lignes ci dessous (préalable : ajouter une dépendance vers CategoryRepository)
        	List<Category> preferences = cmd.getPreferences().stream()
        			.flatMap(i -> categoryRepository.findById(i).stream())
        			.collect(Collectors.toList());
        	member.setPreferences(preferences);
        }
        // TODO : sauvegarde du member
        this.memberRepository.save(member); // added
        
        cmd.setMember(member);
        
        // si nous déléguons l'authentification : création du compte utilisateur sur ce dernier.
    }
}