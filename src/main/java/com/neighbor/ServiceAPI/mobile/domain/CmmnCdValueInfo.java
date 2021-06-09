package com.neighbor.ServiceAPI.mobile.domain;

import org.apache.ibatis.type.Alias;

@Alias("cmmnCdValueInfo")
public class CmmnCdValueInfo {

	private String cd_id;
	
	private String cd_value;
	
	private String cd_value_de;

	public String getCd_id() {
		return cd_id;
	}

	public void setCd_id(String cd_id) {
		this.cd_id = cd_id;
	}

	public String getCd_value() {
		return cd_value;
	}

	public void setCd_value(String cd_value) {
		this.cd_value = cd_value;
	}

	public String getCd_value_de() {
		return cd_value_de;
	}

	public void setCd_value_de(String cd_value_de) {
		this.cd_value_de = cd_value_de;
	}

	
}
