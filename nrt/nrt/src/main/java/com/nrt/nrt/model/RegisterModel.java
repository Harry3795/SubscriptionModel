package com.nrt.nrt.model;



public class RegisterModel {


	
	private long RegisterId ;
	

	private String email;


	private String password;
	
	private Integer remainingBids;

	
	//
	

	public long getRegisterId() {
		return RegisterId;
	}


	public Integer getRemainingBids() {
		return remainingBids;
	}


	public void setRemainingBids(Integer remainingBids) {
		this.remainingBids = remainingBids;
	}


	public void setRegisterId(int registerId) {
		RegisterId = registerId;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	@Override
	public String toString() {
		return "RegisterModel [RegisterId=" + RegisterId + ", email=" + email + ", password=" + password + "]";
	}
	
	
	
	
}
