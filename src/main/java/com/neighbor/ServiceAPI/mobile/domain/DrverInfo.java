package com.neighbor.ServiceAPI.mobile.domain;

import org.apache.ibatis.type.Alias;

@Alias("drverInfo")
public class DrverInfo {

	private String mber_id;
	private String cnter_cd;
	private String drver_sttus_cd;
	private String vhcle_no;
	private String work_de;
	private String work_tmzon;
	private String rm;
	public String getMber_id() {
		return mber_id;
	}
	public void setMber_id(String mber_id) {
		this.mber_id = mber_id;
	}
	public String getCnter_cd() {
		return cnter_cd;
	}
	public void setCnter_cd(String cnter_cd) {
		this.cnter_cd = cnter_cd;
	}
	public String getDrver_sttus_cd() {
		return drver_sttus_cd;
	}
	public void setDrver_sttus_cd(String drver_sttus_cd) {
		this.drver_sttus_cd = drver_sttus_cd;
	}
	public String getVhcle_no() {
		return vhcle_no;
	}
	public void setVhcle_no(String vhcle_no) {
		this.vhcle_no = vhcle_no;
	}
	public String getWork_de() {
		return work_de;
	}
	public void setWork_de(String work_de) {
		this.work_de = work_de;
	}
	public String getWork_tmzon() {
		return work_tmzon;
	}
	public void setWork_tmzon(String work_tmzon) {
		this.work_tmzon = work_tmzon;
	}
	public String getRm() {
		return rm;
	}
	public void setRm(String rm) {
		this.rm = rm;
	}

}
