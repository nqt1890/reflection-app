package com.sample.app.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin
@RestController
public class BeController {

	List<Sale> list = new ArrayList<>();
	
	
	@GetMapping("/sales")
	public List<Sale> testSales() {
		list = new ArrayList<>();

		System.err.println("Call Sales");
		Sale s1 = new Sale("s1", "Abcdef", 110);
		Sale s2 = new Sale("s2", "XYZ", 200);
		Sale s3 = new Sale("s2", "XYZ", 230);
		list.add(s1);
		list.add(s2);
		list.add(s3);
		return list;
	}
}


class Sale {
	private String key;
	private String username;
	private int volume;
	
	
	public Sale(String key, String username, int volume) {
		super();
		this.key = key;
		this.username = username;
		this.volume = volume;
	}
	
	
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public int getVolume() {
		return volume;
	}
	public void setVolume(int volume) {
		this.volume = volume;
	}


	public String getKey() {
		return key;
	}


	public void setKey(String key) {
		this.key = key;
	} 
	
	
}