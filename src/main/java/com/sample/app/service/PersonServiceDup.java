package com.sample.app.service;


import com.sample.app.annotations.FakeService;
import com.sample.app.iface.IPersonService;

//@SampleService
public class PersonServiceDup implements IPersonService {
	
	public String testNewPerson() {
		return "A Duplicate Person";
	}
}
