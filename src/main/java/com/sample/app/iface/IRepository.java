package com.sample.app.iface;

public interface IRepository<T> {
	
	public T findById(int id);
	
	public Boolean save(T object);
}

