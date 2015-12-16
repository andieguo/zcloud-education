package com.education.experiment.cloudwechat;

public class WeixinResultBean {
	private String linkman;
	private String begintime;
	private String endtime;
	private String place;
	private String linkcontent;

	@Override
	public String toString() {
		return "WeixinResultBean [linkman=" + linkman + ", begintime=" + begintime + ", endtime=" + endtime + ", place=" + place + ", linkcontent=" + linkcontent + "]";
	}

	public String getLinkman() {
		return linkman;
	}

	public String getBegintime() {
		return begintime;
	}

	public String getEndtime() {
		return endtime;
	}

	public String getPlace() {
		return place;
	}

	public String getLinkcontent() {
		return linkcontent;
	}

	public void setLinkman(String linkman) {
		this.linkman = linkman;
	}

	public void setBegintime(String begintime) {
		this.begintime = begintime;
	}

	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public void setLinkcontent(String linkcontent) {
		this.linkcontent = linkcontent;
	}
}
