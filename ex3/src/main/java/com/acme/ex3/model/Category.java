package com.acme.ex3.model;

import javax.persistence.Cacheable;
import javax.persistence.Entity;

import com.acme.common.model.AbstractPersistentEntity;

@Entity
@Cacheable //celle du jpa pour permettre de mettre en cache niveau 2
public class Category extends AbstractPersistentEntity<Integer> {

    private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
