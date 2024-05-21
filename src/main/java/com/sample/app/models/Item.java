package com.sample.app.models;

public class Item {
	private int id;
	private String name;
	private int number;
	
	public Item() {}

	public Item(int id, String name, int number) {
		super();
		this.id = id;
		this.name = name;
		this.number = number;
	}

	public int getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumber() {
		return number;
	}

	public void setNumber(Integer number) {
		this.number = number;
	} ;
}
