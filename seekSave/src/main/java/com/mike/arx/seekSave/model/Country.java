package com.mike.arx.seekSave.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="countries")
public class Country {
	@Id
	private String id;
	private String name;
	private int prefix;
	public Country (){
	}
	
	public Country(String name){
		this.name= name;
	}
	public Country(String name,int prefix){
		this.name= name;
		this.prefix=prefix;
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
	public int getPrefix() {
		return prefix;
	}
	public void setPrefix(int prefix) {
		this.prefix = prefix;
	}
	@Override
	 public String toString() {
	  return "Country [id=" + id + ", name=" + name + ", prefix=" + prefix + "]";
	 }
	

}
