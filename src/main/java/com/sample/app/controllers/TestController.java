package com.sample.app.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.sample.app.annotations.FakeAutowired;
import com.sample.app.iface.IPersonService;
import com.sample.app.iface.IRepository;
import com.sample.app.iface.repository.IPersonRepository;
import com.sample.app.models.Item;
import com.sample.app.models.Person;
import com.sample.app.runtime.RuntimeHandler;
import com.sample.app.service.MyService;

@RestController
public class TestController {

	private Logger logger = LoggerFactory.getLogger(TestController.class);
	
	
	
	@Autowired
	MyService sprService;
	
	
	
	// init AFTER the rest controller loaded in spring context
	@FakeAutowired
	IPersonService ps;
	
	
	@FakeAutowired
	IPersonRepository personRepo;
	
//	compare reflection repository init 
	IRepository<?> itemRepo = initRepository(IRepository.class, Item.class);
	
	
	@GetMapping("/spring")
	public String testSpring() {
		return sprService.testSpring();
	}
	

	@GetMapping("/test")
	public String testPerson() {
		logger.info("Test person");
		return ps.testNewPerson();
	}
	
	@GetMapping("/person/{idStr}")
	public String getValue( @PathVariable String idStr) {
		int id = Integer.parseInt(idStr);
		Person p = personRepo.findById(id);
//		Person p = personRepo.findByName("Ann");
//		Person p = personRepo.findByAge(30);
		
		if (p != null) {
			return p.toString();
		}
		else {
			return "Record not found";
		}
	}
	
	@GetMapping("/create_person")
	public boolean createRandomPerson() {
		logger.info("Create random person info");
		
		Person person = new Person(5, "Five", 32, "000 new address");
		boolean rs = personRepo.save(person);
		return rs;
	}
	
	
	@GetMapping("/item/{idStr}")
	public Item getItem( @PathVariable String idStr) {
		logger.info("Get item info");
		int id = Integer.parseInt(idStr);
		
		Item item  = (Item) itemRepo.findById(id);
		if (item != null) {
			return item;
		}
		else {
			return null;
		}
	}
	
	
	// SETUP
	private <T> T initRepository(Class<?> repoCls, Class<?> modelCls) {
		return RuntimeHandler.initRepository(repoCls, modelCls);
	}
}
