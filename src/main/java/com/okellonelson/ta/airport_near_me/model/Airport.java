package com.okellonelson.ta.airport_near_me.model;

import java.io.Serializable;

public class Airport implements Serializable {
	private static final long serialVersionUID = 1L;
	private String code;
	private Double lat;
	private Double lon;

	public Airport(String code, Double lat, Double lon) {
		this.code = code;
		this.lat = lat;
		this.lon = lon;
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("Code: ").append(code).append(", ").append("Lat: ").append(lat).append(", ").append("Lon: ")
				.append(lon);
		return sb.toString();
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Double getLat() {
		return lat;
	}

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public Double getLon() {
		return lon;
	}

	public void setLon(Double lon) {
		this.lon = lon;
	}
}
