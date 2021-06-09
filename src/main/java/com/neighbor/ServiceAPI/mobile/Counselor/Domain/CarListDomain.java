package com.neighbor.ServiceAPI.mobile.Counselor.Domain;

public class CarListDomain {
	private String vhcle_no;			//차량번호
	private String drver_sttus_cd;		//운전자 상태코드
	private String lc_crdnt_x;			//운전자 현재 위치
	private String lc_crdnt_y;
	private String arvl_lc_crdnt_x;		//목적지 위치
	private String arvl_lc_crdnt_y;
	private String start_lc_crdnt_x;	//예약 출발 위치
	private String start_lc_crdnt_y;
	
	private String totalTime;			//현재 위치에서 예약 출발지까지 걸리는 시간
	public String getVhcle_no() {
		return vhcle_no;
	}
	public void setVhcle_no(String vhcle_no) {
		this.vhcle_no = vhcle_no;
	}
	public String getDrver_sttus_cd() {
		return drver_sttus_cd;
	}
	public void setDrver_sttus_cd(String drver_sttus_cd) {
		this.drver_sttus_cd = drver_sttus_cd;
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
	public String getArvl_lc_crdnt_x() {
		return arvl_lc_crdnt_x;
	}
	public void setArvl_lc_crdnt_x(String arvl_lc_crdnt_x) {
		this.arvl_lc_crdnt_x = arvl_lc_crdnt_x;
	}
	public String getArvl_lc_crdnt_y() {
		return arvl_lc_crdnt_y;
	}
	public void setArvl_lc_crdnt_y(String arvl_lc_crdnt_y) {
		this.arvl_lc_crdnt_y = arvl_lc_crdnt_y;
	}
	public String getStart_lc_crdnt_x() {
		return start_lc_crdnt_x;
	}
	public void setStart_lc_crdnt_x(String start_lc_crdnt_x) {
		this.start_lc_crdnt_x = start_lc_crdnt_x;
	}
	public String getStart_lc_crdnt_y() {
		return start_lc_crdnt_y;
	}
	public void setStart_lc_crdnt_y(String start_lc_crdnt_y) {
		this.start_lc_crdnt_y = start_lc_crdnt_y;
	}
	public String getTotalTime() {
		return totalTime;
	}
	public void setTotalTime(String totalTime) {
		this.totalTime = totalTime;
	}
	@Override
	public String toString() {
		return "CarListDomain [vhcle_no=" + vhcle_no + ", drver_sttus_cd=" + drver_sttus_cd + ", lc_crdnt_x="
				+ lc_crdnt_x + ", lc_crdnt_y=" + lc_crdnt_y + ", arvl_lc_crdnt_x=" + arvl_lc_crdnt_x
				+ ", arvl_lc_crdnt_y=" + arvl_lc_crdnt_y + ", start_lc_crdnt_x=" + start_lc_crdnt_x
				+ ", start_lc_crdnt_y=" + start_lc_crdnt_y + ", totalTime=" + totalTime + "]";
	}
	
}
