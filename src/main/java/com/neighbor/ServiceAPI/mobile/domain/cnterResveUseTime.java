package com.neighbor.ServiceAPI.mobile.domain;

import org.apache.ibatis.type.Alias;

@Alias("cnterResveUseTime")
public class cnterResveUseTime {

	private String cnter_cd;
	private String cnter_nm;
	private String rcept_se_cd;
	private String sctn_begin_time;
	private String sctn_end_time;
	private String beffat_resve_posble_de;
	public String getCnter_cd() {
		return cnter_cd;
	}
	public void setCnter_cd(String cnter_cd) {
		this.cnter_cd = cnter_cd;
	}
	public String getCnter_nm() {
		return cnter_nm;
	}
	public void setCnter_nm(String cnter_nm) {
		this.cnter_nm = cnter_nm;
	}
	public String getRcept_se_cd() {
		return rcept_se_cd;
	}
	public void setRcept_se_cd(String rcept_se_cd) {
		this.rcept_se_cd = rcept_se_cd;
	}
	public String getSctn_begin_time() {
		return sctn_begin_time;
	}
	public void setSctn_begin_time(String sctn_begin_time) {
		this.sctn_begin_time = sctn_begin_time;
	}
	public String getSctn_end_time() {
		return sctn_end_time;
	}
	public void setSctn_end_time(String sctn_end_time) {
		this.sctn_end_time = sctn_end_time;
	}
	public String getBeffat_resve_posble_de() {
		return beffat_resve_posble_de;
	}
	public void setBeffat_resve_posble_de(String beffat_resve_posble_de) {
		this.beffat_resve_posble_de = beffat_resve_posble_de;
	}
	@Override
	public String toString() {
		return "cnterResveUseTime [cnter_cd=" + cnter_cd + ", cnter_nm=" + cnter_nm +", rcept_se_cd=" + rcept_se_cd +  
				", sctn_begin_time=" + sctn_begin_time +  ", sctn_end_time=" + sctn_end_time +  
				", beffat_resve_posble_de=" + beffat_resve_posble_de+ "]";
	}
}
