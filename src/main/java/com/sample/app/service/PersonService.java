package com.sample.app.service;

import com.sample.app.annotations.FakeService;
import com.sample.app.iface.IPersonService;

@FakeService
public class PersonService implements IPersonService {
	
	private String title;
	
	public String testNewPerson() {
		return "A Person";
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}
}
