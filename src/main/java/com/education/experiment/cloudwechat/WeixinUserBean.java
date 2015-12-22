package com.education.experiment.cloudwechat;

public class WeixinUserBean {
	@Override
	public String toString() {
		return "WeixinUserBean [id=" + id + ", name=" + name + ", age=" + age + ", sex=" + sex + ", vocation=" + vocation + ", friends=" + friends + ", splitfriends=" + splitfriends + "]";
	}

	private int id;
	private String name;
	private int age;
	private String sex;
	private String vocation;
	private String friends;
	private String splitfriends;

	public int getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public int getAge() {
		return age;
	}

	public String getSex() {
		return sex;
	}

	public String getVocation() {
		return vocation;
	}

	public String getFriends() {
		return friends;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public void setVocation(String vocation) {
		this.vocation = vocation;
	}

	public void setFriends(String friends) {
		this.friends = friends;
	}

	public void setSplitfriends(String splitfriends) {
		this.splitfriends = splitfriends;
	}

	public String getSplitfriends() {
		return splitfriends;
	}
	
	public WeixinUserBean() {
		super();
	}

	public WeixinUserBean(int id, String name, int age, String sex, String vocation, String friends) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.sex = sex;
		this.vocation = vocation;
		this.friends = friends;
	}
	
}
