package com.education.experiment.cloudwechat;

import java.util.Arrays;

public class WeixinParsingBean {
	@Override
	public String toString() {
		return "WeixinParsingBean [timePoint=" + timePoint + ", durationTime="
				+ durationTime + ", gender=" + gender + ", friend=" + friend
				+ ", maxAge=" + maxAge + ", minAge=" + minAge + ", vocations="
				+ vocations + ", communicationPlace="
				+ Arrays.toString(communicationPlace) + ", keywords="
				+ Arrays.toString(keywords) + "]";
	}

	public String getVocations() {
		return vocations;
	}

	public void setVocations(String vocations) {
		this.vocations = vocations;
	}

	public String getFriend() {
		return friend;
	}

	public void setFriend(String friend) {
		this.friend = friend;
	}

	private long timePoint = 0L;
	private int durationTime = 0;
	private String gender = null;
	private String friend = null;
	private int maxAge = 0;
	private int minAge = 0;
	private String vocations = null;
	private String[] communicationPlace = null;
	private String[] keywords = null;

	public long getTimePoint() {
		return timePoint;
	}

	public int getDurationTime() {
		return durationTime;
	}

	public String getGender() {
		return gender;
	}

	public int getMaxAge() {
		return maxAge;
	}

	public int getMinAge() {
		return minAge;
	}

	public String[] getCommunicationPlace() {
		return communicationPlace;
	}

	public String[] getKeywords() {
		return keywords;
	}

	public void setTimePoint(long timePoint) {
		this.timePoint = timePoint;
	}

	public void setDurationTime(int durationTime) {
		this.durationTime = durationTime;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public void setMaxAge(int maxAge) {
		this.maxAge = maxAge;
	}

	public void setMinAge(int minAge) {
		this.minAge = minAge;
	}

	public void setCommunicationPlace(String[] communicationPlace) {
		this.communicationPlace = communicationPlace;
	}

	public void setKeywords(String[] keywords) {
		this.keywords = keywords;
	}
}
