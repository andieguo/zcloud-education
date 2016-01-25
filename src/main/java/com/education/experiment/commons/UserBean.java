package com.education.experiment.commons;

public class UserBean {
	private String userId;
	private String userPassWd;
	private String userName;
	private String phoneNumber;
	private String remark;
	private long cloudSize;
	
	public long getCloudSize(){
		return cloudSize;
	}

	public String getCloudSizeString() {
		String cloudsNum = "0B";
		if(cloudSize > 0){
			cloudsNum = cloudSize+"B";
		}
		if(cloudSize > 1024){
			cloudsNum = cloudSize/1024 +"KB";
		}
		if(cloudSize > 1024*1024){
			cloudsNum = cloudSize/1024/1024+"MB";
		}
		if(cloudSize > 1024*1024*1024){
			cloudsNum = cloudSize/1024/1024/1024+"GB";
		}
		return cloudsNum;
	}

	public void setCloudSize(long cloudSize) {
		this.cloudSize = cloudSize;
	}

	public String getUserId() {
		return userId;
	}

	public String getUserPassWd() {
		return userPassWd;
	}

	public String getUserName() {
		return userName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public String getRemark() {
		return remark;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setUserPassWd(String userPassWd) {
		this.userPassWd = userPassWd;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	@Override
	public String toString() {
		return "UserBean [userId=" + userId + ", userPassWd=" + userPassWd + ", userName=" + userName + ", phoneNumber=" + phoneNumber + ", remark=" + remark + ", cloudSize=" + cloudSize + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userName == null) ? 0 : userName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserBean other = (UserBean) obj;
		if (userName == null) {
			if (other.userName != null)
				return false;
		} else if (!userName.equals(other.userName))
			return false;
		return true;
	}
	
	public static void main(String[] args) {
		UserBean userBean = new UserBean();
		userBean.setCloudSize(10736436491L);
		System.out.println(userBean.getCloudSize());
	}
	
}
