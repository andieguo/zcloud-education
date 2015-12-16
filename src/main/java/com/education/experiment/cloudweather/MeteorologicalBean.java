package com.education.experiment.cloudweather;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;

import org.apache.hadoop.io.WritableComparable;

public class MeteorologicalBean implements WritableComparable<MeteorologicalBean> {
	@Override
	public String toString() {
		return "MeteorologicalBean [maxTemp=" + maxTemp + ", minTemp=" + minTemp + ", humidity=" + humidity + ", WSP=" + WSP + "]";
	}

	private float maxTemp;
	private float minTemp;
	private float humidity;
	private float WSP;

	public float getMaxTemp() {
		return maxTemp;
	}

	public float getMinTemp() {
		return minTemp;
	}

	public float getHumidity() {
		return humidity;
	}

	public float getWSP() {
		return WSP;
	}

	public void setMaxTemp(float maxTemp) {
		this.maxTemp = maxTemp;
	}

	public void setMinTemp(float minTemp) {
		this.minTemp = minTemp;
	}

	public void setHumidity(float humidity) {
		this.humidity = humidity;
	}

	public void setWSP(float wSP) {
		WSP = wSP;
	}

	public void readFields(DataInput dataIn) throws IOException {
		// TODO Auto-generated method stub
		maxTemp = dataIn.readFloat();
		minTemp = dataIn.readFloat();
		humidity = dataIn.readFloat();
		WSP = dataIn.readFloat();
	}

	public void write(DataOutput dataOut) throws IOException {
		// TODO Auto-generated method stub
		dataOut.writeFloat(maxTemp);
		dataOut.writeFloat(minTemp);
		dataOut.writeFloat(humidity);
		dataOut.writeFloat(WSP);
	}

	public int compareTo(MeteorologicalBean other) {
		// TODO Auto-generated method stub
		if (other.getMaxTemp() > this.getMaxTemp()) {
			return -1;
		} else if (other.getMaxTemp() < this.getMaxTemp()) {
			return 1;
		} else {
			return 0;
		}
	}

}
