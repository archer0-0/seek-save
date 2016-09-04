package com.mike.arx.seekSave.model;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "emilsSent")
public class EmailSent {
	@Id
	private String id;
	private Date sentDate;
	private boolean response;
	@DBRef
	private Establishment establishment;
	public EmailSent(){
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public Date getSentDate() {
		return sentDate;
	}
	public void setSentDate(Date sentDate) {
		this.sentDate = sentDate;
	}
	public boolean isResponse() {
		return response;
	}
	public void setResponse(boolean response) {
		this.response = response;
	}
	public Establishment getEstablishment() {
		return establishment;
	}
	public void setEstablishment(Establishment establishment) {
		this.establishment = establishment;
	}
	@Override
	public String toString() {
		return "EmailSent [id=" + id + ", sentDate=" + sentDate + ", response=" + response + ", establishment="
				+ establishment + "]";
	}
	
}
