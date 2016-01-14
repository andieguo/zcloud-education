package com.education.experiment.cloudexpress;

public class GPRSBean {
	private float longitude;
	private float latitude;

	public float getLongitude() {
		return longitude;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	@Override
	public String toString() {
		return "GPRSBean [longitude=" + longitude + ", latitude=" + latitude + "]";
	}
	
}
