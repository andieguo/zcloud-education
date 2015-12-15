package com.education.experiment.cloudweather;

public class MonthBean {
	private float minTemp;
	private float maxTemp;
	private float humidity;
	private float WSP;

	public float getMinTemp() {
		return minTemp;
	}

	public float getMaxTemp() {
		return maxTemp;
	}

	public float getHumidity() {
		return humidity;
	}

	public float getWSP() {
		return WSP;
	}

	public void setMinTemp(float minTemp) {
		this.minTemp = minTemp;
	}

	public void setMaxTemp(float maxTemp) {
		this.maxTemp = maxTemp;
	}

	public void setHumidity(float humidity) {
		this.humidity = humidity;
	}

	public void setWSP(float wSP) {
		WSP = wSP;
	}
}
