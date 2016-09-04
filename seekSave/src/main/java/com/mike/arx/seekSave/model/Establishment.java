package com.mike.arx.seekSave.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import com.drew.lang.annotations.NotNull;

@Document(collection = "establishments")
public class Establishment {
	@Id
	private String id;
	@Indexed(unique=true)
	@NotNull
	private String name;
	private String address;
	private String webPage;
	@Indexed(unique=true)
	private String contactMail;
	private String phone;
	@DBRef
	private Town town;

	public Establishment() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getWebPage() {
		return webPage;
	}

	public void setWebPage(String webPage) {
		this.webPage = webPage;
	}

	public String getContactMail() {
		return contactMail;
	}

	public void setContactMail(String contactMail) {
		this.contactMail = contactMail;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setTown(Town town) {
		this.town = town;
	}

	public Town getTown() {
		return town;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}

	@Override
	public String toString() {
		return "Establishment [id=" + id + ", name=" + name + ", address=" + address + ", webPage=" + webPage
				+ ", contactMail=" + contactMail + ", phone=" + phone + ", town=" + town + "]";
	}

	

}
