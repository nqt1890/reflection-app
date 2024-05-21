package com.sample.app.iface.repository;

import com.sample.app.iface.IRepository;
import com.sample.app.models.Person;


public interface IPersonRepository extends IRepository<Person> {
	
	public Person findByName(String name);
	
	public Person findByAge(int age);
}
