package com.neighbor.ServiceAPI.mobile.LocInfoSystem.Domain;

import org.apache.ibatis.type.Alias;

@Alias("locDrverInfoDomain")
public class LocDrverInfoDomain {
	private String mber_id;
	private String drver_nm;
	private String vhcle_no;
	private String handi_mber_nm;
	private String trobl;
	private String cnter_nm;
	public String getMber_id() {
		return mber_id;
	}
	public void setMber_id(String mber_id) {
		this.mber_id = mber_id;
	}
	public String getDrver_nm() {
		return drver_nm;
	}
	public void setDrver_nm(String drver_nm) {
		this.drver_nm = drver_nm;
	}
	public String getVhcle_no() {
		return vhcle_no;
	}
	public void setVhcle_no(String vhcle_no) {
		this.vhcle_no = vhcle_no;
	}
	public String getHandi_mber_nm() {
		return handi_mber_nm;
	}
	public void setHandi_mber_nm(String handi_mber_nm) {
		this.handi_mber_nm = handi_mber_nm;
	}
	public String getTrobl() {
		return trobl;
	}
	public void setTrobl(String trobl) {
		this.trobl = trobl;
	}
	public String getCnter_nm() {
		return cnter_nm;
	}
	public void setCnter_nm(String cnter_nm) {
		this.cnter_nm = cnter_nm;
	}
	@Override
	public String toString() {
		return "LocDrverInfoDomain [mber_id=" + mber_id + ", drver_nm=" + drver_nm + ", vhcle_no=" + vhcle_no
				+ ", handi_mber_nm=" + handi_mber_nm + ", trobl=" + trobl + ", cnter_nm=" + cnter_nm + "]";
	}
	
}
