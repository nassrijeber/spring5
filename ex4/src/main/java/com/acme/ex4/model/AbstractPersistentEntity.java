package com.acme.ex4.model;

import org.springframework.data.annotation.Id;

public abstract class AbstractPersistentEntity<T> {

	@Id
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
