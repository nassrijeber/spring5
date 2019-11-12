package com.acme.common.model;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class AbstractPersistentEntity<T> {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private T id;

	public T getId() {
		return id;
	}

	public void setId(T id) {
		this.id = id;
	}

	@Override
	public int hashCode() {
		return this.id == null ? super.hashCode() : (this.id).hashCode();
	}


	@Override
	public boolean equals(Object obj) {
		if(obj==null) {
			return false;
		}
		else if(obj==this) {
			return true;
		}
		else if(obj.getClass()!=this.getClass()) {
			return false;
		}
		else {
			return this.getId()!=null && this.getId().equals(((AbstractPersistentEntity<?>)obj).getId());
		}
	}

}
