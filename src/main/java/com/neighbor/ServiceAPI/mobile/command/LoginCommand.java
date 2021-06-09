package com.neighbor.ServiceAPI.mobile.command;

public class LoginCommand {

	private String mbrId;
	
	private String password;
	
	public LoginCommand(){
		super();
	}

	public String getMbrId() {
		return mbrId;
	}

	public void setMbrId(String mbrId) {
		this.mbrId = mbrId;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	
}
