package com.neighbor.ServiceAPI.mobile.Counselor.Domain;

public class CarInfoDomain {
	private String cnter_name;
	private String vhcle_no;
	private String drver_sttus_name;
	private String vhcle_ty_name;
	private String min;						//소요시간
	private String lc_crdnt_x;
	private String lc_crdnt_y;
	public String getCnter_name() {
		return cnter_name;
	}
	public void setCnter_name(String cnter_name) {
		this.cnter_name = cnter_name;
	}
	public String getVhcle_no() {
		return vhcle_no;
	}
	public void setVhcle_no(String vhcle_no) {
		this.vhcle_no = vhcle_no;
	}
	public String getDrver_sttus_name() {
		return drver_sttus_name;
	}
	public void setDrver_sttus_name(String drver_sttus_name) {
		this.drver_sttus_name = drver_sttus_name;
	}
	public String getVhcle_ty_name() {
		return vhcle_ty_name;
	}
	public void setVhcle_ty_name(String vhcle_ty_name) {
		this.vhcle_ty_name = vhcle_ty_name;
	}
	public String getMin() {
		return min;
	}
	public void setMin(String min) {
		this.min = min;
	}
	public String getLc_crdnt_x() {
		return lc_crdnt_x;
	}
	public void setLc_crdnt_x(String lc_crdnt_x) {
		this.lc_crdnt_x = lc_crdnt_x;
	}
	public String getLc_crdnt_y() {
		return lc_crdnt_y;
	}
	public void setLc_crdnt_y(String lc_crdnt_y) {
		this.lc_crdnt_y = lc_crdnt_y;
	}
	@Override
	public String toString() {
		return "CarInfoDomain [cnter_name=" + cnter_name + ", vhcle_no=" + vhcle_no + ", drver_sttus_name="
				+ drver_sttus_name + ", vhcle_ty_name=" + vhcle_ty_name + ", min=" + min + "]";
	}
	
}
