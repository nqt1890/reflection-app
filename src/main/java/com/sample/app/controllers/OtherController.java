package com.sample.app.controllers;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.sample.app.annotations.FakeAutowired;
import com.sample.app.iface.IPersonService;

@CrossOrigin
@RestController
public class OtherController {

	private Logger logger = LoggerFactory.getLogger(OtherController.class);
	List<Meetup> list = new ArrayList<>();
	
	@FakeAutowired
	IPersonService ps;
	
	@GetMapping("/otherperson")
	public String testPerson() {
		logger.info("Get person info");
		
		return ps.testNewPerson();
	}
	
	@PostMapping("/meetup")
	public String addMeetup(@RequestBody Meetup data) {
		System.out.println("Post request OK");
		System.out.println(data);
		list.add(data);
		return "ok";
	}
	
	@GetMapping("/meetups")
	public List<Meetup> getMeetup() {
		if (list.size() == 0) {
			getData();
		}
		return list;
	}
	
	private List<Meetup> getData() {
		Meetup m1 = new Meetup("m1", "This is a first meetup",
				 "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d3/Stadtbild_M%C3%BCnchen.jpg/2560px-Stadtbild_M%C3%BCnchen.jpg", 
				 "This is a first, amazing meetup which you definitely should not miss. It will be a lot of fun!");
		
		Meetup m2 = new Meetup("m2", "This is a 2nd meetup",
				 "https://upload.wikimedia.org/wikipedia/commons/thumb/d/d3/Stadtbild_M%C3%BCnchen.jpg/2560px-Stadtbild_M%C3%BCnchen.jpg", 
				 "This is a Second, amazing meetup which you definitely should not miss. It will be a lot of fun!");
		list.add(m1);
		list.add(m2);
		System.out.println(list);
		
		return list;
	}
}


class Meetup {
	private String id;
	private String title;
	private String image;
	private String description;
	
	public Meetup(String id, String title, String image, String description) {
		super();
		this.id = id;
		this.title = title;
		this.image = image;
		this.description = description;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
}
