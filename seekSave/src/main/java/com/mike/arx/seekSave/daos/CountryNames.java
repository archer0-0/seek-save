package com.mike.arx.seekSave.daos;

public enum CountryNames {
	SPAIN("spain"),UK("uk"),FRANCE("france"),PORTUGAL("portugal");
	String countryName;
	private CountryNames(String countryName) {
		this.countryName=countryName;
	}
	public String getCountryName() {
		return countryName;
	}
	@Override
	public String toString() {
		return super.toString();
	}

}
