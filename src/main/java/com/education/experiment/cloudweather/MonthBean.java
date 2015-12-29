package com.education.experiment.cloudweather;

public class MonthBean {
	private double minTemp;
	private double maxTemp;
	private double humidity;
	private double WSP;

	public double getMinTemp() {
		return minTemp;
	}

	public double getMaxTemp() {
		return maxTemp;
	}

	public double getHumidity() {
		return humidity;
	}

	public double getWSP() {
		return WSP;
	}

	public void setMinTemp(double minTemp) {
		this.minTemp = minTemp;
	}

	public void setMaxTemp(double maxTemp) {
		this.maxTemp = maxTemp;
	}

	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}

	public void setWSP(double wSP) {
		WSP = wSP;
	}
}
