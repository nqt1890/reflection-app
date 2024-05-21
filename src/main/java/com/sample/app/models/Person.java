package com.sample.app.models;

import java.lang.reflect.Field;

import com.sample.app.annotations.FakeChangeFieldName;

public class Person {
	private int id;
	private String name;
	private int age;
	private String address;
	
	public Person() {};
	
	public Person(int id, String name, int age, String address) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.address = address;
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

	public int getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		String rs = "";
		try {
			rs = String.format( "{\n"
					+ "\"%s\": \"%s\",\n"
					+ "\"%s\": \"%s\",\n"
					+ "\"%s\": %s,\n"
					+ "\"%s\": \"%s\"\n"
					+ "}", getFieldName("id"), getId(), getFieldName("name"), getName(), getFieldName("age"), getAge(), getFieldName("address"), getAddress());
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		}
		return rs;
	}
	
	private String getFieldName(String fieldName) throws NoSuchFieldException, SecurityException {
		Class<?> clazz = this.getClass();
		Field field = clazz.getDeclaredField(fieldName);
		
		if (field.isAnnotationPresent(FakeChangeFieldName.class)) {
			FakeChangeFieldName annotate = field.getAnnotation(FakeChangeFieldName.class);
			String name = annotate.name();
			return name;
		}
		else {
			return fieldName;
		}
	}
}
