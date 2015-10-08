package com.mike.arx.seekSave.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="towns")
public class Town{
	@Id
	private String id;
	@Indexed(unique=true)
	private String name;
	@DBRef
	private Country country;
	public Town(){
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Country getCountry() {
		return country;
	}
	public void setCountry(Country country) {
		this.country = country;
	}
	@Override
	public String toString() {
		return "Town [id=" + id + ", name=" + name + ", country=" + country + "]";
	}

}
